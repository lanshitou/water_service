package com.zzwl.ias.dto.camera;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-25
 * Time: 17:40
 */
public class OpenVideoOnYsSingleResultDTO {
    private String deviceSerial;
    private Integer channelNo;
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
