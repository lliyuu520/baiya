package com.miguoma.by.common.handler;

import com.miguoma.by.common.exception.BaseException;
import com.miguoma.by.common.utils.Result;
import com.miguoma.by.modules.erp.constant.SignatureConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.util.NoSuchElementException;


/**
 * 异常处理器
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Slf4j
@RestControllerAdvice
public class ServerExceptionHandler {

    /**
     * 处理签名验证异常
     */
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result<String> handleSignatureException(SignatureException e) {
        log.error("签名验证异常: {}", e.getMessage());
        return Result.error(SignatureConstants.SIGNATURE_ERROR_CODE, e.getMessage());
    }
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BaseException.class)
    public Result<String> handleException(BaseException ex) {

        return Result.error(ex.getCode(), ex.getMsg());
    }

    /**
     * SpringMVC参数绑定，Validator校验不正确
     */
    @ExceptionHandler(BindException.class)
    public Result<String> bindException(BindException ex) {
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null;
        return Result.error(fieldError.getDefaultMessage());
    }


    /**
     * 参数异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException ex) {
       log.error(ex.getMessage(), ex);
        return Result.error(ex.getMessage());
    }

    /**
     * 其他异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(NoSuchElementException.class)
    public Result<String> handleException(NoSuchElementException ex) {
       log.error(ex.getMessage(), ex);
        return Result.error("数据不存在");
    }


    /**
     * 其他异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception ex) {
       log.error(ex.getMessage(), ex);
        return Result.error(500, "服务器异常，请稍后再试");
    }

//
}
