package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description: 灌溉任务执行器等待阀门开启状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:15
 */
class NormalIrrigationExecutorStateWaitingValvesStarted extends NormalIrrigationExecutorState {
    @Override
    void handleAddTaskEvent(AddIrrigationTasksEvent event, NormalIrrigationExecutor executor) {
        //添加任务
        executor.addTasks(event);
        //启动可以运行的任务
        executor.startRunnableTasks();
    }

    @Override
    void handleRemoveTaskEvent(RemoveIrrigationTasksEvent event, NormalIrrigationExecutor executor) {
        //删除(停止)任务
        executor.removeTasks(event);
    }

    @Override
    void handleValvesStartedEvent(IrrigationTaskValvesStartedEvent event, NormalIrrigationExecutor executor) {
        //启动水泵
        executor.opPump(IaCommandType.START_DEVICE);
        executor.setState(NormalIrrigationExecutorState.STATE_STARTING_PUMP);
    }

    @Override
    void handleDevOpFinishedEvent(IrrigationDevOpFinishedEvent event, NormalIrrigationExecutor executor) {
        receiveUnconcernedEventHandler(event);
    }

    @Override
    void handleTaskWaitFinishEvent(IrrigationTaskWaitFinishEvent event, NormalIrrigationExecutor executor) {
        //启动可以运行的任务
        executor.startRunnableTasks();

        //水泵未启动，通知灌溉任务可以关闭了
        IrrigationTaskFinishableEvent finishableEvent = new IrrigationTaskFinishableEvent(event.getTask());
        AppContext.eventDispatcher.sendEvent(finishableEvent);

        //没有其他正在运行的任务了，Executor进入等待所有任务结束状态
        if (executor.getRunningTaskNum() == 0) {
            executor.setState(NormalIrrigationExecutorState.STATE_WAITING_FINISH);
        }
    }

    @Override
    void handleTaskFinishedEvent(IrrigationTaskFinishedEvent event, NormalIrrigationExecutor executor) {
        executor.removeFinishedTask(event.getTask());
    }
}
