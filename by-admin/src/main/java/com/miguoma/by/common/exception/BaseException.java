package com.miguoma.by.common.exception;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private int code;
    private String msg;

    public BaseException(final String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }

    
    public BaseException(final String msg, final Throwable e) {
        super(msg, e);
        this.code = 500;
        this.msg = msg;
    }

    public BaseException(String template, Object... message) {
        super(StrUtil.format(template, message));
        code = 500;
        msg = StrUtil.format(template, message);
    }
}
