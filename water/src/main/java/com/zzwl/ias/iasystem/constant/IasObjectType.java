package com.zzwl.ias.iasystem.constant;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-15
 * Time: 9:02
 */
public class IasObjectType {
    public static final int IASYSTEM = 200;         //智慧农业系统
    public static final int FARMLAND = 201;         //农田
    public static final int IRRI_AREA = 202;        //灌溉区
    public static final int IRRI_FER = 203;         //水肥一体化子系统

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
}
