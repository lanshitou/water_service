package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceType;

/**
 * Description:
 * User: HuXin
 * Date: 2018-05-26
 * Time: 12:37
 */
public class DeviceConfigVo {
    private Integer iasId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String name;
    private Integer sysId;
    private Byte ctlerId;
    private Byte devType;
    private Byte devSeq;
    private Integer keepAlive;
    private Boolean lowConsume;

    public boolean check() {
        if (name == null || sysId == null || ctlerId == null || devType == null || devSeq == null) {
            return false;
        }

        DeviceAddr deviceAddr = new DeviceAddr(sysId, ctlerId, devType, devSeq);
        if (!deviceAddr.isValid()) {
            return false;
        }

        if (devType == DeviceType.DEV_CONTROLLER && ctlerId == 0) {
            if (keepAlive == null || lowConsume == null) {
                return false;
            }
        }

        return true;
    }

    public boolean updateCheck() {
        if (id == null || name == null) {
            return false;
        }

        return true;
    }

    public void generateDevId() {
        DeviceAddr deviceAddr = new DeviceAddr(sysId, ctlerId, devType, devSeq);
        id = deviceAddr.getId();
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
    }

    public Byte getCtlerId() {
        return ctlerId;
    }

    public void setCtlerId(Byte ctlerId) {
        this.ctlerId = ctlerId;
    }

    public Byte getDevType() {
        return devType;
    }

    public void setDevType(Byte devType) {
        this.devType = devType;
    }

    public Byte getDevSeq() {
        return devSeq;
    }

    public void setDevSeq(Byte devSeq) {
        this.devSeq = devSeq;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getLowConsume() {
        return lowConsume;
    }

    public void setLowConsume(Boolean lowConsume) {
        this.lowConsume = lowConsume;
    }
}
