package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:停止灌溉任务事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 11:15
 */
public class StopIrrigationTaskEvent extends IrrigationEvent {
    private boolean userCancel = false;

    public StopIrrigationTaskEvent(NormalIrrigationTask task) {
        super(task);
    }

    @Override
    public int getTargetType() {
        return TARGET_TYPE_TASK;
    }

    public boolean isUserCancel() {
        return userCancel;
    }

    public void setUserCancel(boolean userCancel) {
        this.userCancel = userCancel;
    }
}
