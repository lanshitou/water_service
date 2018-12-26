package com.zzwl.ias.dto.camera;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.RequestDTO;
import com.zzwl.ias.iasystem.constant.CameraConstant;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-25
 * Time: 11:40
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CameraConfigRequestDTO extends RequestDTO {
    private String name;
    private String sn;
    private String code;
    private Integer iaSystemId;
    private Integer farmlandId;
    private Integer areaId;
    private Integer location;

    @Override
    public void check() {
        if (sn == null || name == null || code == null || iaSystemId == null || location == null) {
            AssertEx.isOK(ErrorCode.INVALID_PARAMS);
        }

        if (location != CameraConstant.CAMERA_LOC_SYSTEM
                && location != CameraConstant.CAMERA_LOC_FARMLAND
                && location != CameraConstant.CAMERA_LOC_AREA) {
            AssertEx.isOK(ErrorCode.INVALID_PARAMS);
        }

        if (location == CameraConstant.CAMERA_LOC_FARMLAND) {
            if (farmlandId == null) {
                AssertEx.isOK(ErrorCode.INVALID_PARAMS);
            }
        }

        if (location == CameraConstant.CAMERA_LOC_AREA) {
            if (farmlandId == null || areaId == null) {
                AssertEx.isOK(ErrorCode.INVALID_PARAMS);
            }
        }

        AssertEx.isOK(ErrorCode.OK);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIaSystemId() {
        return iaSystemId;
    }

    public void setIaSystemId(Integer iaSystemId) {
        this.iaSystemId = iaSystemId;
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

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }
}
