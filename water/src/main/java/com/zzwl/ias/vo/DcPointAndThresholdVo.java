package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.dto.iasystem.DcPointDTO;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DcPointAndThresholdVo {
    DcPointDTO dcPoint;
    List<ThresholdAlarmConfigVo> thresholdAlarmConfigs;

    public DcPointDTO getDcPoint() {
        return dcPoint;
    }

    public void setDcPoint(DcPointDTO dcPoint) {
        this.dcPoint = dcPoint;
    }

    public List<ThresholdAlarmConfigVo> getThresholdAlarmConfigs() {
        return thresholdAlarmConfigs;
    }

    public void setThresholdAlarmConfigs(List<ThresholdAlarmConfigVo> thresholdAlarmConfigs) {
        this.thresholdAlarmConfigs = thresholdAlarmConfigs;
    }
}
