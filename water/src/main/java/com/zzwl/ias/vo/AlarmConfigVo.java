package com.zzwl.ias.vo;

import com.zzwl.ias.domain.AlarmConfigRecord;

public class AlarmConfigVo {
    public static final int DATE_IS_OK = 0;
    public static final int OVER_UPPER_LMMIT = 1;
    public static final int UNDER_LOWER_LMMIT = 2;
    private Integer upperLimmit;
    private Integer lowerLimmit;
    private Integer status;

    public AlarmConfigVo(AlarmConfigRecord alarmConfigRecord) {
        if (alarmConfigRecord != null) {
            upperLimmit = alarmConfigRecord.getAlarmwarnUplimit();
            lowerLimmit = alarmConfigRecord.getAlarmwarnLowlimit();
            status = DATE_IS_OK;
        }
    }

    public Integer getUpperLimmit() {
        return upperLimmit;
    }

    public void setUpperLimmit(Integer upperLimmit) {
        this.upperLimmit = upperLimmit;
    }

    public Integer getLowerlimmit() {
        return lowerLimmit;
    }

    public void setLowerlimmit(Integer lowerlimmit) {
        this.lowerLimmit = lowerlimmit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
