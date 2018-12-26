package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.irrigation.event.*;

import java.util.Calendar;

/**
 * Description:灌溉任务等待状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:24
 */
class NormalIrrigationTaskStateWaiting extends NormalIrrigationTaskState {
    @Override
    void handleStartIrrigationEvent(StartIrrigationEvent event, NormalIrrigationTask task) {
        task.opValves(IaCommandType.START_DEVICE);
        task.setState(NormalIrrigationTaskState.STATE_STARTING_VALVES);
    }

    @Override
    void handleStopIrrigationEvent(StopIrrigationTaskEvent event, NormalIrrigationTask task) {
        //任务没有启动，立即停止
        IrrigationTaskFinishedEvent finishedEvent = new IrrigationTaskFinishedEvent(task);
        AppContext.eventDispatcher.sendEvent(finishedEvent);

        if (event.isUserCancel()){
            task.setCanceled(true);
            task.setResult(NormalIrriTaskResult.USER_CANCEL_OK);
        }
        task.setState(NormalIrrigationTaskState.STATE_FINISH);
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
        return NormalIrrigationTaskState.STATE_WAITING;
    }
}
