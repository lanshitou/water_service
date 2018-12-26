package com.zzwl.ias.iasystem.constant;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-09
 * Time: 7:45
 */
public class DeviceConstant {
    //数据采集类型定义
    public static final int COLLECT_TYPE_PERIOD = 1;
    public static final int COLLECT_TYPE_CHANGE = 2;

    //设备在智慧农业系统中的使用方式
    final public static int OP_DEV_MIN_VALUE = 1;
    final public static int IRRI_FER_PUMP = 1;          //水肥一体化系统中的水泵
    final public static int IRRI_VALVE = 2;             //灌溉区中的阀门
    final public static int IAS_NORMAL_DEVICE = 3;     //智慧农业系统中的普通可操作设备
    final public static int FM_NORMAL_DEVICE = 4;      //农田中的普通可操作设备
    final public static int AREA_NORMAL_DEVICE = 5;    //灌溉区中的普通可操作设备
    final public static int OP_DEV_MAX_VALUE = 5;

    final public static int SENSOR_MIN_VALUE = 101;
    final public static int IAS_SENSOR = 101;             //智慧农业系统的传感器
    final public static int FM_SENSOR = 102;              //农田中的传感器
    final public static int AREA_SENSOR = 103;            //灌溉区中的传感器
    final public static int OP_DEV_SENSOR = 104;          //设备的传感器
    final public static int SENSOR_MAX_VALUE = 104;

    //设备操作相关常量定义
    public static final int DEV_MAX_OP_DURATION = 86400;    //设备运行最长可设置时间：24小时
    public static final int DEV_POSITION_MIN = 0;           //电动阀或卷帘机位置最小值
    public static final int DEV_POSITION_MAX = 100;         //电动阀或卷帘机位置最大值

    //设备操作的来源类型定义
    public static final int OP_DEV_SRC_USER = 1;            //用户手动进行操作
    public static final int OP_DEV_SRC_IRRI_TASK = 2;      //灌溉任务执行对设备进行操作
    public static final int OP_DEV_SRC_ANY = 10;            //任意类型的设备操作来源

}
