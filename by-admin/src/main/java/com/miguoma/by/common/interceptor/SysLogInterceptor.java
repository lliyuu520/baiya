package com.miguoma.by.common.interceptor;

import com.miguoma.by.common.event.SysLogEvent;
import com.miguoma.by.modules.system.entity.SysLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 控制层日志拦截器
 *
 * @author Keller
 * @create 2018/1/29
 */
@RequiredArgsConstructor
public class SysLogInterceptor implements HandlerInterceptor {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        SysLog sysLog = new SysLog();
        // 设置基本信息
        sysLog.setIp(getIpAddress(request));
        sysLog.setUrl(request.getRequestURI());
        sysLog.setMethod(request.getMethod());
        // 发布事件
        eventPublisher.publishEvent(new SysLogEvent(sysLog));
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
