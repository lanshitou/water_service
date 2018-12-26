package com.zzwl.ias.iasystem.warning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.AlarmConfigRecord;
import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.dto.warning.ThresholdWarningExtDTO;
import com.zzwl.ias.iasystem.IasObjectAddr;
import com.zzwl.ias.iasystem.IasSensor;
import com.zzwl.ias.iasystem.common.SensorValue;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.iasystem.constant.WarningConstant;
import com.zzwl.ias.iasystem.message.MessageUtil;
import com.zzwl.ias.iasystem.message.WarningMessageExt;
import com.zzwl.ias.mapper.AlarmConfigRecordExtMapper;
import com.zzwl.ias.mapper.WarningDOExtMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-08
 * Time: 11:59
 */
@Component
public class SensorThresholdWarning {
    private final WarningDOExtMapper warningDOExtMapper;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Autowired
    public SensorThresholdWarning(WarningDOExtMapper warningDOExtMapper){
        this.warningDOExtMapper = warningDOExtMapper;
    }

    /** 检查是否有传感器采集值门限告警
     * @param sensor 传感器
     * @param sensorValues 采集值
     * @param reason 告警解除原因。当告警消除时，如果该字段不为null，则使用该解除原因
     */
    public void checkThresholdWarning(IasSensor sensor, SensorValue[] sensorValues, Integer reason){
        for (SensorValue sensorValue : sensorValues){
            //设备离线或者没有采集值时不进行处理,当产生设备离线告警时消除阈值告警
            if (sensorValue.getValue() == SensorValue.VALUE_INVALID) {
                continue;
            }

            //获取告警配置
            AlarmConfigRecord alarmConfig =
                    AppContext.getBean(AlarmConfigRecordExtMapper.class).selectByDevIdAndType(sensor.getId(), sensorValue.getType());
            if (alarmConfig == null) {
                continue;
            }
            Integer upperLimit = alarmConfig.getAlarmwarnUplimit();
            Integer lowerLimit = alarmConfig.getAlarmwarnLowlimit();

            //获取当前告警
            List<WarningDO> warningDOS = warningDOExtMapper.listCurrWarningByAddrAndType(sensor.getAddr(), null, sensorValue.getType());
            WarningDO current = null;
            if (warningDOS.size() != 0){
                current = warningDOS.get(0);
            }

            if (upperLimit != null && sensorValue.getValue() > upperLimit) {
                //上限告警
                if (current == null || current.getType() == WarningConstant.UNDER_LOWER_LIMIT_WARN) {
                    if (current != null && current.getType() == WarningConstant.UNDER_LOWER_LIMIT_WARN) {
                        //当前告警为下限告警，则需要消除当前告警
                        if (reason != null){
                            releaseWarning(current, WarningConstant.RELEASE_TYPE_USER_CHANGE_CONFIG);
                        }
                        else {
                            releaseWarning(current, WarningConstant.RELEASE_TYPE_OVER_LOWER_LIMIT);
                        }

                    }
                    createWarning(WarningConstant.OVER_UPPER_LIMIT_WARN, sensor, sensorValue, upperLimit);
                }
            } else if (lowerLimit != null && sensorValue.getValue() < lowerLimit) {
                //下限告警
                if (current == null || current.getType() == WarningConstant.OVER_UPPER_LIMIT_WARN) {
                    if (current != null && current.getType() == WarningConstant.OVER_UPPER_LIMIT_WARN) {
                        //当前告警为上限告警，则需要消除当前告警
                        if (reason != null){
                            releaseWarning(current, reason);
                        }
                        else {
                            releaseWarning(current, WarningConstant.RELEASE_TYPE_UNDER_UPPER_LIMIT);
                        }
                    }
                    createWarning(WarningConstant.UNDER_LOWER_LIMIT_WARN, sensor, sensorValue, lowerLimit);
                }
            } else {
                //没有告警
                if (current != null && !current.getCleared()) {
                    //消除当前告警
                    if (current.getType() == WarningConstant.OVER_UPPER_LIMIT_WARN) {
                        releaseWarning(current, reason);
                    } else {
                        releaseWarning(current, WarningConstant.RELEASE_TYPE_OVER_LOWER_LIMIT);
                    }
                }
            }
        }
    }

