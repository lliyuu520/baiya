package com.miguoma.by.common.utils;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Http
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@UtilityClass
public class HttpContextUtils {

    public HttpServletRequest getHttpServletRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }


    public HttpServletResponse getHttpServletResponse() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }

        return ((ServletRequestAttributes) requestAttributes).getResponse();
    }


    public Map<String, String> getParameterMap(final HttpServletRequest request) {
        final Enumeration<String> parameters = request.getParameterNames();

        final Map<String, String> params = new HashMap<>();
        while (parameters.hasMoreElements()) {
            final String parameter = parameters.nextElement();
            final String value = request.getParameter(parameter);
            if (StrUtil.isNotBlank(value)) {
                params.put(parameter, value);
            }
        }

        return params;
    }

    public String getDomain() {
        final HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        final StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public String getOrigin() {
        final HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        return request.getHeader(HttpHeaders.ORIGIN);
    }
}
