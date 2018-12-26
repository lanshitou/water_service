package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.iasystem.common.SensorValue;

/**
 * Created by HuXin on 2018/1/11.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    long id;
    int type;
    String name;
    int state;
    SensorValue[] sensorValues;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public SensorValue[] getSensorValues() {
        return sensorValues;
    }

    public void setSensorValues(SensorValue[] sensorValues) {
        this.sensorValues = sensorValues;
    }
}
