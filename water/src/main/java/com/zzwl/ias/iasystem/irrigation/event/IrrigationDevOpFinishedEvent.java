package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;

/**
 * Description:设备操作完成事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 11:10
 */
public class IrrigationDevOpFinishedEvent extends IrrigationEvent {
    private ReqOperateDevice request;
    private int targetType;

    public IrrigationDevOpFinishedEvent(int iaSystemId, ReqOperateDevice request) {
        super(iaSystemId);
        this.request = request;
        targetType = TARGET_TYPE_EXECUTOR;
    }

    public IrrigationDevOpFinishedEvent(NormalIrrigationTask task, ReqOperateDevice request) {
        super(task);
        this.request = request;
        targetType = TARGET_TYPE_TASK;
    }

    public ReqOperateDevice getRequest() {
        return request;
    }

    @Override
    public int getTargetType() {
        return targetType;
    }
}
