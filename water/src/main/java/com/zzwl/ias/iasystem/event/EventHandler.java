package com.zzwl.ias.iasystem.event;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.iasystem.IaSystem;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by HuXin on 2018/1/16.
 */
public class EventHandler implements Runnable {
    private LinkedBlockingQueue<IasEvent> eventQueue;

    public EventHandler(LinkedBlockingQueue<IasEvent> eventQueue) {
        this.eventQueue = eventQueue;
    }

    @Override
    public void run() {
        ErrorCode errorCode;
        while (true) {
            try {
                IasEvent event = eventQueue.take();
                try {
                    IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(event.getIaSystemId());
                    if (iaSystem != null) {
                        errorCode = iaSystem.handleEvent(event);
                        if (errorCode != ErrorCode.OK) {
                            //事件处理出错，暂时未处理
                        }
                    }
                } catch (Exception e) {
                    event.handleException(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
