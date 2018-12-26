package com.zzwl.ias.dto.camera;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-25
 * Time: 16:05
 */
public class OpenVideoOnYsResultDTO {
    private String code;
    private String msg;
    private LinkedList<OpenVideoOnYsSingleResultDTO> data;

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

    public LinkedList<OpenVideoOnYsSingleResultDTO> getData() {
        return data;
    }

    public void setData(LinkedList<OpenVideoOnYsSingleResultDTO> data) {
        this.data = data;
    }
}

