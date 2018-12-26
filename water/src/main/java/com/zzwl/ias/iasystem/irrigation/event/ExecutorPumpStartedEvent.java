package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:水泵已经开启事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 11:05
 */
public class ExecutorPumpStartedEvent extends IrrigationEvent {
    public ExecutorPumpStartedEvent(NormalIrrigationTask task) {
        super(task);
    }

    @Override
    public int getTargetType() {
        return TARGET_TYPE_EXECUTOR;
    }
}
