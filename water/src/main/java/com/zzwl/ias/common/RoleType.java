package com.zzwl.ias.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Lvpin on 2018/11/21.
 */
public enum RoleType {
    IAS_RETRIEVE(1, "智慧农业系统使用者"),
    IAS_ADMIN(2, "智慧农业系统管理员"),
    IAS_OWNER(3, "智慧农业系统拥有者");
    private int value;
    private String descTemplate;

    RoleType(int value, String descTemplate) {
        this.value = value;
        this.descTemplate = descTemplate;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public String getDescTemplate() {
        return this.descTemplate;
    }
}
