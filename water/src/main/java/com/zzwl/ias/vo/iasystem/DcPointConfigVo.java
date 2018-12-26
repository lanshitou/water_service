package com.zzwl.ias.vo.iasystem;

/**
 * Created by HuXin on 2018/2/26.
 */
public class DcPointConfigVo {
    private int id;
    private String name;
    private long sensorId;

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

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }
}
