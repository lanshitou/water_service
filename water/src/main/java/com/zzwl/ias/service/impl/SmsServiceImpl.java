package com.zzwl.ias.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HuXin on 2018/1/4.
 */
@Service
public class SmsServiceImpl implements SmsService {
    private static final String signName = "众智新农";
    //获取注册验证码
    private static final String registerTemplateCode = "SMS_129760869";
    private static final String registerTemplateParam = "{\"code\":\"%s\"}";
    //获取修改密码验证码
    private static final String changePassTemplateCode = "SMS_129745849";
    private static final String changePassTemplateParam = "{\"code\":\"%s\"}";

    @Autowired
    private IAcsClient acsClient;

    @Override
    public ErrorCode sendRegisterVerificationCode(String mobile, String verificationCode) {
        String templateCode = registerTemplateCode;
        String templateParam = String.format(registerTemplateParam, verificationCode);

        return Send(mobile, templateCode, templateParam);
    }

    @Override
    public ErrorCode sendChangePassCode(String mobile, String verificationCode) {
        String templateCode = changePassTemplateCode;
        String templateParam = String.format(changePassTemplateParam, verificationCode);

        return Send(mobile, templateCode, templateParam);
    }

    private ErrorCode Send(String mobile, String templateCode, String templateParam) {
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(mobile);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(templateParam);

        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (!sendSmsResponse.getCode().equals("OK")) {
                return ErrorCode.SEND_MSG_FAIL;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return ErrorCode.SEND_MSG_FAIL;
        } catch (ClientException e) {
            e.printStackTrace();
            return ErrorCode.SEND_MSG_FAIL;
        }

        return ErrorCode.OK;
    }

}
