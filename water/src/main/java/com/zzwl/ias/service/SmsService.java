package com.zzwl.ias.service;

import com.zzwl.ias.common.ErrorCode;

/**
 * Created by HuXin on 2018/1/4.
 */
public interface SmsService {
    ErrorCode sendRegisterVerificationCode(String mobile, String verifyCode);

    ErrorCode sendChangePassCode(String mobile, String verificationCode);
}
