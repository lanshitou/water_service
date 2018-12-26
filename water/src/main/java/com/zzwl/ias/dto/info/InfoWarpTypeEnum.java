package com.zzwl.ias.dto.info;


import com.fasterxml.jackson.annotation.JsonValue;

public enum InfoWarpTypeEnum {
    TwoInOneLine(1), //一行两个 左右排布
    OneLineBig(2), //一行一个 大图模式
    OneLineSmall(3), //一行一个 小图模式
    OneLineGallery(4); //横向滚动 大图模式

    int value;

    InfoWarpTypeEnum(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
