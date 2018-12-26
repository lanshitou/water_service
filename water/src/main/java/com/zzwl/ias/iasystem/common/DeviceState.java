package com.zzwl.ias.iasystem.common;

/**
 * Created by HuXin on 2017/8/28.
 */
public interface DeviceState {
    //############以下状态针对电动阀和卷帘############
    //设备处于完全闭合的状态
    int DEV_STATE_CLOSED = 0;
    //设备处于处于完全打开的状态
    int DEV_STATE_OPENED = 100;
    //状态打开程度未知
    int DEV_STATE_STOPPED = 101;
    //设备正在执行打开动作
    int DEV_STATE_OPENNING = 102;
    //设备证在执行闭合动作
    int DEV_STATE_CLOSING = 103;

    //############以下状态针对水泵、电磁阀、风机、生长灯、加热器、除湿器等只有开、关两种状态的设备############
    /**
     * 设备开启
     */
    int DEV_STATE_ON = 110;
    /**
     * 设备关闭
     */
    int DEV_STATE_OFF = 111;

    //############以下状态针对传感器设备############
    //设备正常
    int DEV_STATE_OK = 120;

    /**
     * 设备离线
     */
    int DEV_STATE_OFFLINE = -1;

}
