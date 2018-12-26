package com.zzwl.ias.common;

import java.util.Collection;

public class AssertEx {
    static public void isNotEmpty(Object[] collection) {
        if (collection == null || collection.length == 0)
            throw new AssertErrorCodeException(ErrorCode.EMPTY);
    }

    static public void isNotEmpty(Collection<?> collection) {
        if (collection == null || collection.isEmpty())
            throw new AssertErrorCodeException(ErrorCode.EMPTY);
    }

    static public void isTrue(boolean condition, ErrorCode errorCode) {
        if (!condition) throw new AssertErrorCodeException(errorCode);
    }

    static public void isOK(ErrorCode errorCode){
        if (errorCode != ErrorCode.OK){
            throw new AssertErrorCodeException(errorCode);
        }
    }
}