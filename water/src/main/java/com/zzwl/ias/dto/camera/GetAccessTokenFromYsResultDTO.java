package com.zzwl.ias.dto.camera;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-26
 * Time: 16:40
 */
public class GetAccessTokenFromYsResultDTO {
    private String accessToken;
    private String expireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
