package com.zzwl.ias.dto.warning;

import com.zzwl.ias.dto.iasystem.DcPointDTO;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-18
 * Time: 18:10
 */
public class ThresholdWarningCfgExtDTO {
    private LinkedList<ThresholdWarningCfgDTO> configs;
    private DcPointDTO dcPoint;

    public LinkedList<ThresholdWarningCfgDTO> getConfigs() {
        return configs;
    }

    public void setConfigs(LinkedList<ThresholdWarningCfgDTO> configs) {
        this.configs = configs;
    }

    public DcPointDTO getDcPoint() {
        return dcPoint;
    }

    public void setDcPoint(DcPointDTO dcPoint) {
        this.dcPoint = dcPoint;
    }
}
