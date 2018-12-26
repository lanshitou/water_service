package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by HuXin on 2017/12/14.
 */
public class SimpleFarmlandInfoVo {
    private int id;
    private String name;
    private List<DeviceInfoVo> deviceInfoVos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("devices")
    public List<DeviceInfoVo> getDeviceInfoVos() {
        return deviceInfoVos;
    }

    public void setDeviceInfoVos(List<DeviceInfoVo> deviceInfoVos) {
        this.deviceInfoVos = deviceInfoVos;
    }
}
