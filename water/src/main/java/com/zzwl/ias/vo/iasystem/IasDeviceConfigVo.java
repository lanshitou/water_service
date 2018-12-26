package com.zzwl.ias.vo.iasystem;

import com.zzwl.ias.domain.IasDeviceRecord;

/**
 * Description:
 * User: HuXin
 * Date: 2018-04-26
 * Time: 10:36
 */
public class IasDeviceConfigVo {
    private Integer id;
    private Long deviceId;
    private Integer iasId;
    private Integer farmlandId;
    private Integer irrigationAreaId;
    private Long iasDevId;
    private Integer irriFerId;
    private Integer usageType;
    private int userId;
    private int sortOrder;
    private String name;

    public IasDeviceRecord createIasDeviceRecord() {
        IasDeviceRecord iasDeviceRecord = new IasDeviceRecord();
        iasDeviceRecord.setDevId(deviceId);
        iasDeviceRecord.setIasId(iasId);
        iasDeviceRecord.setFarmlandId(farmlandId);
        iasDeviceRecord.setIrriAreaId(irrigationAreaId);
        iasDeviceRecord.setIasDevId(iasDevId);
        iasDeviceRecord.setIrriFerId(irriFerId);
        iasDeviceRecord.setUsageType(usageType);
        iasDeviceRecord.setUserId(userId);
        iasDeviceRecord.setSortOrder(sortOrder);
        iasDeviceRecord.setName(name);
        return iasDeviceRecord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(Integer farmlandId) {
        this.farmlandId = farmlandId;
    }

    public Integer getIrrigationAreaId() {
        return irrigationAreaId;
    }

    public void setIrrigationAreaId(Integer irrigationAreaId) {
        this.irrigationAreaId = irrigationAreaId;
    }

    public Long getIasDevId() {
        return iasDevId;
    }

    public void setIasDevId(Long iasDevId) {
        this.iasDevId = iasDevId;
    }

    public Integer getIrriFerId() {
        return irriFerId;
    }

    public void setIrriFerId(Integer irriFerId) {
        this.irriFerId = irriFerId;
    }

    public Integer getUsageType() {
        return usageType;
    }

    public void setUsageType(Integer usageType) {
        this.usageType = usageType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
