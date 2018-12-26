package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.SensorValue;

/**
 * Created by HuXin on 2017/11/29.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceInfoVo {
    DeviceAddr deviceAddr;
    String name;
    boolean isUsed;
    boolean isOnline;
    Integer OpState;
    SensorValue[] sensorValues;
    LatestOpVo latestOpVo;

    public DeviceInfoVo(DeviceAddr deviceAddr) {
        this.deviceAddr = deviceAddr;
    }

    public long getId() {
        return deviceAddr.getId();
    }

    public byte getType() {
        return deviceAddr.getDevType();
    }

    @JsonIgnore
    public DeviceAddr getDeviceAddr() {
        return deviceAddr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public Integer getOpState() {
        return OpState;
    }

    public void setOpState(int opState) {
        OpState = opState;
    }

    public SensorValue[] getSensorValues() {
        return sensorValues;
    }

    public void setSensorValues(SensorValue[] sensorValues) {
        this.sensorValues = sensorValues;
    }

    @JsonProperty("latest_op")
    public LatestOpVo getLatestOpVo() {
        return latestOpVo;
    }

    public void setLatestOpVo(LatestOpVo latestOpVo) {
        this.latestOpVo = latestOpVo;
    }
}
