package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.irrigation.event.*;
import com.zzwl.ias.timer.IaTimer;

import java.util.Calendar;

/**
 * Description:灌溉任务等待水泵启动
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:26
 */
class NormalIrrigationTaskStateWaitingPumpStarted extends NormalIrrigationTaskState {
    @Override
    void handleStartIrrigationEvent(StartIrrigationEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleStopIrrigationEvent(StopIrrigationTaskEvent event, NormalIrrigationTask task) {
        IrrigationTaskWaitFinishEvent waitFinishEvent = new IrrigationTaskWaitFinishEvent(task);
        AppContext.eventDispatcher.sendEvent(waitFinishEvent);
        task.setCanceled(event.isUserCancel());
        task.setState(NormalIrrigationTaskState.STATE_WAITING_FINISH);
    }

    @Override
    void handleDevOpFinishEvent(IrrigationDevOpFinishedEvent event, NormalIrrigationTask task) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handlePumpStartedEvent(ExecutorPumpStartedEvent event, NormalIrrigationTask task) {
        //水泵启动成功，开始运行
        IaTimer timer = AppContext.timerManager.createTimer(() ->
        {
            IrrigationTaskTimeoutEvent irriTimeoutEvent = new IrrigationTaskTimeoutEvent(task);
            AppContext.eventDispatcher.sendEvent(irriTimeoutEvent);
        }, task.getDuration(), false);
        task.setTimer(timer);
        //记录灌溉开始时间
        task.setIrriStartTime(Calendar.getInstance());
        //进入灌溉中状态
        task.setState(NormalIrrigationTaskState.STATE_IRRIGATING);
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
        return NormalIrrigationTaskState.STATE_WAITING_PUMP_STARTED;
    }
}
