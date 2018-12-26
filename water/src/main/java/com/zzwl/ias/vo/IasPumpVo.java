package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.dto.iasystem.DcPointDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by HuXin on 2018/1/11.
 */
public class IasPumpVo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    long id;
    String name;
    int usageType;
    List<DcPointDTO> dcPoints;

    public IasPumpVo(IasDeviceRecord iasDeviceRecord) {
        id = iasDeviceRecord.getDevId();
        name = iasDeviceRecord.getName();
        usageType = iasDeviceRecord.getUsageType();
        dcPoints = new LinkedList<>();
    }

    public List<DcPointDTO> getDcPoints() {
        return dcPoints;
    }

    public void setDcPoints(List<DcPointDTO> dcPoints) {
        this.dcPoints = dcPoints;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getUsageType() {
        return usageType;
    }

    public void setUsageType(int usageType) {
        this.usageType = usageType;
    }
}
