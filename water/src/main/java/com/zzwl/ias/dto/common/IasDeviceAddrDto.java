package com.zzwl.ias.dto.common;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-13
 * Time: 8:37
 */
public class IasDeviceAddrDto {
    private Long devId;                 //设备自身的ID
    private Integer type;               //设备在智慧农业系统中的使用方式
    private Integer iaSystemId;         //智慧农业系统的ID
    private Integer farmlandId;         //农田的ID
    private Integer areaId;             //灌溉区的ID
    private Long opDevId;               //传感器设备所挂接的可操作设备的ID

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

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
}
