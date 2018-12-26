package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:灌溉任务执行器等待水泵关闭状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:18
 */
public class NormalIrrigationExecutorStateStoppingPump extends NormalIrrigationExecutorState {
    @Override
    void handleAddTaskEvent(AddIrrigationTasksEvent event, NormalIrrigationExecutor executor) {
        executor.addTasks(event);
    }

    @Override
    void handleRemoveTaskEvent(RemoveIrrigationTasksEvent event, NormalIrrigationExecutor executor) {
        executor.removeTasks(event);
    }

    @Override
    void handleValvesStartedEvent(IrrigationTaskValvesStartedEvent event, NormalIrrigationExecutor executor) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleDevOpFinishedEvent(IrrigationDevOpFinishedEvent event, NormalIrrigationExecutor executor) {
        int ret = executor.checkOpPumpResult(event);
        switch (ret) {
            case NormalIrrigationExecutorOpPumpHelper.OP_PUMP_IN_PROGRESS: {
                //操作未完成，继续等待
                break;
            }
            case NormalIrrigationExecutorOpPumpHelper.OP_PUMP_SUCCESS: {
                //关闭水泵成功
                executor.stopWaitingFinishTasks();
                executor.setState(NormalIrrigationExecutorState.STATE_WAITING_FINISH);
                break;
            }
            case NormalIrrigationExecutorOpPumpHelper.OP_PUMP_FAIL: {
                //关闭水泵失败。目前先按照关闭成功处理，后续再考虑如何处理这种情况
                executor.stopWaitingFinishTasks();
                executor.setState(NormalIrrigationExecutorState.STATE_WAITING_FINISH);
                break;
            }
            default: {
                //do nothing
            }
        }
    }

    @Override
    void handleTaskWaitFinishEvent(IrrigationTaskWaitFinishEvent event, NormalIrrigationExecutor executor) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleTaskFinishedEvent(IrrigationTaskFinishedEvent event, NormalIrrigationExecutor executor) {
        executor.removeFinishedTask(event.getTask());
    }
}
