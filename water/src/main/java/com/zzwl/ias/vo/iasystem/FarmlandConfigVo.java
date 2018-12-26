package com.zzwl.ias.vo.iasystem;

/**
 * Created by HuXin on 2018/2/26.
 */
public class FarmlandConfigVo {
    private int id;
    private int iaSystemId;
    private String name;
    private int sortOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIaSystemId() {
        return iaSystemId;
    }

    public void setIaSystemId(int iaSystemId) {
        this.iaSystemId = iaSystemId;
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
