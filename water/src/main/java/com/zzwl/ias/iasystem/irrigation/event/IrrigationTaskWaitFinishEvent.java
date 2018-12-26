package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:灌溉任务等待结束事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 11:07
 */
public class IrrigationTaskWaitFinishEvent extends IrrigationEvent {
    public IrrigationTaskWaitFinishEvent(NormalIrrigationTask task) {
        super(task);
    }

    @Override
    public int getTargetType() {
        return TARGET_TYPE_EXECUTOR;
    }
}