    private void createWarning(Integer type, IasSensor sensor, SensorValue value, Integer threshold){
        IasObjectAddr addr = sensor.getAddr();
        WarningDO warningDO = new WarningDO();
        warningDO.setType(type);
        warningDO.setSubType(value.getType());
        warningDO.setLevel(WarningConstant.WARNING_LEVEL_NORMAL);
        warningDO.setCleared(false);
        warningDO.setProduceTime(Calendar.getInstance().getTime());
        warningDO.setAddrType(addr.getType());
        warningDO.setAddrIas(addr.getIasId());
        warningDO.setAddrIrriFer(addr.getIrriFerId());
        warningDO.setAddrFarmland(addr.getFarmlandId());
        warningDO.setAddrArea(addr.getIrriAreaId());
        warningDO.setAddrParentDev(addr.getParentDevId());
        warningDO.setAddrDev(addr.getDevId());
        ThresholdWarningExtDTO extDTO = new ThresholdWarningExtDTO();
        extDTO.setSensorId(sensor.getId());
        extDTO.setDataType(value.getType());
        extDTO.setThreshold(threshold);
        try {
            String ext = AppContext.objectMapper.writeValueAsString(extDTO);
            warningDO.setExtension(ext);
        } catch (JsonProcessingException e) {
            return;
        }
        warningDOExtMapper.insert(warningDO);

        //发送告警消息
        MessageDO messageDO = createThresholdWarningMsg(warningDO);
        MessageUtil.sendIasMsg(messageDO, warningDO.getAddrIas(), warningDO.getAddrFarmland());
    }

    private void releaseWarning(WarningDO warningDO, Integer reason){
        warningDO.setCleared(true);
        warningDO.setClearTime(Calendar.getInstance().getTime());
        warningDO.setClearReason(reason);
        warningDOExtMapper.updateByPrimaryKey(warningDO);

        //发送告警消息
        MessageDO messageDO = createThresholdWarningMsg(warningDO);
        MessageUtil.sendIasMsg(messageDO, warningDO.getAddrIas(), warningDO.getAddrFarmland());
    }

    private MessageDO createThresholdWarningMsg(WarningDO warningDO){
        MessageDO messageDO = new MessageDO();
        messageDO.setCategory(MessageConstant.MSG_CAT_WARNING);
        messageDO.setType(MessageConstant.MSG_TYPE_THRESHOLD_WARNING_PRODUCED);

        //生成Title
        String title;
        if (warningDO.getCleared()){
            title = SensorValue.getTypeName(warningDO.getSubType());
            if (warningDO.getType() == WarningConstant.OVER_UPPER_LIMIT_WARN){
                title += "高于上限预警解除";
            }else{
                title += "低于下限预警解除";
            }
        }else{
            title = SensorValue.getTypeName(warningDO.getSubType());
            if (warningDO.getType() == WarningConstant.OVER_UPPER_LIMIT_WARN){
                title += "高于上限预警值";
            }else{
                title += "低于下限预警值";
            }
        }
        messageDO.setTitle(title);

        //生成Summary
        String summary;
        if (warningDO.getCleared()){
            summary = "消除时间:" + dateFormat.format(warningDO.getClearTime());
        }else {
            summary = "告警时间:" + dateFormat.format(warningDO.getProduceTime());
        }
        summary += "告警位置:" + WarningUtil.getWarningLocation(warningDO);
        messageDO.setSummary(summary);

        WarningMessageExt ext = new WarningMessageExt();
        ext.setWarningId(warningDO.getId());
        String extension;
        try {
            extension = AppContext.objectMapper.writeValueAsString(ext);
        } catch (JsonProcessingException e) {
            return null;
        }
        messageDO.setExtension(extension);
        return messageDO;
    }
}
