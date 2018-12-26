package com.zzwl.ias.vo.iasystem;

/**
 * Created by HuXin on 2018/2/26.
 */
public class IrriAreaConfigVo {
    private int id;
    private int iasId;
    private int fmId;
    private String name;
    private int sortOrder;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIasId() {
        return iasId;
    }

    public void setIasId(int iasId) {
        this.iasId = iasId;
    }

    public int getFmId() {
        return fmId;
    }

    public void setFmId(int fmId) {
        this.fmId = fmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
