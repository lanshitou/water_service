package com.zzwl.ias.vo;

import com.zzwl.ias.domain.IaSystemRecord;

import java.util.List;

public class DeviceTopoVo {
    public IaSystemRecord iaSystem;
    public List<MasterControllerVo> masterControllers;

    public IaSystemRecord getIaSystem() {
        return iaSystem;
    }

    public void setIaSystem(IaSystemRecord iaSystem) {
        this.iaSystem = iaSystem;
    }

    public List<MasterControllerVo> getMasterControllers() {
        return masterControllers;
    }

    public void setMasterControllers(List<MasterControllerVo> masterControllers) {
        this.masterControllers = masterControllers;
    }
}