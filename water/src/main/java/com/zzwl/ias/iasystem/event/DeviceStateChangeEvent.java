package com.zzwl.ias.iasystem.event;

import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.common.SensorValue;

/**
 * Created by HuXin on 2018/1/12.
 */
public class DeviceStateChangeEvent extends IasEvent {
    private long id;
    private int iaSystemId;
    private int oldState;
    private SensorValue[] oldSensorValues;
    private int newState;
    private SensorValue[] newSensorValues;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOldState() {
        return oldState;
    }

    public void setOldState(int oldState) {
        this.oldState = oldState;
    }

    public SensorValue[] getOldSensorValues() {
        return oldSensorValues;
    }

    public void setOldSensorValues(SensorValue[] oldSensorValues) {
        this.oldSensorValues = oldSensorValues;
    }

    public int getNewState() {
        return newState;
    }

    public void setNewState(int newState) {
        this.newState = newState;
    }

    public SensorValue[] getNewSensorValues() {
        return newSensorValues;
    }

    public void setNewSensorValues(SensorValue[] newSensorValues) {
        this.newSensorValues = newSensorValues;
    }

    public boolean isChanged() {
        if (oldState != newState) {
            return true;
        }

        if (oldSensorValues == null) {
            //设备不是传感器
            return false;
        }

        for (int i = 0; i < oldSensorValues.length; i++) {
            if (oldSensorValues[i].getValue() != newSensorValues[i].getValue()) {
                return true;
            }
        }
        return false;

//        if (oldState == DeviceState.DEV_STATE_OFFLINE) {
//            return false;
//        }
//
//        if (oldSensorValues == null) {
//            return false;
//        }
//
//        for (int i = 0; i < newSensorValues.length; i++) {
//            if (newSensorValues[i].getValue() == Integer.MAX_VALUE) {
//                return false;
//            }
//        }
//
//        for (int i = 0; i < oldSensorValues.length; i++) {
//            if (oldSensorValues[i].getValue() != newSensorValues[i].getValue()) {
//                return true;
//            }
//        }
//        return false;
    }

    public void setIaSystemId(int iaSystemId) {
        this.iaSystemId = iaSystemId;
    }

    @Override
    public Integer getIaSystemId() {
        return iaSystemId;
    }
}
