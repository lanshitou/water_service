package com.zzwl.ias.dto.iasystem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.IaSystemRecord;
import com.zzwl.ias.dto.camera.CameraDTO;
import com.zzwl.ias.dto.warning.WarningStatisticsDTO;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-28
 * Time: 17:18
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IaSystemDTO {
    private Integer id;             //智慧农业系统ID
    private String name;            //智慧农业系统命令
    private Integer mode;           //智慧农业系统工作模式
    private Boolean configMode;     //用户是否可以更改工作模式
    private WarningStatisticsDTO warningStat;       //告警统计信息
    private LinkedList<DcPointDTO> dcPoints;        //环境信息
    private LinkedList<DeviceDTO> devices;          //可操作设备
    private IrriAndFerSystemDTO irriAndFerSystem;   //水肥一体化信息
    private LinkedList<FarmlandDTO> farmlands;      //农田摘要信息
    private LinkedList<CameraDTO> cameras;          //摄像头信息

    public IaSystemDTO(IaSystemRecord iaSystemRecord) {
        id = iaSystemRecord.getId();
        name = iaSystemRecord.getName();
        mode = iaSystemRecord.getMode();
        configMode = true;
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

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Boolean getConfigMode() {
        return configMode;
    }

    public void setConfigMode(Boolean configMode) {
        this.configMode = configMode;
    }

    public WarningStatisticsDTO getWarningStat() {
        return warningStat;
    }

    public void setWarningStat(WarningStatisticsDTO warningStat) {
        this.warningStat = warningStat;
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

    public IrriAndFerSystemDTO getIrriAndFerSystem() {
        return irriAndFerSystem;
    }

    public void setIrriAndFerSystem(IrriAndFerSystemDTO irriAndFerSystem) {
        this.irriAndFerSystem = irriAndFerSystem;
    }

    public LinkedList<FarmlandDTO> getFarmlands() {
        return farmlands;
    }

    public void setFarmlands(LinkedList<FarmlandDTO> farmlands) {
        this.farmlands = farmlands;
    }

    public LinkedList<CameraDTO> getCameras() {
        return cameras;
    }

    public void setCameras(LinkedList<CameraDTO> cameras) {
        this.cameras = cameras;
    }
}
