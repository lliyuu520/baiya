package com.miguoma.by.modules.erp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 签名验证注解
 * 在需要进行签名验证的接口方法上使用此注解
 *
 * @author AI Assistant
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SignatureCheck {

    /**
     * 是否必须验证签名，默认为true
     */
    boolean required() default true;
}