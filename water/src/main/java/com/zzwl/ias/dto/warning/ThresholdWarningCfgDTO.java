package com.zzwl.ias.dto.warning;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-18
 * Time: 14:45
 */
public class ThresholdWarningCfgDTO {
    private Integer alarmType;
    private Integer threshold;

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}
