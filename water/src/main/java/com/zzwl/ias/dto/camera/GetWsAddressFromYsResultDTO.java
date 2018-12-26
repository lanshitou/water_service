package com.zzwl.ias.dto.camera;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-06
 * Time: 12:55
 */
public class GetWsAddressFromYsResultDTO {
    private String code;
    private String msg;
    private LinkedList<GetWsAddressFromYsSingleResult> data;

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

    public LinkedList<GetWsAddressFromYsSingleResult> getData() {
        return data;
    }

    public void setData(LinkedList<GetWsAddressFromYsSingleResult> data) {
        this.data = data;
    }
}
