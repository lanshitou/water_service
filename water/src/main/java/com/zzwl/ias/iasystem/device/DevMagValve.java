package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceRecord;

/**
 * Created by HuXin on 2017/12/1.
 */
public class DevMagValve extends Device {


    public DevMagValve(DeviceRecord deviceRecord, MasterController masterController) {
        super(deviceRecord, masterController);
    }

    @Override
    public ErrorCode updateState(int state, int[] sensorValues) {
        return updateStateTemplate1(state);
    }

}
