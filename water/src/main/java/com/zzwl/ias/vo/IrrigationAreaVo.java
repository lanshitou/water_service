package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.IrrigationAreaRecord;
import com.zzwl.ias.dto.iasystem.DcPointDTO;
import com.zzwl.ias.dto.iasystem.DeviceDTO;

import java.util.LinkedList;

/**
 * Created by HuXin on 2018/1/11.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IrrigationAreaVo {
    private Integer id;
    private String name;
    private int sortOrder;
    private LinkedList<DeviceDTO> normalDevices = null;
    private LinkedList<DeviceDTO> valves = null;
    private LinkedList<DcPointDTO> dcPoints = null;
    private IrrigationTaskStateVo taskState = null;

    public IrrigationAreaVo(IrrigationAreaRecord irrigationAreaRecord) {
        id = irrigationAreaRecord.getId();
        name = irrigationAreaRecord.getName();
        sortOrder = irrigationAreaRecord.getSortOrder();
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<DeviceDTO> getNormalDevices() {
        return normalDevices;
    }

    public void setNormalDevices(LinkedList<DeviceDTO> normalDevices) {
        this.normalDevices = normalDevices;
    }

    public LinkedList<DcPointDTO> getDcPoints() {
        return dcPoints;
    }

    public void setDcPoints(LinkedList<DcPointDTO> dcPoints) {
        this.dcPoints = dcPoints;
    }

    public LinkedList<DeviceDTO> getValves() {
        return valves;
    }

    public void setValves(LinkedList<DeviceDTO> valves) {
        this.valves = valves;
    }

    public IrrigationTaskStateVo getTaskState() {
        return taskState;
    }

    public void setTaskState(IrrigationTaskStateVo taskState) {
        this.taskState = taskState;
    }
}
