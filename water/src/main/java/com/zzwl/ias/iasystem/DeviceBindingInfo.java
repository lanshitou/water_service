package com.zzwl.ias.iasystem;

/**
 * Created by HuXin on 2018/1/17.
 */
public class DeviceBindingInfo {
    public final static int SYS_DC_POINT = 1;
    public final static int SYS_PUMP = 2;
    public final static int FM_DC_POINT = 3;
    public final static int FM_IRR_AREA_VALVE = 4;
    public final static int FM_NORMAL_DEVICE = 5;

    private long deviceId;
    private int iaSystemId;
    private int bindingType;
    private Integer farmlandId;         //如果设备属于某个Farmland，则这里记录Farmland的ID，否则为null
    private Object bindingObject;

    public DeviceBindingInfo(long deviceId, int iaSystemId, Integer farmlandId, int bindingType, Object bindingObject) {
        this.deviceId = deviceId;
        this.iaSystemId = iaSystemId;
        this.farmlandId = farmlandId;
        this.bindingType = bindingType;
        this.bindingObject = bindingObject;
    }

    public long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public int getIaSystemId() {
        return iaSystemId;
    }

    public void setIaSystemId(int iaSystemId) {
        this.iaSystemId = iaSystemId;
    }

    public int getBindingType() {
        return bindingType;
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public Object getBindingObject() {
        return bindingObject;
    }
}
