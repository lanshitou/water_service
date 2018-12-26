package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:灌溉任务结束状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:30
 */
class NormalIrrigationTaskStateFinish extends NormalIrrigationTaskState {
    @Override
    void handleStartIrrigationEvent(StartIrrigationEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleStopIrrigationEvent(StopIrrigationTaskEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleDevOpFinishEvent(IrrigationDevOpFinishedEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handlePumpStartedEvent(ExecutorPumpStartedEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleTaskTimeoutEvent(IrrigationTaskTimeoutEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleTaskFinishableEvent(IrrigationTaskFinishableEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    int getState() {
        return NormalIrrigationTaskState.STATE_FINISH;
    }
}
