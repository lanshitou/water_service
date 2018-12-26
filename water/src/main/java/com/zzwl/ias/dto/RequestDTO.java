package com.zzwl.ias.dto;

import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-25
 * Time: 11:53
 */
public abstract class RequestDTO {
    protected Integer userId;

    public RequestDTO() {
        userId = CurrentUserUtil.getCurrentUserId();
    }

    public RequestDTO(Integer userId){
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    abstract public void check();
}
