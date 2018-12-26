package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.dto.iasystem.DcPointDTO;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WeatherStationVo {
    private Integer id;
    private String name;
    private List<DcPointDTO> dcPoints;

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

    public List<DcPointDTO> getDcPoints() {
        return dcPoints;
    }

    public void setDcPoints(List<DcPointDTO> dcPoints) {
        this.dcPoints = dcPoints;
    }
}
