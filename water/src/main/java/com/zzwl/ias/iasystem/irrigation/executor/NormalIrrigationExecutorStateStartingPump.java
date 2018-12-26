package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:灌溉任务执行器等待水泵启动
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:16
 */
public class NormalIrrigationExecutorStateStartingPump extends NormalIrrigationExecutorState {
    @Override
    void handleAddTaskEvent(AddIrrigationTasksEvent event, NormalIrrigationExecutor executor) {
        executor.addTasks(event);
        executor.startRunnableTasks();
    }

    @Override
    void handleRemoveTaskEvent(RemoveIrrigationTasksEvent event, NormalIrrigationExecutor executor) {
        executor.removeTasks(event);
    }

    @Override
    void handleValvesStartedEvent(IrrigationTaskValvesStartedEvent event, NormalIrrigationExecutor executor) {
        //任务(最后一个任务除外)停止前需要等待后续任务的阀门启动
        //这里有新的阀门开启了，则这些任务就可以停止了
        executor.stopWaitingFinishTasks();
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
                //启动水泵成功
                executor.notifyPumpStarted();
                executor.setState(NormalIrrigationExecutorState.STATE_IRRIGATING);
                break;
            }
            case NormalIrrigationExecutorOpPumpHelper.OP_PUMP_FAIL: {
                //启动水泵失败
                executor.stopAllTasks();
                //虽然水泵启动失败了，但是实际上可能已经开启了，进入关闭水泵状态
                executor.opPump(IaCommandType.STOP_DEVICE);
                executor.setState(NormalIrrigationExecutorState.STATE_STOPPING_PUMP);
                break;
            }
            default: {
                //do nothing
            }
        }
    }

    @Override
    void handleTaskWaitFinishEvent(IrrigationTaskWaitFinishEvent event, NormalIrrigationExecutor executor) {
        executor.stopWaitFinishTask(event.getTask());
    }

    @Override
    void handleTaskFinishedEvent(IrrigationTaskFinishedEvent event, NormalIrrigationExecutor executor) {
        executor.removeFinishedTask(event.getTask());
    }
}
