package com.zzwl.ias.dto.info;


import com.fasterxml.jackson.annotation.JsonValue;

public enum InfoContentTypeEnum {
    Article(1),
    Subject(2);

    int value;

    InfoContentTypeEnum(int value) {
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

