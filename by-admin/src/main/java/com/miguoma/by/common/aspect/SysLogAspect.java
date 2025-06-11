package com.miguoma.by.common.aspect;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.miguoma.by.common.annotation.SysLogCut;
import com.miguoma.by.common.utils.ClientContextHolder;
import com.miguoma.by.common.utils.SysUserUtil;
import com.miguoma.by.modules.system.entity.SysLog;
import com.miguoma.by.modules.system.service.SysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 系统日志切面
 * 用于记录系统操作日志，包括：
 * 1. 操作模块和类型
 * 2. 请求参数和响应结果
 * 3. 操作人和时间
 * 4. 操作状态和错误信息
 *
 * @author AI Assistant
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SysLogAspect {

    /**
     * 系统日志服务
     */
    private final SysLogService sysLogService;

    /**
     * 定义切点
     * 拦截所有带有@SysLogCut注解的方法
     */
    @Pointcut("@annotation(com.miguoma.by.common.annotation.SysLogCut)")
    public void logPointCut() {
    }

    /**
     * 环绕通知
     * 在目标方法执行前后进行日志记录
     *
     * @param point 连接点
     * @return 目标方法的返回值
     * @throws Throwable 如果目标方法抛出异常
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        try {
            // 执行方法
            result = point.proceed();
            // 异步保存成功日志
            asyncSaveSysLog(point, result, 0, null);
            return result;
        } catch (Throwable e) {
            // 异步保存失败日志
            asyncSaveSysLog(point, e, 1, e.getMessage());
            throw e;
        }
    }

    /**
     * 异步保存系统日志
     * 记录操作信息、请求参数和响应结果
     *
     * @param joinPoint 连接点
     * @param result    目标方法的返回值或异常
     * @param status    操作状态：0-成功，1-失败
     * @param errorMsg  错误信息
     */
    @Async("logExecutor")
    public void asyncSaveSysLog(ProceedingJoinPoint joinPoint, Object result, Integer status, String errorMsg) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            SysLog sysLog = new SysLog();
            // 获取注解
            SysLogCut sysLogCut = method.getAnnotation(SysLogCut.class);
            if (sysLogCut != null) {
                // 设置操作模块和类型
                sysLog.setModuleName(sysLogCut.module().getDesc());
                sysLog.setTypeName(sysLogCut.type().getDesc());
            }

            // 记录请求参数
            try {
                Object[] args = joinPoint.getArgs();
                String params = JSON.toJSONString(args);
                sysLog.setRequestParams(params);
            } catch (Exception e) {
                log.error("记录请求参数失败", e);
            }

            // 记录响应结果
            try {
                String responseResult;
                if (result instanceof Throwable) {
                    Throwable throwable = (Throwable) result;
                    responseResult = String.format("异常类型：%s\n异常信息：%s\n堆栈信息：%s",
                            throwable.getClass().getName(),
                            throwable.getMessage(),
                            throwable.getStackTrace()[0]);
                } else {
                    responseResult = JSON.toJSONString(result);
                }
                sysLog.setResponseResult(responseResult);
            } catch (Exception e) {
                log.error("记录响应结果失败", e);
            }

            // 获取当前登录用户
            String username = "anonymous";
            try {
                if (StpUtil.isLogin()) {
                    username = SysUserUtil.getUserInfo().getUsername();
                } else {
                    String factoryCode = ClientContextHolder.getFactoryCode();
                    String workshopCode = ClientContextHolder.getWorkshopName();
                    if (StrUtil.isNotBlank(factoryCode) || StrUtil.isNotBlank(workshopCode)) {
                        username = factoryCode + "_" + workshopCode;
                    }
                }
            } catch (Exception e) {
                log.error("获取用户名失败", e);
            }
            sysLog.setOperatorName(username);

            // 设置操作时间
            LocalDateTime now = LocalDateTime.now();
            sysLog.setOperateTime(now);
            sysLog.setCreateTime(now);
            // 设置操作状态
            sysLog.setStatus(status);
            // 设置错误信息
            sysLog.setErrorMsg(errorMsg);

            // 保存系统日志
            sysLogService.save(sysLog);
        } catch (Exception e) {
            log.error("异步保存系统日志失败", e);
        }
    }
}