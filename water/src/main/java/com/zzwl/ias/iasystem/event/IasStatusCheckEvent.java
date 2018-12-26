package com.zzwl.ias.iasystem.event;

/**
 * Created by HuXin on 2018/1/25.
 */
public class IasStatusCheckEvent extends IasEvent {
    int iaSystemId;

    public IasStatusCheckEvent(int iaSystemId) {
        this.iaSystemId = iaSystemId;
    }

    @Override
    public Integer getIaSystemId() {
        return iaSystemId;
    }
}
