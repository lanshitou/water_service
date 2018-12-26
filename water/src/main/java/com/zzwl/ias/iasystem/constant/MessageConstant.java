package com.zzwl.ias.iasystem.constant;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-11
 * Time: 15:12
 */
public class MessageConstant {
	public final static byte MSG_NOT_READ = 0;
	public final static byte MSF_READ = 1;

	//废弃
	public final static byte MSG_TYPE_NORMAL = 1;
	public final static byte MSG_TYPE_URL = 2;
	public final static byte MSG_TYPE_THRESHOLD_ALARM = 3;

	//消息的分类
	public final static int MSG_CAT_NOTIFICATION = 1;               //通知消息
	public final static int MSG_CAT_IRRIGATION = 2;                 //灌溉类消息
	public final static int MSG_CAT_OFFLINE = 3;                    //设备离线类消息
	public final static int MSG_CAT_WARNING = 4;                    //告警类消息

	//消息类型
	public final static int MSG_TYPE_NOTIFY_WEB = 1;                            //网页通知消息
	public final static int MSG_TYPE_IRRIGATION = 10;                           //灌溉任务状态更新消息
	public final static int MSG_TYPE_IRRIGATION_FAIL = 11;					   //灌溉任务失败告警消息
	public final static int MSG_TYPE_DEVICE_OFFLINE = 20;                      //设备离线告警消息
	public final static int MSG_TYPE_DEVICE_ONLINE = 21;                       //设备离线告警消除消息
	public final static int MSG_TYPE_THRESHOLD_WARNING_PRODUCED = 30;        //阈值告警产生消息
	public final static int MSG_TYPE_THRESHOLD_WARNING_CLEARED = 31;         //阈值告警消除消息

}
