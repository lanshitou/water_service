package com.zzwl.ias.vo;

/**
 * Created by Lvpin on 2018/11/12.
 */
public class UserAddIaSystemVo {
    private Integer id;
    private String name;
    private String address;
    //别名
    private String alias;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

}
