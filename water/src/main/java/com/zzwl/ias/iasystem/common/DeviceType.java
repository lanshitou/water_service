package com.zzwl.ias.iasystem.common;

import com.zzwl.ias.vo.DevTypeVo;

import java.util.LinkedList;

/**
 * Created by HuXin on 2017/8/26.
 */
public class DeviceType {
    /**
     * 控制器
     */
    public static final byte DEV_CONTROLLER = 0;

    /**
     * 可操作设备最小编号
     */
    public static final byte OP_DEV_TYPE_MIN_ID = 1;

    /**
     * 电动阀
     */
    public static final byte DEV_ELEC_VALVE = 1;

    /**
     * 电磁阀
     */
    public static final byte DEV_MAGNETIC_VALVE = 2;

    /**
     * 水泵
     */
    public static final byte DEV_PUMP = 3;

    /**
     * 风机
     */
    public static final byte DEV_FAN = 4;

    /**
     * 卷帘机
     */
    public static final byte DEV_SHUTTER = 5;

    /**
     * 生长灯
     */
    public static final byte DEV_GROW_LIGHT = 6;

    /**
     * 加热器
     */
    public static final byte DEV_HEALER = 7;

    /**
     * 除湿器
     */
    public static final byte DEV_DEHUMIDIFIER = 8;

    /**
     * 脉冲电磁阀
     */
    public static final byte DEV_PULSE_VALVE = 9;

    /**
     * 可操作设备最大编号
     */
    public static final byte OP_DEV_TYPE_MAX_ID = 9;

    /**
     * 传感器最小编号
     */
    public static final byte SEN_TYPE_MIN_ID = 64;

    /**
     * 土壤湿度传感器
     */
    public static final byte SEN_SOIL_HUMI = 64;

    /**
     * 土壤温度传感器
     */
    public static final byte SEN_SOIL_TEMP = 65;

    /**
     * 土壤温湿度传感器
     */
    public static final byte SEN_SOIL_H_T = 66;

    /**
     * 空气湿度传感器
     */
    public static final byte SEN_AIR_HUMI = 67;

    /**
     * 空气温度传感器
     */
    public static final byte SEN_AIR_TEMP = 68;

    /**
     * 空气温湿度传感器
     */
    public static final byte SEN_AIR_H_T = 69;

    /**
     * 光照强度感器
     */
    public static final byte SEN_LIGHT_ILLU = 70;

    /**
     * 二氧化碳传感器
     */
    public static final byte SEN_CO2 = 71;

    /**
     * 空气温湿度和光照强度传感器
     */
    public static final byte SEN_H_T_I = 72;

    /**
     * 悬挂式空气温湿度传感器
     */
    public static final byte SEN_AIR_H_T_HANG = 73;

    /**
     * 土壤温湿度、电导率、盐分传感器
     */
    public static final byte SENSOR_SOIL_HTCS = 74;

    /**
     * 土壤PH值传感器
     */
    public static final byte SENSOR_SOIL_PH = 75;

    /**
     * 传感器最大编号
     */
    public static final byte SEN_TYPE_MAX_ID = 75;

    private static LinkedList<DevTypeVo> deviceTypes = null;

    public static LinkedList<DevTypeVo> getDevType() {
        if (deviceTypes == null) {
            synchronized (DeviceType.class) {
                if (deviceTypes == null) {
                    LinkedList<DevTypeVo> temp = new LinkedList<>();
                    temp.add(new DevTypeVo((int) DEV_CONTROLLER, "控制器"));
                    temp.add(new DevTypeVo((int) DEV_ELEC_VALVE, "电动阀"));
                    temp.add(new DevTypeVo((int) DEV_MAGNETIC_VALVE, "电磁阀"));
                    temp.add(new DevTypeVo((int) DEV_PUMP, "水泵"));
                    temp.add(new DevTypeVo((int) DEV_FAN, "风机"));
                    temp.add(new DevTypeVo((int) DEV_SHUTTER, "卷帘机"));
                    temp.add(new DevTypeVo((int) DEV_GROW_LIGHT, "生长灯"));
                    temp.add(new DevTypeVo((int) DEV_HEALER, "加热器"));
                    temp.add(new DevTypeVo((int) DEV_DEHUMIDIFIER, "除湿器"));
                    temp.add(new DevTypeVo((int) DEV_PULSE_VALVE, "脉冲电磁阀"));
                    temp.add(new DevTypeVo((int) SEN_SOIL_HUMI, "土壤湿度传感器"));
                    temp.add(new DevTypeVo((int) SEN_SOIL_TEMP, "土壤温度传感器"));
                    temp.add(new DevTypeVo((int) SEN_SOIL_H_T, "土壤温湿度传感器"));
                    temp.add(new DevTypeVo((int) SEN_AIR_HUMI, "空气湿度传感器"));
                    temp.add(new DevTypeVo((int) SEN_AIR_TEMP, "空气温度传感器"));
                    temp.add(new DevTypeVo((int) SEN_AIR_H_T, "空气温湿度传感器"));
                    temp.add(new DevTypeVo((int) SEN_LIGHT_ILLU, "光照强度传感器"));
                    temp.add(new DevTypeVo((int) SEN_CO2, "二氧化碳浓度传感器"));
                    temp.add(new DevTypeVo((int) SEN_H_T_I, "空气温湿度、光照强度三合一传感器"));
                    temp.add(new DevTypeVo((int) SEN_AIR_H_T_HANG, "悬挂式空气温湿度传感器"));
                    temp.add(new DevTypeVo((int) SENSOR_SOIL_HTCS, "土壤温湿度、电导率、盐分传感器"));
                    temp.add(new DevTypeVo((int) SENSOR_SOIL_PH, "土壤PH值传感器 "));
                    deviceTypes = temp;
                }
            }
        }
        return deviceTypes;
    }
}
