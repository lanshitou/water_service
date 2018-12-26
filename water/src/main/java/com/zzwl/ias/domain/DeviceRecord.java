package com.zzwl.ias.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceRecord {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private Integer iasId;

    private String name;

    private Integer keepAlive;

    private Boolean lowConsume;
    private String icon;

    private byte[] config;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getLowConsume() {
        return lowConsume;
    }

    public void setLowConsume(Boolean lowConsume) {
        this.lowConsume = lowConsume;
    }

    public byte[] getConfig() {
        return config;
    }

    public void setConfig(byte[] config) {
        this.config = config;
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
        DeviceRecord other = (DeviceRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getIasId() == null ? other.getIasId() == null : this.getIasId().equals(other.getIasId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getKeepAlive() == null ? other.getKeepAlive() == null : this.getKeepAlive().equals(other.getKeepAlive()))
                && (this.getLowConsume() == null ? other.getLowConsume() == null : this.getLowConsume().equals(other.getLowConsume()))
                && (Arrays.equals(this.getConfig(), other.getConfig()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIasId() == null) ? 0 : getIasId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getKeepAlive() == null) ? 0 : getKeepAlive().hashCode());
        result = prime * result + ((getLowConsume() == null) ? 0 : getLowConsume().hashCode());
        result = prime * result + (Arrays.hashCode(getConfig()));
        return result;
    }
}