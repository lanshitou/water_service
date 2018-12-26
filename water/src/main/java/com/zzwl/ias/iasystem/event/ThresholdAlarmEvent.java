package com.zzwl.ias.iasystem.event;

import com.zzwl.ias.domain.AlarmThresholdRecord;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-09
 * Time: 15:08
 */
public class ThresholdAlarmEvent extends IasEvent {
    private int iasId;
    private AlarmThresholdRecord record;

    public ThresholdAlarmEvent(int iasId, AlarmThresholdRecord alarmThresholdRecord) {
        this.iasId = iasId;
        this.record = alarmThresholdRecord;
    }

    public AlarmThresholdRecord getRecord() {
        return record;
    }

    @Override
    public Integer getIaSystemId() {
        return iasId;
    }
}
