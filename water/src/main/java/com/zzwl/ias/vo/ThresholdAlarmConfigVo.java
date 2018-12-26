package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ThresholdAlarmConfigVo {
    public static final int NORMAL = 0;
    public static final int OVER_UPPER_LIMIT = 1;
    public static final int UNDER_LOWER_LIMIT = 2;
    public static final int NO_VALUE = 3;

    public static final byte WARNING = 1;
    public static final byte RELEASE_WARNING = 2;

    Integer alarmType;
    Integer threshold;

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
