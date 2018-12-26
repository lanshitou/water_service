package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:灌溉任务阀门已经开启事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 11:04
 */
public class IrrigationTaskValvesStartedEvent extends IrrigationEvent {
    public IrrigationTaskValvesStartedEvent(NormalIrrigationTask task) {
        super(task);
    }

    @Override
    public int getTargetType() {
        return TARGET_TYPE_EXECUTOR;
    }
}
