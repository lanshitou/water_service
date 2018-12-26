package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.irrigation.event.*;

import java.util.Calendar;

/**
 * Description:灌溉任务停止阀门状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:30
 */
class NormalIrrigationTaskStateStoppingValves extends NormalIrrigationTaskState {
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
        switch (task.checkResult(event)) {
            case NormalIrrigationTaskOpValveHelper.OP_VALVES_IN_PROGRESS: {
                //还存在没有关闭的阀门，继续等待
                break;
            }
            case NormalIrrigationTaskOpValveHelper.OP_VALVES_SUCCESS: {
                //阀门已经全部关闭,通知Executor
                IrrigationTaskFinishedEvent finishedEvent = new IrrigationTaskFinishedEvent(task);
                AppContext.eventDispatcher.sendEvent(finishedEvent);
                if (task.isCanceled()) {
                    task.setResult(NormalIrriTaskResult.USER_CANCEL_OK);
                } else {
                    task.setResult(NormalIrriTaskResult.OK);
                }
                task.setState(NormalIrrigationTaskState.STATE_FINISH);
                break;
            }
            case NormalIrrigationTaskOpValveHelper.OP_VALVES_FAIL: {
                //关闭阀门失败，目前仍然按照阀门关闭成功处理，后期在考虑如何处理
                IrrigationTaskFinishedEvent finishedEvent = new IrrigationTaskFinishedEvent(task);
                AppContext.eventDispatcher.sendEvent(finishedEvent);
                if (task.isCanceled()) {
                    task.setResult(NormalIrriTaskResult.USER_CANCEL_CLOSE_VALVES_FAIL);
                } else {
                    task.setResult(NormalIrriTaskResult.CLOSE_VALVES_FAIL);
                }
                task.setState(NormalIrrigationTaskState.STATE_FINISH);
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
        return NormalIrrigationTaskState.STATE_STOPPING_VALVES;
    }
}
