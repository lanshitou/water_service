package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceType;

/**
 * Created by HuXin on 2017/9/20.
 */
public class DeviceFactory {
    static public Device createDevice(DeviceRecord deviceRecord, MasterController masterController) {
        switch (DeviceAddr.getDevTypeById(deviceRecord.getId())) {
            case DeviceType.DEV_ELEC_VALVE: {
                return new DevElecValve(deviceRecord, masterController);
            }
            case DeviceType.DEV_MAGNETIC_VALVE: {
                return new DevMagValve(deviceRecord, masterController);
            }
            case DeviceType.DEV_PUMP: {
                return new DevPump(deviceRecord, masterController);
            }
            case DeviceType.DEV_FAN: {
                return new DevFan(deviceRecord, masterController);
            }
            case DeviceType.DEV_SHUTTER: {
                return new DevShutter(deviceRecord, masterController);
            }
            case DeviceType.SEN_SOIL_TEMP: {
                return new SensorSoilTemp(deviceRecord, masterController);
            }
            case DeviceType.DEV_GROW_LIGHT: {
                return new DevGrowLight(deviceRecord, masterController);
            }
            case DeviceType.DEV_HEALER: {
                return new DevHealer(deviceRecord, masterController);
            }
            case DeviceType.DEV_DEHUMIDIFIER: {
                return new DevDehumidifer(deviceRecord, masterController);
            }
            case DeviceType.DEV_PULSE_VALVE: {
                return new DevPulseValve(deviceRecord, masterController);
            }
            case DeviceType.SEN_SOIL_HUMI: {
                return new SensorSoilHumi(deviceRecord, masterController);
            }
            case DeviceType.SEN_SOIL_H_T: {
                return new SensorSoilHT(deviceRecord, masterController);
            }
            case DeviceType.SEN_AIR_TEMP: {
                return new SensorAirTemp(deviceRecord, masterController);
            }
            case DeviceType.SEN_AIR_HUMI: {
                return new SensorAirHumi(deviceRecord, masterController);
            }
            case DeviceType.SEN_AIR_H_T: {
                return new SensorAirHT(deviceRecord, masterController);
            }
            case DeviceType.SEN_LIGHT_ILLU: {
                return new SensorLightIllu(deviceRecord, masterController);
            }
            case DeviceType.SEN_CO2: {
                return new SensorCO2(deviceRecord, masterController);
            }
            case DeviceType.SEN_H_T_I: {
                return new SensorHTI(deviceRecord, masterController);
            }
            case DeviceType.SEN_AIR_H_T_HANG: {
                return new SensorAirHTHang(deviceRecord, masterController);
            }
            case DeviceType.SENSOR_SOIL_HTCS: {
                return new SensorSoilHtcs(deviceRecord, masterController);
            }
            case DeviceType.SENSOR_SOIL_PH: {
                return new SensorSoilPH(deviceRecord, masterController);
            }
            default: {
                return null;
            }
        }
    }
}
