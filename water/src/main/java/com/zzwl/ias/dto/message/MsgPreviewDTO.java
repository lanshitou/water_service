package com.zzwl.ias.dto.message;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-20
 * Time: 16:39
 */
public class MsgPreviewDTO {
    private SingleMsgPreviewDTO notify;
    private SingleMsgPreviewDTO irrigate;
    private SingleMsgPreviewDTO offline;
    private SingleMsgPreviewDTO alarm;

    public SingleMsgPreviewDTO getNotify() {
        return notify;
    }

    public void setNotify(SingleMsgPreviewDTO notify) {
        this.notify = notify;
    }

    public SingleMsgPreviewDTO getIrrigate() {
        return irrigate;
    }

    public void setIrrigate(SingleMsgPreviewDTO irrigate) {
        this.irrigate = irrigate;
    }

    public SingleMsgPreviewDTO getOffline() {
        return offline;
    }

    public void setOffline(SingleMsgPreviewDTO offline) {
        this.offline = offline;
    }

    public SingleMsgPreviewDTO getAlarm() {
        return alarm;
    }

    public void setAlarm(SingleMsgPreviewDTO alarm) {
        this.alarm = alarm;
    }
}
