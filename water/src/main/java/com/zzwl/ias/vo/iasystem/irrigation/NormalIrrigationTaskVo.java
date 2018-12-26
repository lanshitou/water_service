package com.zzwl.ias.vo.iasystem.irrigation;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;

/**
 * Description:灌溉任务信息
 * User: HuXin
 * Date: 2018-04-10
 * Time: 10:35
 */
public class NormalIrrigationTaskVo {
    private Integer farmlandId;
    private Integer areaId;
    private Integer duration;
    private ErrorCode result;
    private Integer[] valves;

    public void check(int reqType) {
        if (reqType == ReqUpdateNormalIrrigationDTO.REQUEST_TYPE_ADD) {
            AssertEx.isTrue(farmlandId != null && areaId != null && duration != null, ErrorCode.INVALID_PARAMS);
        } else {
            AssertEx.isTrue(farmlandId != null && areaId != null, ErrorCode.INVALID_PARAMS);
        }
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(Integer farmlandId) {
        this.farmlandId = farmlandId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ErrorCode getResult() {
        return result;
    }

    public void setResult(ErrorCode result) {
        this.result = result;
    }

    public Integer[] getValves() {
        return valves;
    }

    public void setValves(Integer[] valves) {
        this.valves = valves;
    }
}
