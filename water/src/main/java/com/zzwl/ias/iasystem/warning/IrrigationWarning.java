package com.zzwl.ias.iasystem.warning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.IrrigationTaskRecord;
import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.dto.irrigation.IrrigationWarningExtDTO;
import com.zzwl.ias.iasystem.constant.IasObjectType;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.iasystem.constant.WarningConstant;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrriTaskResult;
import com.zzwl.ias.iasystem.message.IrrigationMessageUtil;
import com.zzwl.ias.iasystem.message.MessageUtil;
import com.zzwl.ias.mapper.WarningDOExtMapper;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-30
 * Time: 9:27
 */
@Component
public class IrrigationWarning {

    /** 检查灌溉任务是否产生告警
     * @param task 灌溉任务
     */
    public void checkIrriWarning(IrrigationTaskStateVo task){
        if (task.getResult() == NormalIrriTaskResult.OK
                || task.getResult() == NormalIrriTaskResult.USER_CANCEL_OK){
            return;
        }
        //灌溉任务执行失败，创建告警
        createIrriWarning(task);
    }

    private void createIrriWarning(IrrigationTaskStateVo task){
        //创建告警
        WarningDO warningDO = new WarningDO();
        warningDO.setType(WarningConstant.IRRIGATION_FAIL);
        warningDO.setSubType(0);    //灌溉类告警没有子类型
        warningDO.setLevel(WarningConstant.WARNING_LEVEL_HIGH);
        warningDO.setCleared(false);
        warningDO.setProduceTime(Calendar.getInstance().getTime());

        warningDO.setAddrType(IasObjectType.IRRI_AREA);
        warningDO.setAddrIas(task.getIasId());
        warningDO.setAddrIrriFer(null);
        warningDO.setAddrFarmland(task.getFarmlandId());
        warningDO.setAddrArea(task.getIrriAreaId());
        warningDO.setAddrParentDev(null);
        warningDO.setAddrDev(null);

        String extension;
        try {
            extension = AppContext.objectMapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            return;
        }
        warningDO.setExtension(extension);
        AppContext.getBean(WarningDOExtMapper.class).insert(warningDO);

        //创建告警消息
        MessageDO messageDO = createIrrigationWarningMsg(warningDO);

        //发送告警消息
        MessageUtil.sendIasMsg(messageDO, warningDO.getAddrIas(), warningDO.getAddrFarmland());
    }

    private MessageDO createIrrigationWarningMsg(WarningDO warningDO){
        MessageDO messageDO = new MessageDO();

        messageDO.setIasId(warningDO.getAddrIas());
        messageDO.setCategory(MessageConstant.MSG_CAT_IRRIGATION);
        messageDO.setType(MessageConstant.MSG_TYPE_IRRIGATION_FAIL);
        //生成Title
        messageDO.setTitle("灌溉任务执行失败");
        //生成Summary
        String summary;
        summary = "告警位置:" + WarningUtil.getWarningLocation(warningDO);
        messageDO.setSummary(summary);
        //生成扩展字段
        WarningUtil.createWarningMsgExt(messageDO, warningDO);
        return messageDO;
    }
}
