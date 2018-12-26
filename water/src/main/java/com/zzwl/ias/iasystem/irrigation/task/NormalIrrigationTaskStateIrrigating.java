package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:灌溉任务执行中状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:28
 */
class NormalIrrigationTaskStateIrrigating extends NormalIrrigationTaskState {
    @Override
    void handleStartIrrigationEvent(StartIrrigationEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleStopIrrigationEvent(StopIrrigationTaskEvent event, NormalIrrigationTask task) {
        task.removeTimer();
        task.setCanceled(event.isUserCancel());
        IrrigationTaskWaitFinishEvent waitFinishEvent = new IrrigationTaskWaitFinishEvent(task);
        AppContext.eventDispatcher.sendEvent(waitFinishEvent);
        task.setState(NormalIrrigationTaskState.STATE_WAITING_FINISH);
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
        IrrigationTaskWaitFinishEvent waitFinishEvent = new IrrigationTaskWaitFinishEvent(task);
        AppContext.eventDispatcher.sendEvent(waitFinishEvent);
        task.setState(NormalIrrigationTaskState.STATE_WAITING_FINISH);
    }

    @Override
    void handleTaskFinishableEvent(IrrigationTaskFinishableEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    int getState() {
        return NormalIrrigationTaskState.STATE_IRRIGATING;
    }
}
