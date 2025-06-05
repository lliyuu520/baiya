package com.miguoma.by.common.utils;

import lombok.Data;

/**
 * 响应数据
 *
 * @author lliyuu520 lliyuu520@gmail.com
 */
@Data
public class Result<T> {
    private int code = 0;

    private String msg = "success";

    private T data;

    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    public static <T> Result<T> ok(final T data) {
        final Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }


    public static <T> Result<T> error(final String msg) {
        return Result.error(500, msg);
    }


    public static <T> Result<T> error(final int code, final String msg) {
        final Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
