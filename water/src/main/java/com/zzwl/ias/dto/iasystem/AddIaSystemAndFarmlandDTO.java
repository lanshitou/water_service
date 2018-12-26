package com.zzwl.ias.dto.iasystem;

import java.util.List;

/**
 * 系统绑定农田
 * Created by Lvpin on 2018/11/14.
 */
public class AddIaSystemAndFarmlandDTO {
    //系统id
    private Integer iasId;
    //农田id集合
    private List<Integer> fids;
    //角色id
    private Integer roleId;
    //用户id
    private Integer userId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

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
