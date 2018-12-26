package com.zzwl.ias.iasystem.device;

import com.alibaba.druid.support.json.JSONUtils;
import com.zzwl.ias.domain.CameraDO;
import com.zzwl.ias.dto.camera.CameraDTO;
import com.zzwl.ias.dto.camera.CameraSummaryDTO;
import com.zzwl.ias.iasystem.constant.IaSystemConstant;

import java.util.HashMap;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-25
 * Time: 12:34
 */
public class Camera {
    private CameraDO cameraDO;

    /** 获取摄像头信息
     * @param type 查询类型
     * @return 摄像头信息
     */
    public CameraDTO getCameraDTO(Integer type) {
        CameraDTO cameraDTO = new CameraDTO();
        cameraDTO.setId(getId());
        cameraDTO.setName(getName());
        if (type == IaSystemConstant.QUERY_TYPE_ALL) {
            cameraDTO.setSn(getSn());
            cameraDTO.setHls(getUrlHls());
            cameraDTO.setHlsHD(getUrlHlsHd());
            cameraDTO.setRtmp(getRtmp());
            cameraDTO.setRtmpHD(getRtmpHd());
            cameraDTO.setWsAddr(getWsAddr());
            cameraDTO.setCapability((HashMap<String, String>) JSONUtils.parse((String) getCapability()));
        }
        return cameraDTO;
    }

    public Camera(CameraDO cameraDO) {
        this.cameraDO = cameraDO;
    }

    public Integer getId() {
        return cameraDO.getId();
    }

    public void setId(Integer id) {
        cameraDO.setId(id);
    }

    public String getName() {
        return cameraDO.getName();
    }

    public void setName(String name) {
        cameraDO.setName(name);
    }

    public String getSn() {
        return cameraDO.getSn();
    }

    public void setSn(String sn) {
        cameraDO.setSn(sn);
    }

    public String getCode() {
        return cameraDO.getCode();
    }

    public void setCode(String code) {
        cameraDO.setCode(code);
    }

    public String getUrlHls() {
        return cameraDO.getUrlHls();
    }

    public void setUrlHls(String urlHls) {
        cameraDO.setUrlHls(urlHls);
    }

    public String getUrlHlsHd() {
        return cameraDO.getUrlHlsHd();
    }

    public void setUrlHlsHd(String urlHlsHd) {
        cameraDO.setUrlHlsHd(urlHlsHd);
    }

    public String getRtmp() {
        return cameraDO.getRtmp();
    }

    public void setRtmp(String rtmp) {
        cameraDO.setRtmp(rtmp);
    }

    public String getRtmpHd() {
        return cameraDO.getRtmpHd();
    }

    public void setRtmpHd(String rtmpHd) {
        cameraDO.setRtmpHd(rtmpHd);
    }

    public Integer getIasId() {
        return cameraDO.getIasId();
    }

    public void setIasId(Integer iasId) {
        cameraDO.setIasId(iasId);
    }

    public Integer getFarmlandId() {
        return cameraDO.getFarmlandId();
    }

    public void setFarmlandId(Integer farmlandId) {
        cameraDO.setFarmlandId(farmlandId);
    }

    public Integer getAreaId() {
        return cameraDO.getAreaId();
    }

    public void setAreaId(Integer areaId) {
        cameraDO.setAreaId(areaId);
    }

    public Integer getLocation() {
        return cameraDO.getLocation();
    }

    public void setLocation(Integer location) {
        cameraDO.setLocation(location);
    }

    public Object getCapability() {
        return cameraDO.getCapability();
    }

    public void setCapability(Object capability) {
        cameraDO.setCapability(capability);
    }

    public String getWsAddr() {
        return cameraDO.getWsAddr();
    }

    public void setWsAddr(String wsAddr) {
        cameraDO.setWsAddr(wsAddr);
    }

}
