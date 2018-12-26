package com.zzwl.ias.vo;

/**
 * Created by Lvpin on 2018/11/22.
 */
public class IaSystemListVo {
    private Integer id;
    private String name;
    private String alias;
    private String address;
    private Integer maxIrrNum;
    private Integer mode;
    private Integer type;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getMaxIrrNum() {
        return maxIrrNum;
    }

    public void setMaxIrrNum(Integer maxIrrNum) {
        this.maxIrrNum = maxIrrNum;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
