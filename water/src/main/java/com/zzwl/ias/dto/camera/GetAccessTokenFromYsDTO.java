package com.zzwl.ias.dto.camera;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-26
 * Time: 16:39
 */
public class GetAccessTokenFromYsDTO {
    private String code;
    private String msg;
    private GetAccessTokenFromYsResultDTO data;

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

    public GetAccessTokenFromYsResultDTO getData() {
        return data;
    }

    public void setData(GetAccessTokenFromYsResultDTO data) {
        this.data = data;
    }
}
