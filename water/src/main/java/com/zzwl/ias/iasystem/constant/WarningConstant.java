package com.zzwl.ias.iasystem.constant;

import com.zzwl.ias.iasystem.irrigation.event.StartIrrigationEvent;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-09
 * Time: 8:32
 */
public class WarningConstant {
    //告警类别
    public final static int WARNING_CAT_THRESHOLD = 1;          //阈值告警
    public final static int WARNING_CAT_IRRIGATION = 2;         //灌溉任务告警
    public final static int WARNING_CAT_OFFLINE = 3;            //离线告警
    public final static int WARNING_CAT_OTHER = 4;              //其他告警

    //告警种类
    public final static int WARNING_NONE = 0;               //没有告警
    public final static int OVER_UPPER_LIMIT_WARN = 1;     //上限告警
    public final static int UNDER_LOWER_LIMIT_WARN = 2;    //下限告警
    public final static int DEVICE_OFFLINE = 3;             //设备离线告警
    public final static int IRRIGATION_FAIL = 4;            //灌溉任务执行失败

    //告警状态
    public final static byte WARNING_STATUS_TRIGGERED = 1;
    public final static byte WARNING_STATUS_RELEASED = 2;

    //告警消除的原因
    public final static int RELEASE_TYPE_USER_CLEAR = 0;
    public final static int RELEASE_TYPE_UNDER_UPPER_LIMIT = 1;
    public final static int RELEASE_TYPE_OVER_LOWER_LIMIT = 2;
    public final static int RELEASE_TYPE_USER_CHANGE_CONFIG = 3;
    public final static int RELEASE_TYPE_DEVICE_OFFLINE = 4;
    public final static int RELEASE_TYPE_DEVICE_ONLINE = 5;

    //告警级别
    public final static int WARNING_LEVEL_LOW = 1;
    public final static int WARNING_LEVEL_NORMAL = 2;
    public final static int WARNING_LEVEL_HIGH = 3;

    //告警参数
    public final static int DEVICE_OFFLINE_WARNING_TIME = 1 * 60;  //设备离线告警检测时间
}
