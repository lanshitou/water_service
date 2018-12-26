package com.zzwl.ias.dto.iasystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.IrrigationAreaRecord;
import com.zzwl.ias.vo.IrrigationTaskStateVo;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-28
 * Time: 17:04
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IrriAreaDTO {
    private Integer irriAreaId;
    private String name;
    private LinkedList<DcPointDTO> dcPoints;
    private LinkedList<DeviceDTO> devices;
    private LinkedList<DeviceDTO> valves;
    private IrrigationTaskStateVo irriTask;

    public IrriAreaDTO(IrrigationAreaRecord irrigationAreaRecord) {
        irriAreaId = irrigationAreaRecord.getId();
        name = irrigationAreaRecord.getName();
        irriTask = null;
    }

    public Integer getIrriAreaId() {
        return irriAreaId;
    }

    public void setIrriAreaId(Integer irriAreaId) {
        this.irriAreaId = irriAreaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<DcPointDTO> getDcPoints() {
        return dcPoints;
    }

    public void setDcPoints(LinkedList<DcPointDTO> dcPoints) {
        this.dcPoints = dcPoints;
    }

    public LinkedList<DeviceDTO> getDevices() {
        return devices;
    }

    public void setDevices(LinkedList<DeviceDTO> devices) {
        this.devices = devices;
    }

    public LinkedList<DeviceDTO> getValves() {
        return valves;
    }

    public void setValves(LinkedList<DeviceDTO> valves) {
        this.valves = valves;
    }

    public IrrigationTaskStateVo getIrriTask() {
        return irriTask;
    }

    public void setIrriTask(IrrigationTaskStateVo irriTask) {
        this.irriTask = irriTask;
    }
}
