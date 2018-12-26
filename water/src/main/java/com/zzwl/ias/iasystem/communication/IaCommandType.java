package com.zzwl.ias.iasystem.communication;

/**
 * Created by HuXin on 2017/8/29.
 */
public interface IaCommandType {

    /**
     * 启动设备
     */
    int START_DEVICE = 1;

    /**
     * 停止设备
     */
    int STOP_DEVICE = 2;

    /**
     * 设备状态更新
     */
    int DEVICE_INFO_UPDATE = 3;

    /**
     * 查询设备状态
     */
    int QUERY_DEVICE_INFO = 4;

    /**
     * 保活命令
     */
    int KEEP_ALIVE = 5;

    /**
     * 网络连接建立通知
     */
    int CONNECTION_SETUP = 6;

    /**
     * 控制器状态信息上报
     */
    int CTLER_INFO_UPDATE = 0x14;

}
