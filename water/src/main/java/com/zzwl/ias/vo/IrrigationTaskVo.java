package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzwl.ias.common.ErrorCode;

/**
 * Created by HuXin on 2018/1/16.
 */
public class IrrigationTaskVo {
    int farmlandId;
    int irrigationAreaId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    long valveId;
    int duration;
    ErrorCode result;

    public int getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(int farmlandId) {
        this.farmlandId = farmlandId;
    }

    public int getIrrigationAreaId() {
        return irrigationAreaId;
    }

    public void setIrrigationAreaId(int irrigationAreaId) {
        this.irrigationAreaId = irrigationAreaId;
    }

    @JsonIgnore
    public long getValveId() {
        return valveId;
    }

    public void setValveId(long valveId) {
        this.valveId = valveId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ErrorCode getResult() {
        return result;
    }

    public void setResult(ErrorCode result) {
        this.result = result;
    }
}
