package com.zzwl.ias.domain;

public class IasDeviceRecord {
    private Integer id;

    private Long devId;

    private Integer iasId;

    private Integer farmlandId;

    private Integer irriAreaId;

    private Long iasDevId;

    private Integer irriFerId;

    private String name;

    private Integer usageType;

    private Integer userId;

    private Integer sortOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
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

    public Integer getIrriAreaId() {
        return irriAreaId;
    }

    public void setIrriAreaId(Integer irriAreaId) {
        this.irriAreaId = irriAreaId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getUsageType() {
        return usageType;
    }

    public void setUsageType(Integer usageType) {
        this.usageType = usageType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        IasDeviceRecord other = (IasDeviceRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getDevId() == null ? other.getDevId() == null : this.getDevId().equals(other.getDevId()))
                && (this.getIasId() == null ? other.getIasId() == null : this.getIasId().equals(other.getIasId()))
                && (this.getFarmlandId() == null ? other.getFarmlandId() == null : this.getFarmlandId().equals(other.getFarmlandId()))
                && (this.getIrriAreaId() == null ? other.getIrriAreaId() == null : this.getIrriAreaId().equals(other.getIrriAreaId()))
                && (this.getIasDevId() == null ? other.getIasDevId() == null : this.getIasDevId().equals(other.getIasDevId()))
                && (this.getIrriFerId() == null ? other.getIrriFerId() == null : this.getIrriFerId().equals(other.getIrriFerId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getUsageType() == null ? other.getUsageType() == null : this.getUsageType().equals(other.getUsageType()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getSortOrder() == null ? other.getSortOrder() == null : this.getSortOrder().equals(other.getSortOrder()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDevId() == null) ? 0 : getDevId().hashCode());
        result = prime * result + ((getIasId() == null) ? 0 : getIasId().hashCode());
        result = prime * result + ((getFarmlandId() == null) ? 0 : getFarmlandId().hashCode());
        result = prime * result + ((getIrriAreaId() == null) ? 0 : getIrriAreaId().hashCode());
        result = prime * result + ((getIasDevId() == null) ? 0 : getIasDevId().hashCode());
        result = prime * result + ((getIrriFerId() == null) ? 0 : getIrriFerId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getUsageType() == null) ? 0 : getUsageType().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getSortOrder() == null) ? 0 : getSortOrder().hashCode());
        return result;
    }
}