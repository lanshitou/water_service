package com.zzwl.ias.dto.iasystem;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/13.
 * 用户绑定系统
 */
public class AddUserAndIaSystemDTO {
    //用户id
    private Integer userId;
    //系统id
    private List<Integer> iaSystemIds;
    //角色id
    private List<Integer> roleIds;

    public List<Integer> getIaSystemIds() {
        return iaSystemIds;
    }

    public void setIaSystemIds(List<Integer> iaSystemIds) {
        this.iaSystemIds = iaSystemIds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
