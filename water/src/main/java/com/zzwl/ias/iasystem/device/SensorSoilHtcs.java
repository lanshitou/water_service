package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.common.SensorValue;

public class SensorSoilHtcs extends Device {

    public SensorSoilHtcs(DeviceRecord deviceRecord, MasterController masterController) {
        super(deviceRecord, masterController);
        sensorValues = new SensorValue[4];
        sensorValues[0] = SensorValue.create(SensorValue.SOIL_TEMP);
        sensorValues[1] = SensorValue.create(SensorValue.SOIL_HUMI);
        sensorValues[2] = SensorValue.create(SensorValue.SVT_SOIL_CONDUCTIVITY);
        sensorValues[3] = SensorValue.create(SensorValue.SVT_SOIL_SALINITY);
    }

    @Override
    public ErrorCode updateState(int state, int[] sensorValues) {
        this.state = state;
        if (state == DeviceState.DEV_STATE_OK) {
            updateSensorData(SensorValue.SOIL_TEMP, sensorValues[0]);
            updateSensorData(SensorValue.SOIL_HUMI, sensorValues[1]);
            updateSensorData(SensorValue.SVT_SOIL_CONDUCTIVITY, sensorValues[2]);
            updateSensorData(SensorValue.SVT_SOIL_SALINITY, sensorValues[3]);
        } else if (state == DeviceState.DEV_STATE_OFFLINE) {
            updateSensorData(SensorValue.SOIL_TEMP, SensorValue.VALUE_INVALID);
            updateSensorData(SensorValue.SOIL_HUMI, SensorValue.VALUE_INVALID);
            updateSensorData(SensorValue.SVT_SOIL_CONDUCTIVITY, SensorValue.VALUE_INVALID);
            updateSensorData(SensorValue.SVT_SOIL_SALINITY, SensorValue.VALUE_INVALID);
        } else {
            return ErrorCode.OPERATION_ERR_REPLY_INVALID;
        }

        return ErrorCode.OK;
    }
}
