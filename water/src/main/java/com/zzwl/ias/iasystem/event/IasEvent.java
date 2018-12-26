package com.zzwl.ias.iasystem.event;

/**
 * Created by HuXin on 2018/1/3.
 */
public abstract class IasEvent {
    public abstract Integer getIaSystemId();

    public void handleException(Exception e) {
        e.printStackTrace();
    }
}
