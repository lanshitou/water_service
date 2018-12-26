package com.zzwl.ias.common;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Object parameterErrorHandler(Exception e) {
        return Result.error(ErrorCode.USER_NOT_LOGIN);
    }

    @ExceptionHandler(value = ErrorCodeException.class)
    @ResponseBody
    public Object errorCodeExceptionHandler(Exception e) {
        return Result.error(((ErrorCodeException) e).getErrorCode());
    }

    @ExceptionHandler(value = AssertErrorCodeException.class)
    @ResponseBody
    public Object assertErrorCodeExceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.error(((AssertErrorCodeException) e).errorCode);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object nullPointerException(Exception e) {
        e.printStackTrace();
        return Result.error(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
