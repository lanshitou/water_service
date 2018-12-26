package com.zzwl.ias.vo;

import com.zzwl.ias.domain.FarmlandRecord;

public class FarmlandBasicVo {
    private int id;
    private String name;
    private boolean valveStatus;
    private boolean dcPointStatus;

    public FarmlandBasicVo(FarmlandRecord farmlandRecord) {
        this.id = farmlandRecord.getId();
        this.name = farmlandRecord.getName();
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

    public boolean getValveStatus() {
        return valveStatus;
    }

    public void setValveStatus(boolean valveStatus) {
        this.valveStatus = valveStatus;
    }

    public boolean getDcPointStatus() {
        return dcPointStatus;
    }

    public void setDcPointStatus(boolean dcPointStatus) {
        this.dcPointStatus = dcPointStatus;
    }
}
