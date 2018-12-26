package com.zzwl.ias.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by HuXin on 2017/12/11.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private ErrorCode errorCode;
    private T data;

    public Result(ErrorCode errorCode, T data) {
        this.errorCode = errorCode;
        this.data = data;
    }

    public String getMessage() {
        return errorCode.getDescTemplate();
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return errorCode.getValue();
    }

    public static Result error(ErrorCode errorCode) {
        return new Result<>(errorCode, null);
    }

    public static <T> Result<T> error(T data) {
        return new Result<>(ErrorCode.ERROR, data);
    }

    public static Result ok() {
        return new Result<>(ErrorCode.OK, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ErrorCode.OK, data);
    }
}
