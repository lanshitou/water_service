package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.common.SensorValue;

/**
 * Created by HuXin on 2018/3/21.
 */
public class SensorAirHumi extends Device {
    public SensorAirHumi(DeviceRecord deviceRecord, MasterController masterController) {
        super(deviceRecord, masterController);
        sensorValues = new SensorValue[1];
        sensorValues[0] = SensorValue.create(SensorValue.AIR_HUMI);
    }

    @Override
    public ErrorCode updateState(int state, int[] sensorValues) {
        this.state = state;
        if (state == DeviceState.DEV_STATE_OK) {
            updateSensorData(SensorValue.AIR_HUMI, sensorValues[0]);
        } else if (state == DeviceState.DEV_STATE_OFFLINE) {
            updateSensorData(SensorValue.AIR_HUMI, SensorValue.VALUE_INVALID);
        } else {
            return ErrorCode.OPERATION_ERR_REPLY_INVALID;
        }

        return ErrorCode.OK;
    }

}