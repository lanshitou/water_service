package com.zzwl.ias.common;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-18
 * Time: 15:20
 */
public class ErrorCodeException extends Exception {
    private ErrorCode errorCode;

    public ErrorCodeException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
