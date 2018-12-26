package com.zzwl.ias.vo;

public class IrrigationHistoryTaskVo {
    Integer id;
    String name;
    IrrigationTaskStateVo irriTask;

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

    public IrrigationTaskStateVo getIrriTask() {
        return irriTask;
    }

    public void setIrriTask(IrrigationTaskStateVo irriTask) {
        this.irriTask = irriTask;
    }
}
