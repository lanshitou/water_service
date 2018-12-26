package com.zzwl.ias.dto.camera;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-26
 * Time: 11:35
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CameraDTO {
    private Integer id;
    private String name;
    private String sn;
    private String hls;
    private String hlsHD;
    private String rtmp;
    private String rtmpHD;
    private String wsAddr;
    private HashMap<String, String> capability;

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

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getHls() {
        return hls;
    }

    public void setHls(String hls) {
        this.hls = hls;
    }

    public String getHlsHD() {
        return hlsHD;
    }

    public void setHlsHD(String hlsHD) {
        this.hlsHD = hlsHD;
    }

    public String getRtmp() {
        return rtmp;
    }

    public void setRtmp(String rtmp) {
        this.rtmp = rtmp;
    }

    public String getRtmpHD() {
        return rtmpHD;
    }

    public void setRtmpHD(String rtmpHD) {
        this.rtmpHD = rtmpHD;
    }

    public HashMap<String, String> getCapability() {
        return capability;
    }

    public void setCapability(HashMap<String, String> capability) {
        HashMap<String, String> temp = new HashMap<>();
        temp.putAll(capability);
        this.capability = temp;
    }

    public String getWsAddr() {
        return wsAddr;
    }

    public void setWsAddr(String wsAddr) {
        this.wsAddr = wsAddr;
    }
}
