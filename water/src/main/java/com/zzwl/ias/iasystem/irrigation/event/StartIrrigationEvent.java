package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:启动灌溉任务事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 11:02
 */
public class StartIrrigationEvent extends IrrigationEvent {
    public StartIrrigationEvent(NormalIrrigationTask task) {
        super(task);
    }

    @Override
    public int getTargetType() {
        return TARGET_TYPE_TASK;
    }
}
