package com.zzwl.ias.iasystem;

import com.zzwl.ias.common.ErrorCode;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HuXin on 2018/1/17.
 */
public class DeviceBindingMap {
    private ConcurrentHashMap<Long, DeviceBindingInfo> deviceBindingInfos;

    public DeviceBindingMap() {
        deviceBindingInfos = new ConcurrentHashMap<>();
    }

    public ErrorCode add(DeviceBindingInfo deviceBindingInfo) {
        if (null != deviceBindingInfos.putIfAbsent(deviceBindingInfo.getDeviceId(), deviceBindingInfo)) {
            return ErrorCode.DEVICE_ALREADY_BOUND;
        }
        return ErrorCode.OK;
    }

    public DeviceBindingInfo get(long devId) {
        return deviceBindingInfos.get(devId);
    }
}
