package com.zzwl.ias.dto.camera;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-06
 * Time: 12:56
 */
public class GetWsAddressFromYsSingleResult {
    private String deviceSerial;
    private Integer channelNo;
    private String deviceName;
    private String wsAddress;
    private Integer status;
    private Integer exception;
    private String ret;
    private String desc;

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getWsAddress() {
        return wsAddress;
    }

    public void setWsAddress(String wsAddress) {
        this.wsAddress = wsAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getException() {
        return exception;
    }

    public void setException(Integer exception) {
        this.exception = exception;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
