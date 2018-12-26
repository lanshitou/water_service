package com.zzwl.ias.dto.camera;

import java.util.Calendar;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-26
 * Time: 9:54
 */
public class VideoAccessTokenDTO {
    private String accessToken;
    private Calendar expDate;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Calendar getExpDate() {
        return expDate;
    }

    public void setExpDate(Calendar expDate) {
        this.expDate = expDate;
    }
}
