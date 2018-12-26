package com.zzwl.ias.iasystem.event;

import com.zzwl.ias.common.ErrorCode;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by HuXin on 2018/1/16.
 */
public class EventDispatcher {
    private int workerSize;
    private EventHandler[] handlers;
    private ArrayList<LinkedBlockingQueue<IasEvent>> eventQueues;

    public EventDispatcher(int workerSize) {
        this.workerSize = workerSize;
        handlers = new EventHandler[workerSize];
        eventQueues = new ArrayList<>(workerSize);
    }

    public void start() {
        for (int i = 0; i < workerSize; i++) {
            eventQueues.add(i, new LinkedBlockingQueue<>());
            handlers[i] = new EventHandler(eventQueues.get(i));
            Thread t = new Thread(handlers[i]);
            t.setName(String.format("EventDispatcher-%d", i));
            t.start();
        }
    }

    public ErrorCode sendEvent(IasEvent event) {
        boolean retry;
        do {
            retry = false;
            try {
                int index = event.getIaSystemId() % workerSize;
                eventQueues.get(index).put(event);
            } catch (InterruptedException e) {
                retry = true;
            } catch (Exception e) {
                e.printStackTrace();
                return ErrorCode.SEND_EVENT_FAIL;
            }

        } while (retry);
        return ErrorCode.OK;
    }
}
