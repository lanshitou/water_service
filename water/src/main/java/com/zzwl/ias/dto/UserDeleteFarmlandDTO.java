package com.zzwl.ias.dto;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/16.
 */
public class UserDeleteFarmlandDTO {
    private Integer userId;
    private Integer iasId;
    private List<Integer> fids;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public List<Integer> getFids() {
        return fids;
    }

    public void setFids(List<Integer> fids) {
        this.fids = fids;
    }
}
