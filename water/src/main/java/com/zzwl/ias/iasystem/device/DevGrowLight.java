package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceRecord;

/**
 * Created by HuXin on 2018/3/21.
 */
public class DevGrowLight extends Device {

    public DevGrowLight(DeviceRecord deviceRecord, MasterController masterController) {
        super(deviceRecord, masterController);
    }

    @Override
    public ErrorCode updateState(int state, int[] sensorValues) {
        return updateStateTemplate1(state);
    }
}