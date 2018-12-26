package com.zzwl.ias.vo.message;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SensorOwnerVo {
    Integer type;
    Integer iaSystemId;
    String iaSystemName;
    Integer farmlandId;
    String farmlandName;
    Integer areaId;
    String areaName;
    Long opDevId;
    String opDevName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIaSystemId() {
        return iaSystemId;
    }

    public void setIaSystemId(Integer iaSystemId) {
        this.iaSystemId = iaSystemId;
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(Integer farmlandId) {
        this.farmlandId = farmlandId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Long getOpDevId() {
        return opDevId;
    }

    public void setOpDevId(Long opDevId) {
        this.opDevId = opDevId;
    }

    public String getIaSystemName() {
        return iaSystemName;
    }

    public void setIaSystemName(String iaSystemName) {
        this.iaSystemName = iaSystemName;
    }

    public String getFarmlandName() {
        return farmlandName;
    }

    public void setFarmlandName(String farmlandName) {
        this.farmlandName = farmlandName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getOpDevName() {
        return opDevName;
    }

    public void setOpDevName(String opDevName) {
        this.opDevName = opDevName;
    }
}
