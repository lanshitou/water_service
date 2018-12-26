package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.event.IasEvent;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:52
 */
public abstract class IrrigationEvent extends IasEvent {
    public static final int TARGET_TYPE_EXECUTOR = 1;
    public static final int TARGET_TYPE_TASK = 2;

    private Integer iaSystemId;
    private NormalIrrigationTask task;

    public IrrigationEvent(int iaSystemId) {
        task = null;
        this.iaSystemId = iaSystemId;
    }

    public IrrigationEvent(NormalIrrigationTask task) {
        this.task = task;
        iaSystemId = null;
    }

    @Override
    public Integer getIaSystemId() {
        if (task != null) {
            return task.getIaSystemId();
        } else {
            return iaSystemId;
        }
    }

    public NormalIrrigationTask getTask() {
        return task;
    }

    abstract public int getTargetType();

}
