package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceRecord;

/**
 * Created by HuXin on 2018/4/3.
 */
public class DevPulseValve extends Device {


    public DevPulseValve(DeviceRecord deviceRecord, MasterController masterController) {
        super(deviceRecord, masterController);
    }

    @Override
    public ErrorCode updateState(int state, int[] sensorValues) {
        return updateStateTemplate1(state);
    }

}