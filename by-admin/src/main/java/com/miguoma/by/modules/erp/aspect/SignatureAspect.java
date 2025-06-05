package com.miguoma.by.modules.erp.aspect;


import com.miguoma.by.modules.erp.annotation.SignatureCheck;
import com.miguoma.by.modules.erp.config.AppConfig;
import com.miguoma.by.modules.erp.constant.SignatureConstants;
import com.miguoma.by.modules.erp.utils.SignatureUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.SignatureException;

/**
 * 签名验证切面
 *
 * @author AI Assistant
 */
@Slf4j
@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class SignatureAspect {

    private final AppConfig appConfig;

    @Around("@annotation(signatureCheck)")
    public Object validateSignature(ProceedingJoinPoint joinPoint, SignatureCheck signatureCheck) throws Throwable {

        // 如果签名验证被禁用，直接执行方法
        if (!appConfig.isEnabled()) {
            log.debug("签名验证已禁用，直接执行方法");
            return joinPoint.proceed();
        }

        // 如果注解标记为不必须验证，直接执行方法
        if (!signatureCheck.required()) {
            log.debug("当前方法不要求签名验证，直接执行");
            return joinPoint.proceed();
        }

        try {
            // 获取请求对象
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes == null) {
                throw new SignatureException("无法获取请求上下文");
            }

            HttpServletRequest request = attributes.getRequest();

            // 获取请求头信息
            String appId = request.getHeader(SignatureConstants.HEADER_APP_ID);
            String timestamp = request.getHeader(SignatureConstants.HEADER_TIMESTAMP);
            String nonce = request.getHeader(SignatureConstants.HEADER_NONCE);
            String signature = request.getHeader(SignatureConstants.HEADER_SIGNATURE);

            log.debug("签名验证请求 - AppId: {}, Timestamp: {}, Nonce: {}, Signature: {}",
                    appId, timestamp, nonce, signature);

            // 验证必要参数
            if (!StringUtils.hasText(appId) || !StringUtils.hasText(timestamp) ||
                !StringUtils.hasText(nonce) || !StringUtils.hasText(signature)) {
                throw new SignatureException("缺少必要的签名参数");
            }

            // 验证应用ID
            if (!appConfig.isValidAppId(appId)) {
                throw new SignatureException("无效的应用ID: " + appId);
            }

            // 验证时间戳
            if (!SignatureUtil.isValidTimestamp(timestamp)) {
                throw new SignatureException("请求时间戳已过期: " + timestamp);
            }

            // 获取请求体内容


            // 验证签名
            String appSecret = appConfig.getAppSecret(appId);
            boolean isValidSignature = SignatureUtil.verifySignature(appId, timestamp, nonce, signature,
                    appSecret);

            if (!isValidSignature) {
                throw new SignatureException("签名验证失败");
            }

            log.debug("签名验证成功，执行业务方法");
            return joinPoint.proceed();

        } catch (SignatureException e) {
            log.warn("签名验证失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("签名验证过程中发生异常", e);
            throw new SignatureException("签名验证异常");
        }
    }

   
}