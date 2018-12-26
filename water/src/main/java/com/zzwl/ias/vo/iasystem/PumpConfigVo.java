package com.zzwl.ias.vo.iasystem;

/**
 * Created by HuXin on 2018/2/27.
 */
public class PumpConfigVo {
    private int id;
    private String name;
    private long pumpId;

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

    public long getPumpId() {
        return pumpId;
    }

    public void setPumpId(long pumpId) {
        this.pumpId = pumpId;
    }
}
