package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:灌溉任务可以结束事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 11:08
 */
public class IrrigationTaskFinishableEvent extends IrrigationEvent {
    public IrrigationTaskFinishableEvent(NormalIrrigationTask task) {
        super(task);
    }

    @Override
    public int getTargetType() {
        return TARGET_TYPE_TASK;
    }
}
