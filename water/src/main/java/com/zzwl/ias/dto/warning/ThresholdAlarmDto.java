package com.zzwl.ias.dto.warning;

import com.zzwl.ias.domain.MessageAlarmRecord;
import com.zzwl.ias.dto.common.IasDeviceAddrDto;
import com.zzwl.ias.dto.message.MessageDtoOld;
import com.zzwl.ias.iasystem.constant.MessageConstant;

import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-13
 * Time: 8:36
 */
public class ThresholdAlarmDto {
    private Integer id;
    private Integer status;
    private Integer level;
    private Long sensorId;
    private Integer dataType;
    private Date occurTime;
    private Date endTime;
    private Integer endReason;
    private Integer threshold;
    private IasDeviceAddrDto sensorOwner;

    public static MessageDtoOld<ThresholdAlarmDto> getMessageFromRecord(MessageAlarmRecord alarm) {
        MessageDtoOld<ThresholdAlarmDto> msg = new MessageDtoOld<>();
        msg.setId(alarm.getId().longValue());
        msg.setType((int) MessageConstant.MSG_TYPE_THRESHOLD_ALARM);
        msg.setTitle(alarm.getTitle());
        msg.setContent(alarm.getContent());
        msg.setRead(alarm.getIsRead() == 1);
        msg.setCreateTime(alarm.getOccurTime());
        msg.setExtension(null);

        ThresholdAlarmDto alarmDto = new ThresholdAlarmDto();
        alarmDto.setId(alarm.getAlarmThresholdId());
        alarmDto.setStatus(alarm.getStatus());
        alarmDto.setLevel(alarm.getLevel().intValue());
        alarmDto.setSensorId(alarm.getSersorId());
        alarmDto.setDataType(alarm.getDataType());
        return msg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEndReason() {
        return endReason;
    }

    public void setEndReason(Integer endReason) {
        this.endReason = endReason;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

    public IasDeviceAddrDto getSensorOwner() {
        return sensorOwner;
    }

    public void setSensorOwner(IasDeviceAddrDto sensorOwner) {
        this.sensorOwner = sensorOwner;
    }
}
