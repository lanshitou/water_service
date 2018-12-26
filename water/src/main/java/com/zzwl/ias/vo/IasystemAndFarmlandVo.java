package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Lvpin on 2018/11/12.
 */
public class IasystemAndFarmlandVo {
    private Integer id;
    private String name;
    //iid 如果为null则没有和系统建立关系
    private Integer iid;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer roleId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String roleName;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
