package com.zzwl.ias.dto.warning;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.RequestDTO;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-25
 * Time: 17:20
 */
public class ReqConfigSensorThresholdWarningDTO extends RequestDTO{
    private Integer iasId;
    private Integer devId;
    private Integer dataType;
    private LinkedList<ThresholdWarningCfgDTO> config;

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Integer getDevId() {
        return devId;
    }

    public void setDevId(Integer devId) {
        this.devId = devId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public LinkedList<ThresholdWarningCfgDTO> getConfig() {
        return config;
    }

    public void setConfig(LinkedList<ThresholdWarningCfgDTO> config) {
        this.config = config;
    }

    @Override
    public void check() {
        AssertEx.isOK(ErrorCode.OK);
    }
}
