package com.zzwl.ias.iasystem.device;


import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.ControllerStatusRecord;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.common.DeviceType;
import com.zzwl.ias.iasystem.common.SensorValue;
import com.zzwl.ias.iasystem.communication.analyzer.ControllerStatusInfo;
import com.zzwl.ias.iasystem.constant.DeviceConstant;
import com.zzwl.ias.vo.DeviceConfigVo;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by HuXin on 2017/8/30.
 */
public class Controller {
    static public final int MIN_CTLER_ID = 0;
    static public final int MAX_CTLER_ID = 60;

    private DeviceRecord deviceRecord;
    private MasterController masterController;
    private byte ctlerId;
    private int state;
    private LinkedList<Device> devices;
    private ControllerState controllerState;

    private LinkedList<SensorValue> boardStatus;

    public Controller(DeviceRecord deviceRecord, MasterController masterController) {
        this.deviceRecord = deviceRecord;
        this.masterController = masterController;
        ctlerId = DeviceAddr.getControllerIdById(deviceRecord.getId());
        state = DeviceState.DEV_STATE_OFFLINE;
        devices = new LinkedList<>();
        controllerState = new ControllerState(this);
        if (ctlerId == 0) {
            boardStatus = new LinkedList<>();
        } else {
            boardStatus = null;
        }
    }

    public void updateBoardStatus(ControllerStatusInfo info) {
        boolean found;
        for (SensorValue sensorValue : info.getSensorValues()) {
            found = false;
            for (SensorValue localValue : boardStatus) {
                if (localValue.getType() == sensorValue.getType()) {
                    found = true;
                    localValue.setValue(sensorValue.getValue());
                    break;
                }
            }

            if (!found) {
                boardStatus.add(sensorValue);
            }
        }

        ControllerStatusRecord record = new ControllerStatusRecord();
        record.setDeviceId(deviceRecord.getId());
        for (SensorValue value : boardStatus) {
            switch (value.getType()) {
                case SensorValue.BAT_VOL: {
                    record.setBatteryVol(value.getValue());
                    break;
                }
                case SensorValue.BAT_WITH_SOLAR_VOL: {
                    record.setBatteryWithSolarVol(value.getValue());
                    break;
                }
                case SensorValue.SOLAR_VOL: {
                    record.setSolarPanelVol(value.getValue());
                    break;
                }
                case SensorValue.SIGNAL_STR: {
                    record.setSignalStrength(value.getValue());
                    break;
                }
                case SensorValue.BOARD_TEMP: {
                    record.setBoardTemp(value.getValue());
                    break;
                }
                default: {
                    //do nothing
                }
            }
        }
        try {
            AppContext.deviceService.recordControllerStatus(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isLowConsume() {
        if (deviceRecord.getLowConsume() == null) {
            return false;
        } else {
            return deviceRecord.getLowConsume();
        }
    }

    public byte getCtlerId() {
        return ctlerId;
    }

    public long getId() {
        return deviceRecord.getId();
    }


    public Device getDevice(long id) {
        for (Device dev : devices) {
            if (dev.getId() == id) {
                return dev;
            }
        }
        return null;
    }

    public ErrorCode addDevice(DeviceRecord deviceRecord) {
        Device dev = getDevice(deviceRecord.getId());
        if (dev != null) {
            return ErrorCode.DEVICE_EXIST;
        }

        dev = DeviceFactory.createDevice(deviceRecord, masterController);
        if (dev == null) {
            return ErrorCode.DEV_TYPE_INVALID;
        }
        devices.add(dev);
        return ErrorCode.OK;
    }

    public ErrorCode delDevice(long id) {
        for (Device dev : devices) {
            if (dev.getId() == id) {
                devices.remove(dev);
                return ErrorCode.OK;
            }
        }
        return ErrorCode.IAS_DEVICE_NOT_EXIST;
    }

    public ErrorCode updateDevice(DeviceConfigVo deviceConfigVo) {
        DeviceAddr deviceAddr = new DeviceAddr(deviceConfigVo.getId());
        if (deviceAddr.getDevType() == DeviceType.DEV_CONTROLLER) {
            deviceRecord.setName(deviceConfigVo.getName());
            if (deviceAddr.getCtlerId() == 0) {
                if (deviceConfigVo.getKeepAlive() != null) {
                    deviceRecord.setKeepAlive(deviceConfigVo.getKeepAlive());
                }
                if (deviceConfigVo.getLowConsume() != null) {
                    deviceRecord.setLowConsume(deviceConfigVo.getLowConsume());
                }
            }
        } else {
            Device device = getDevice(deviceConfigVo.getId());
            if (device == null) {
                return ErrorCode.DEVICE_NOT_EXIST;
            }
            device.setName(deviceConfigVo.getName());
        }

        return ErrorCode.OK;
    }

    public int getSubDeviceNumer() {
        return devices.size();
    }

    public void setState(int newState) {
        if (state == DeviceState.DEV_STATE_OK && newState == DeviceState.DEV_STATE_OFFLINE) {
            //需要设置所有的设备离线
            for (Device device : devices) {
                device.updateDevInfo(DeviceState.DEV_STATE_OFFLINE, null);
            }
        }
        state = newState;
    }

    public Device getDeviceByCmdSn(short cmdSn) {
        for (Device dev : devices) {
            if (dev.isCmdSnMatch(cmdSn)) {
                return dev;
            }
        }
        return null;
    }

    public ControllerState getControllerState() {
        return controllerState;
    }

    Integer getKeepAlive() {
        return deviceRecord.getKeepAlive();
    }

    @Override
    public String toString() {
        return "Controller{" +
                "deviceRecord=" + deviceRecord +
                ", devices=" + devices +
                '}';
    }

    public void collectSensorData(Date date) {
        for (Device device : devices) {
            device.collectSensorData(date, DeviceConstant.COLLECT_TYPE_PERIOD);
        }
    }
}
