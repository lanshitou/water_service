package com.zzwl.ias.vo.iasystem;

import com.zzwl.ias.domain.IaSystemRecord;
import com.zzwl.ias.dto.iasystem.DcPointDTO;
import com.zzwl.ias.dto.iasystem.DeviceDTO;
import com.zzwl.ias.dto.iasystem.IrriAndFerSystemDTO;
import com.zzwl.ias.vo.*;

import java.util.LinkedList;

/**
 * Created by HuXin on 2018/1/10.
 */

public class IaSystemVo {
    private Integer id;
    private String name;
    private Integer maxIrrNum;
    private Integer workMode;
    private String permission;

    IrriAndFerSystemDTO irriAndFerSystems = null;
    LinkedList<FarmlandVo> farmlands = null;
    LinkedList<DeviceDTO> normalDevices = null;
    LinkedList<DcPointDTO> dcPoints = null;
    LinkedList<IrrigationTaskStateVo> taskState = null;

    public IaSystemVo(IaSystemRecord iaSystemRecord) {
        id = iaSystemRecord.getId();
        name = iaSystemRecord.getAlias() == null || iaSystemRecord.getAlias().trim().equals("") ? iaSystemRecord.getName() : iaSystemRecord.getAlias();
        maxIrrNum = iaSystemRecord.getMaxIrrNum();
        workMode = iaSystemRecord.getMode();
    }

    public LinkedList<DeviceDTO> getNormalDevices() {
        return normalDevices;
    }

    public void setNormalDevices(LinkedList<DeviceDTO> normalDevices) {
        this.normalDevices = normalDevices;
    }

    public IrriAndFerSystemDTO getIrriAndFerSystems() {
        return irriAndFerSystems;
    }

    public void setIrriAndFerSystems(IrriAndFerSystemDTO irriAndFerSystems) {
        this.irriAndFerSystems = irriAndFerSystems;
    }

    public void setDcPoints(LinkedList<DcPointDTO> dcPoints) {
        this.dcPoints = dcPoints;
    }

    public void addFarmlandVo(FarmlandVo farmlandVo) {
        farmlands.add(farmlandVo);
    }

    public void addDcPointVo(DcPointDTO dcPointDTO) {
        dcPoints.add(dcPointDTO);
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

    public LinkedList<FarmlandVo> getFarmlands() {
        return farmlands;
    }

    public LinkedList<DcPointDTO> getDcPoints() {
        return dcPoints;
    }

    public int getMaxIrrNum() {
        return maxIrrNum;
    }

    public void setMaxIrrNum(int maxIrrNum) {
        this.maxIrrNum = maxIrrNum;
    }

    public void setFarmlands(LinkedList<FarmlandVo> farmlands) {
        this.farmlands = farmlands;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public int getWorkMode() {
        return workMode;
    }

    public void setWorkMode(int workMode) {
        this.workMode = workMode;
    }

    public LinkedList<IrrigationTaskStateVo> getTaskState() {
        return taskState;
    }

    public void setTaskState(LinkedList<IrrigationTaskStateVo> taskState) {
        this.taskState = taskState;
    }

}
