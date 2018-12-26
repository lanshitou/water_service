package com.zzwl.ias.dto.camera;

import java.util.HashMap;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-25
 * Time: 16:36
 */
public class GetCapabilityFromYsDTO {
    private String code;
    private String msg;
    private HashMap<String, String> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}


