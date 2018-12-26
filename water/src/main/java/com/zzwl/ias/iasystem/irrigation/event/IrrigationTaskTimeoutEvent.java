package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:灌溉任务运行时间到达事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 11:06
 */
public class IrrigationTaskTimeoutEvent extends IrrigationEvent {
    public IrrigationTaskTimeoutEvent(NormalIrrigationTask task) {
        super(task);
    }

    @Override
    public int getTargetType() {
        return TARGET_TYPE_TASK;
    }
}
