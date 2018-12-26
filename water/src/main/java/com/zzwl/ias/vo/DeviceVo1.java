package com.zzwl.ias.vo;

import com.zzwl.ias.domain.DeviceRecord;

public class DeviceVo1 {
    DeviceRecord deviceRecord;
    int sysId;
    byte ctlerId;
    byte devType;
    byte devSeq;

    public void setDeviceAddr(long id) {
        sysId = (int) (id >> 32);
        ctlerId = (byte) (id >> 16);
        devType = (byte) (id >> 8);
        devSeq = (byte) (id);
    }

    public byte getDevSeq() {
        return devSeq;
    }

    public void setDevSeq(byte devSeq) {
        this.devSeq = devSeq;
    }

    public DeviceRecord getDeviceRecord() {
        return deviceRecord;
    }

    public void setDeviceRecord(DeviceRecord deviceRecord) {
        this.deviceRecord = deviceRecord;
    }

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    public byte getCtlerId() {
        return ctlerId;
    }

    public void setCtlerId(byte ctlerId) {
        this.ctlerId = ctlerId;
    }

    public byte getDevType() {
        return devType;
    }

    public void setDevType(byte devType) {
        this.devType = devType;
    }
}
