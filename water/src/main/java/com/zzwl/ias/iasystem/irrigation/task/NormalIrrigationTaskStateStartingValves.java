package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:25
 */
class NormalIrrigationTaskStateStartingValves extends NormalIrrigationTaskState {
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
        switch (task.checkResult(event)) {
            case NormalIrrigationTaskOpValveHelper.OP_VALVES_IN_PROGRESS: {
                //还存在没有打开的阀门，继续等待
                break;
            }
            case NormalIrrigationTaskOpValveHelper.OP_VALVES_SUCCESS: {
                //阀门已经全部打开,通知Executor
                IrrigationTaskValvesStartedEvent valvesStartedEvent = new IrrigationTaskValvesStartedEvent(task);
                AppContext.eventDispatcher.sendEvent(valvesStartedEvent);
                task.setState(NormalIrrigationTaskState.STATE_WAITING_PUMP_STARTED);
                break;
            }
            case NormalIrrigationTaskOpValveHelper.OP_VALVES_FAIL: {
                //打开阀门失败，结束任务
                IrrigationTaskWaitFinishEvent waitFinishEvent = new IrrigationTaskWaitFinishEvent(task);
                AppContext.eventDispatcher.sendEvent(waitFinishEvent);
                task.setResult(NormalIrriTaskResult.OPEN_VALVES_FAIL);
                task.setState(NormalIrrigationTaskState.STATE_WAITING_FINISH);
                break;
            }
            default: {
                //do nothing
            }
        }
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
        return NormalIrrigationTaskState.STATE_STARTING_VALVES;
    }
}
