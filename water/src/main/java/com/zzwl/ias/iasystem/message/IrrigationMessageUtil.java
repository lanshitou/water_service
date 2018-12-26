package com.zzwl.ias.iasystem.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.IrrigationTaskRecord;
import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTaskState;
import com.zzwl.ias.vo.IrrigationTaskStateVo;


/**
 * Description:
 * User: HuXin
 * Date: 2018-09-30
 * Time: 10:52
 */
public class IrrigationMessageUtil {

    /** 根据灌溉任务的状态构造消息并向用户发送
     * @param task 灌溉任务
     */
    public static void createTaskStateChangeMsg(IrrigationTaskStateVo task) {
        if (task.getStatus() != NormalIrrigationTaskState.STATE_STARTING_VALVES
                && task.getStatus() != NormalIrrigationTaskState.STATE_FINISH){
            return;
        }
        MessageDO messageDO = new MessageDO();
        messageDO.setCategory(MessageConstant.MSG_CAT_IRRIGATION);
        messageDO.setType(MessageConstant.MSG_TYPE_IRRIGATION);
        if (task.getStatus() == NormalIrrigationTaskState.STATE_STARTING_VALVES) {
            messageDO.setTitle("灌溉任务开始执行");
        } else {
            messageDO.setTitle("灌溉任务执行完成");
        }
        messageDO.setSummary(MessageUtil.getIasMsgLocation(task.getIasId(), null, task.getFarmlandId(), task.getIrriAreaId(), null));
        String extension;
        try {
            extension = AppContext.objectMapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            return;
        }
        messageDO.setExtension(extension);

        MessageUtil.sendIasMsg(messageDO, task.getIasId(), task.getFarmlandId());
    }
}
