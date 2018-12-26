package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.FarmlandRecord;
import com.zzwl.ias.dto.iasystem.DcPointDTO;
import com.zzwl.ias.dto.iasystem.DeviceDTO;

import java.util.LinkedList;

/**
 * Created by HuXin on 2018/1/11.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FarmlandVo {
    private Integer id;
    private String name;
    private int sortOrder;
    private LinkedList<IrrigationAreaVo> irrigationAreas = null;
    private LinkedList<DcPointDTO> dcPoints = null;
    private LinkedList<DeviceDTO> normalDevices = null;

    public FarmlandVo(FarmlandRecord farmlandRecord) {
        id = farmlandRecord.getId();
        name = farmlandRecord.getName();
        sortOrder = farmlandRecord.getSortOrder();
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public LinkedList<IrrigationAreaVo> getIrrigationAreas() {
        return irrigationAreas;
    }

    public LinkedList<DcPointDTO> getDcPoints() {
        return dcPoints;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setIrrigationAreas(LinkedList<IrrigationAreaVo> irrigationAreas) {
        this.irrigationAreas = irrigationAreas;
    }

    public void setDcPoints(LinkedList<DcPointDTO> dcPoints) {
        this.dcPoints = dcPoints;
    }


    public LinkedList<DeviceDTO> getNormalDevices() {
        return normalDevices;
    }

    public void setNormalDevices(LinkedList<DeviceDTO> normalDevices) {
        this.normalDevices = normalDevices;
    }

}
