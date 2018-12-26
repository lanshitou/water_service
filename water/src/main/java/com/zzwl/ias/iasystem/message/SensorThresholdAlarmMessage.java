package com.zzwl.ias.iasystem.message;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.*;
import com.zzwl.ias.dto.common.IasDeviceAddrDto;
import com.zzwl.ias.dto.warning.ThresholdAlarmDto;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.mapper.IasDeviceRecordExtMapper;
import com.zzwl.ias.mapper.MessageAlarmRecordExtMapper;
import com.zzwl.ias.mapper.MessageUserRecordExtMapper;
import com.zzwl.ias.service.MessagePushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-09
 * Time: 17:23
 */
@Component
public class SensorThresholdAlarmMessage {
    private final MessagePushService messagePushService;

    @Autowired
    public SensorThresholdAlarmMessage(MessagePushService messagePushService) {
        this.messagePushService = messagePushService;
    }

    public ThresholdAlarmDto createThresholdAlarmDtoByAlarm(AlarmThresholdRecord alarm) {
        ThresholdAlarmDto dto = new ThresholdAlarmDto();
        dto.setId(alarm.getId());
        dto.setStatus(alarm.getStatus().intValue());
        dto.setSensorId(alarm.getSensorId());
        dto.setDataType(alarm.getDataType());
        dto.setOccurTime(alarm.getOccurTime());
        dto.setEndTime(alarm.getEndTime());
        dto.setEndReason(alarm.getEndReason());
        dto.setThreshold(alarm.getThreshold());

        IasDeviceAddrDto addr = new IasDeviceAddrDto();
        IasDeviceRecord iasDeviceRecord =
                AppContext.getBean(IasDeviceRecordExtMapper.class).selectByDevId(alarm.getSensorId());
        addr.setDevId(iasDeviceRecord.getDevId());
        addr.setType(iasDeviceRecord.getUsageType());
        addr.setIaSystemId(iasDeviceRecord.getIasId());
        addr.setFarmlandId(iasDeviceRecord.getFarmlandId());
        addr.setAreaId(iasDeviceRecord.getIrriAreaId());
        addr.setOpDevId(iasDeviceRecord.getIasDevId());

        dto.setSensorOwner(addr);
        return dto;
    }
}
