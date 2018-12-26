package com.zzwl.ias.dto.iasystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.iasystem.common.DeviceAddr;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-05-25
 * Time: 17:03
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceDTO {
    private Integer id;
    private Integer type;
    private String name;
    private Integer status;
    private Boolean operable;
    private Integer alarmType;      //告警类型
    private List<DcPointDTO> dcPoints;

    public DeviceDTO(IasDeviceRecord iasDeviceRecord) {
        id = iasDeviceRecord.getId();
        type = (int)DeviceAddr.getDevTypeById(iasDeviceRecord.getDevId());
        name = iasDeviceRecord.getName();
        dcPoints = null;
        operable = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getOperable() {
        return operable;
    }

    public void setOperable(Boolean operable) {
        this.operable = operable;
    }

    public List<DcPointDTO> getDcPoints() {
        return dcPoints;
    }

    public void setDcPoints(List<DcPointDTO> dcPoints) {
        this.dcPoints = dcPoints;
    }

    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }
}
