package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.iasystem.event.IasEvent;
import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:23
 */
public abstract class NormalIrrigationTaskState {
    public static final int STATE_WAITING = 1;
    public static final int STATE_STARTING_VALVES = 2;
    public static final int STATE_WAITING_PUMP_STARTED = 3;
    public static final int STATE_IRRIGATING = 4;
    public static final int STATE_WAITING_FINISH = 5;
    public static final int STATE_STOPPING_VALVES = 6;
    public static final int STATE_FINISH = 7;

    abstract void handleStartIrrigationEvent(StartIrrigationEvent event, NormalIrrigationTask task);

    abstract void handleStopIrrigationEvent(StopIrrigationTaskEvent event, NormalIrrigationTask task);

    abstract void handleDevOpFinishEvent(IrrigationDevOpFinishedEvent event, NormalIrrigationTask task);

    abstract void handlePumpStartedEvent(ExecutorPumpStartedEvent event, NormalIrrigationTask task);

    abstract void handleTaskTimeoutEvent(IrrigationTaskTimeoutEvent event, NormalIrrigationTask task);

    abstract void handleTaskFinishableEvent(IrrigationTaskFinishableEvent event, NormalIrrigationTask task);

    abstract int getState();

    void handleEvent(IrrigationEvent event, NormalIrrigationTask task) {
        if (event instanceof StartIrrigationEvent) {
            handleStartIrrigationEvent((StartIrrigationEvent) event, task);
        } else if (event instanceof StopIrrigationTaskEvent) {
            handleStopIrrigationEvent((StopIrrigationTaskEvent) event, task);
        } else if (event instanceof IrrigationDevOpFinishedEvent) {
            handleDevOpFinishEvent((IrrigationDevOpFinishedEvent) event, task);
        } else if (event instanceof ExecutorPumpStartedEvent) {
            handlePumpStartedEvent((ExecutorPumpStartedEvent) event, task);
        } else if (event instanceof IrrigationTaskTimeoutEvent) {
            handleTaskTimeoutEvent((IrrigationTaskTimeoutEvent) event, task);
        } else if (event instanceof IrrigationTaskFinishableEvent) {
            handleTaskFinishableEvent((IrrigationTaskFinishableEvent) event, task);
        } else {
            //运行到这里就出错啦
            receiveUnsupportedEventHandler(event);
        }
    }

    private void receiveUnsupportedEventHandler(IasEvent event) {
        String output = String.format("#####Receive unsupported event %s in %s state#####", event.getClass(), this.getClass());
        System.out.println(output);
    }

    void receiveUnexpectedEventHandler(IasEvent event) {
        String output = String.format("#####Receive unexpected event %s in %s state#####", event.getClass(), this.getClass());
        System.out.println(output);
    }

    void receiveUnconcernedEventHandler(IasEvent event) {
        String output = String.format("#####Receive unconcerned event %s in %s state#####", event.getClass(), this.getClass());
        System.out.println(output);
    }

    String getName() {
        String[] temps = this.getClass().getName().split("\\.");
        return temps[temps.length - 1];
    }
}
