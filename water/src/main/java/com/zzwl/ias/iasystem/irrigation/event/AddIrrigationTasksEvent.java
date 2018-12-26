package com.zzwl.ias.iasystem.irrigation.event;

import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;

/**
 * Description:添加灌溉任务事件
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:53
 */
public class AddIrrigationTasksEvent extends IrrigationEvent {
    private ReqUpdateNormalIrrigationDTO requestVo;

    public AddIrrigationTasksEvent(ReqUpdateNormalIrrigationDTO requestVo) {
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
