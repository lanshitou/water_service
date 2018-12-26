package com.zzwl.ias.dto.warning;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.RequestDTO;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-26
 * Time: 9:43
 */
public class ReqGetSensorThresholdWarningCfg extends RequestDTO{
    private Integer iasId;
    private Integer devId;
    private Integer dataType;



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

    @Override
    public void check() {
        AssertEx.isOK(ErrorCode.OK);
    }
}
