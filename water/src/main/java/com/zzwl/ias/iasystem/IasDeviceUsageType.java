package com.zzwl.ias.iasystem;

/**
 * Description:
 * User: HuXin
 * Date: 2018-04-27
 * Time: 11:20
 */
public class IasDeviceUsageType {
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

    public static boolean isOpDevice(int usageType) {
        if (usageType >= OP_DEV_MIN_VALUE && usageType <= OP_DEV_MAX_VALUE) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSensor(int usageType) {
        if (usageType >= SENSOR_MIN_VALUE && usageType <= SENSOR_MAX_VALUE) {
            return true;
        } else {
            return false;
        }
    }
}
