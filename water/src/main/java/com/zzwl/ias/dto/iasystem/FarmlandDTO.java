package com.zzwl.ias.dto.iasystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.FarmlandRecord;
import com.zzwl.ias.dto.warning.WarningStatisticsDTO;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-28
 * Time: 17:01
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FarmlandDTO {
    private Integer mode;
    private Integer farmlandId;
    private String name;
    private LinkedList<DcPointDTO> dcPoints;
    private LinkedList<DeviceDTO> devices;
    private LinkedList<IrriAreaDTO> irriAreas;
    private WarningStatisticsDTO warningStat;
    private Integer irriStatus;

    public FarmlandDTO(FarmlandRecord farmlandRecord) {
        farmlandId = farmlandRecord.getId();
        name = farmlandRecord.getName();
        irriAreas = null;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(Integer farmlandId) {
        this.farmlandId = farmlandId;
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

    public LinkedList<IrriAreaDTO> getIrriAreas() {
        return irriAreas;
    }

    public void setIrriAreas(LinkedList<IrriAreaDTO> irriAreas) {
        this.irriAreas = irriAreas;
    }

    public WarningStatisticsDTO getWarningStat() {
        return warningStat;
    }

    public void setWarningStat(WarningStatisticsDTO warningStat) {
        this.warningStat = warningStat;
    }

    public Integer getIrriStatus() {
        return irriStatus;
    }

    public void setIrriStatus(Integer irriStatus) {
        this.irriStatus = irriStatus;
    }
}
