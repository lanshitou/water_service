package com.zzwl.ias.common;

public class Constants {
    /**
     * 报警结束原因
     */
    public static final int EDN_REASON_UPDATE_THRESOLD = 1; //因修改告警阈值解除告警
    public static final int EDN_REASON_ATUO_NORMAL = 2; //传感器值自动变化至正常值
    public static final int EDN_REASON_SENSOR_OFFLINE = 3; //传感器无值时，原本的阈值告警消除

    /**
     * 消息类型
     */
    public static final byte MESSAGE_SYS = 1; //系统类消息
    public static final byte MESSAGE_ALARM = 2; //告警类消息

    /**
     * 极光推送成功与否
     */
    public static final byte PUSH_SUCCESS = 1; //成功
    public static final byte PUSH_FAIL = 2; //推送失败（我方服务器推到极光服务器时失败）

    /**
     * 防抖时间
     */
    public static final int ANTISHAKE_TIME = 600; //10分钟
    /**
     * 图片上传状态
     */
    public static final String IMG_SUCCESS = "上传成功"; //正常状态
    public static final String IMG_FAILED = "上传失败"; //已丢弃
    public static final String DOT = ".";
    public static final long IMAGE_MAX_SIZE = 1048576*2;
    /**
     * 文件路径需要的常量
     */
    public static final String FILE_PATH_IMAGE = "ias/images/";
}
