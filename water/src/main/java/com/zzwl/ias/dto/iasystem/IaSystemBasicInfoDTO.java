package com.zzwl.ias.dto.iasystem;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Description:
 * User: HuXin
 * Date: 2018-05-25
 * Time: 14:53
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IaSystemBasicInfoDTO {
    private Integer id;
    private Integer type;
    private String name;
    private String alias;
    private Integer maxIrrNum;
    private Integer workMode;
    private Integer sortOrder;

    private Integer permission;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getMaxIrrNum() {
        return maxIrrNum;
    }

    public void setMaxIrrNum(Integer maxIrrNum) {
        this.maxIrrNum = maxIrrNum;
    }

    public Integer getWorkMode() {
        return workMode;
    }

    public void setWorkMode(Integer workMode) {
        this.workMode = workMode;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getPermission() {
        return permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}
