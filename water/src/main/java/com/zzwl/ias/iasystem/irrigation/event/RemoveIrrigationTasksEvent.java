package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;

/**
 * Description:删除灌溉任务事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 10:58
 */
public class RemoveIrrigationTasksEvent extends IrrigationEvent {
    private ReqUpdateNormalIrrigationDTO requestVo;

    public RemoveIrrigationTasksEvent(ReqUpdateNormalIrrigationDTO requestVo) {
        super(requestVo.getIaSystemId());
        this.requestVo = requestVo;
    }

    public ReqUpdateNormalIrrigationDTO getRequestVo() {
        return requestVo;
    }

    @Override
    public int getTargetType() {
        return TARGET_TYPE_EXECUTOR;
    }
}
