package com.zzwl.ias.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-23
 * Time: 14:36
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IasObjectAddrDTO {
    private Integer type;
    private Integer iasId;
    private String iasName;
    private Integer irriFerId;
    private String irriFerName;
    private Integer farmlandId;
    private String farmlandName;
    private Integer areaId;
    private String areaName;
    private Integer devId;
    private String devName;
    private Integer parentDevId;
    private String parentDevName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public String getIasName() {
        return iasName;
    }

    public void setIasName(String iasName) {
        this.iasName = iasName;
    }

    public Integer getIrriFerId() {
        return irriFerId;
    }

    public void setIrriFerId(Integer irriFerId) {
        this.irriFerId = irriFerId;
    }

    public String getIrriFerName() {
        return irriFerName;
    }

    public void setIrriFerName(String irriFerName) {
        this.irriFerName = irriFerName;
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(Integer farmlandId) {
        this.farmlandId = farmlandId;
    }

    public String getFarmlandName() {
        return farmlandName;
    }

    public void setFarmlandName(String farmlandName) {
        this.farmlandName = farmlandName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getDevId() {
        return devId;
    }

    public void setDevId(Integer devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public Integer getParentDevId() {
        return parentDevId;
    }

    public void setParentDevId(Integer parentDevId) {
        this.parentDevId = parentDevId;
    }

    public String getParentDevName() {
        return parentDevName;
    }

    public void setParentDevName(String parentDevName) {
        this.parentDevName = parentDevName;
    }
}
