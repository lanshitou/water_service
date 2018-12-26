package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:灌溉任务等待停止状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:29
 */
class NormalIrrigationTaskStateWaitingFinish extends NormalIrrigationTaskState {
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
        receiveUnconcernedEventHandler(event);
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
        //等待10秒后在关闭阀门，防止快速开启关闭阀门造成管道压力剧烈变化
        AppContext.timerManager.createTimer(()-> task.opValves(IaCommandType.STOP_DEVICE), 10, false);
        task.setState(NormalIrrigationTaskState.STATE_STOPPING_VALVES);
    }

    @Override
    int getState() {
        return NormalIrrigationTaskState.STATE_WAITING_FINISH;
    }
}
