package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.User;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserBasicInfoVo {
    Integer uid;
    String headImage;
    String username;

    public UserBasicInfoVo(){}

    public UserBasicInfoVo(Integer uid){
        this.uid = uid;
        headImage = null;
        username = null;
    }

    public UserBasicInfoVo(User user) {
        uid = user.getId();
        headImage = user.getImage();
        username = user.getUsername();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
