package com.miguoma.by.common.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.miguoma.by.common.annotation.SysLogCut;
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
public class ErpLogAspect {

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
        // 执行方法
        Object result = point.proceed();
        // 异步保存日志
        asyncSaveSysLog(point, result);
        return result;
    }

    /**
     * 异步保存系统日志
     * 记录操作信息、请求参数和响应结果
     *
     * @param joinPoint 连接点
     * @param result    目标方法的返回值
     */
    @Async("logExecutor")
    public void asyncSaveSysLog(ProceedingJoinPoint joinPoint, Object result) {
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
                String responseResult = JSON.toJSONString(result);
                sysLog.setResponseResult(responseResult);
            } catch (Exception e) {
                log.error("记录响应结果失败", e);
            }

            // 获取当前登录用户
            String username = "anonymous";
            try {
                if (StpUtil.isLogin()) {
                    username = StpUtil.getLoginIdAsString();
                }
            } catch (Exception e) {
                log.error("获取用户名失败", e);
            }
            sysLog.setOperatorName(username);

            // 设置操作时间
            sysLog.setOperateTime(LocalDateTime.now());
            // 设置操作状态为正常
            sysLog.setStatus(0);

            // 保存系统日志
            sysLogService.save(sysLog);
        } catch (Exception e) {
            log.error("异步保存系统日志失败", e);
        }
    }
}