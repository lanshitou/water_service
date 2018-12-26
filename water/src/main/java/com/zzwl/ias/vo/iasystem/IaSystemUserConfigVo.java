package com.zzwl.ias.vo.iasystem;

/**
 * Created by HuXin on 2018/2/27.
 */
public class IaSystemUserConfigVo {
    private int userId;
    private int iaSystemId;
    private int roleId;
    private int[] farmlands;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIaSystemId() {
        return iaSystemId;
    }

    public void setIaSystemId(int iaSystemId) {
        this.iaSystemId = iaSystemId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int[] getFarmlands() {
        return farmlands;
    }

    public void setFarmlands(int[] farmlands) {
        this.farmlands = farmlands;
    }
}
