package com.zzwl.ias.iasystem.message;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-17
 * Time: 8:54
 */
public class IasMessageExt {
    private Long id;
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long msgId) {
        this.id = msgId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer msgType) {
        this.type = msgType;
    }
}
