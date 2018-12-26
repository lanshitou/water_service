package com.zzwl.ias.vo;

import java.util.LinkedList;

/**
 * Created by HuXin on 2018/2/26.
 */
public class AddDevReqVo {
    private LinkedList<DeviceConfigVo> devices;

    public LinkedList<DeviceConfigVo> getDevices() {
        return devices;
    }

    public void setDevices(LinkedList<DeviceConfigVo> devices) {
        this.devices = devices;
    }
}
