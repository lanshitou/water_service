package com.zzwl.ias.common;

public class AssertErrorCodeException extends RuntimeException {
    public ErrorCode errorCode;
    AssertErrorCodeException(ErrorCode e) {
        errorCode = e;
    }
}
