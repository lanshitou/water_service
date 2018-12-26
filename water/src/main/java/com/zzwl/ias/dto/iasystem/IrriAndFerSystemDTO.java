package com.zzwl.ias.dto.iasystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.IrriFerRecord;

import java.util.LinkedList;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IrriAndFerSystemDTO {
    private Integer id;
    private String name;
    private LinkedList<DcPointDTO> dcPoints;
    private DeviceDTO pump = null;

    public IrriAndFerSystemDTO(IrriFerRecord irriFerRecord) {
        id = irriFerRecord.getId();
        name = irriFerRecord.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public DeviceDTO getPump() {
        return pump;
    }

    public void setPump(DeviceDTO pump) {
        this.pump = pump;
    }
}
