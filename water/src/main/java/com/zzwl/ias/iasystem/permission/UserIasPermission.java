package com.zzwl.ias.iasystem.permission;

import com.zzwl.ias.iasystem.constant.PermissionConstant;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-22
 * Time: 15:32
 */
public class UserIasPermission {
    private Integer iasId;
    private Integer role;
    private LinkedList<Integer> farmlands;

    public boolean checkFarmlandPermission(Integer farmlandId){
        if (role == PermissionConstant.IASYSTEM_USER_RETRIEVE){
            return farmlands.contains(farmlandId);
        }
        return true;
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public LinkedList<Integer> getFarmlands() {
        return farmlands;
    }

    public void setFarmlands(LinkedList<Integer> farmlands) {
        this.farmlands = farmlands;
    }
}
