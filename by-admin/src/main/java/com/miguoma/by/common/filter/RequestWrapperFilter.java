package com.miguoma.by.common.filter;

import com.miguoma.by.common.wrapper.MultiReadHttpServletRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 请求包装过滤器
 * 用于包装HttpServletRequest，使其支持多次读取请求体
 *
 * @author liliangyu
 */
public class RequestWrapperFilter extends OncePerRequestFilter {

    /**
     * 处理请求过滤器
     * 将原始请求包装为可多次读取的请求对象
     *
     * @param request     原始HTTP请求
     * @param response    HTTP响应
     * @param filterChain 过滤器链
     * @throws ServletException 如果发生servlet异常
     * @throws IOException      如果发生I/O异常
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 包装请求
        MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
        // 继续处理
        filterChain.doFilter(wrappedRequest, response);
    }
}
