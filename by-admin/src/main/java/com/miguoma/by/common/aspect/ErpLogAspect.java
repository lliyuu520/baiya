package com.miguoma.by.common.aspect;

import com.alibaba.fastjson.JSON;
import com.miguoma.by.common.annotation.ErpLogCut;
import com.miguoma.by.modules.erp.entity.ErpLog;
import com.miguoma.by.modules.erp.service.ErpLogService;
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
 * ERP日志切面
 * 用于记录ERP系统操作日志，包括：
 * 1. 操作模块
 * 2. 请求参数和响应结果
 * 3. 操作时间
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
     * ERP日志服务
     */
    private final ErpLogService erpLogService;

    /**
     * 定义切点
     * 拦截所有带有@ErpLogCut注解的方法
     */
    @Pointcut("@annotation(com.miguoma.by.common.annotation.ErpLogCut)")
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
            asyncSaveErpLog(point, result, 0, null);
            return result;
        } catch (Throwable e) {
            // 异步保存失败日志
            asyncSaveErpLog(point, e, 1, e.getMessage());
            throw e;
        }
    }

    /**
     * 异步保存ERP日志
     * 记录操作信息、请求参数和响应结果
     *
     * @param joinPoint 连接点
     * @param result    目标方法的返回值或异常
     * @param status    操作状态：0-成功，1-失败
     * @param errorMsg  错误信息
     */
    @Async("logExecutor")
    public void asyncSaveErpLog(ProceedingJoinPoint joinPoint, Object result, Integer status, String errorMsg) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            ErpLog erpLog = new ErpLog();
            // 获取注解
            ErpLogCut erpLogCut = method.getAnnotation(ErpLogCut.class);
            if (erpLogCut != null) {
                // 设置操作模块
                erpLog.setModuleName(erpLogCut.module().getDesc());
            }

            // 记录请求参数
            try {
                Object[] args = joinPoint.getArgs();
                String params = JSON.toJSONString(args);
                erpLog.setRequestParams(params);
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
                erpLog.setResponseResult(responseResult);
            } catch (Exception e) {
                log.error("记录响应结果失败", e);
            }

            // 设置操作时间
            LocalDateTime now = LocalDateTime.now();
            erpLog.setOperateTime(now);
            erpLog.setCreateTime(now);
            // 设置操作状态
            erpLog.setStatus(status);
            // 设置错误信息
            erpLog.setErrorMsg(errorMsg);

            // 保存ERP日志
            erpLogService.save(erpLog);
        } catch (Exception e) {
            log.error("异步保存ERP日志失败", e);
        }
    }
}