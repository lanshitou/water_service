package com.zzwl.ias.iasystem.operation;

import com.zzwl.ias.iasystem.ReqOperateDevice;

/**
 * Created by HuXin on 2017/9/23.
 */
public interface OperateDeviceFinishHandler {
    void notify(ReqOperateDevice request);
}
