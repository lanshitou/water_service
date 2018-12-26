package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:灌溉任务执行器等待所有任务停止状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:19
 */
public class NormalIrrigationExecutorStateWaitingFinish extends NormalIrrigationExecutorState {
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
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleTaskWaitFinishEvent(IrrigationTaskWaitFinishEvent event, NormalIrrigationExecutor executor) {
        receiveUnexpectedEventHandler(event);
    }

    @Override
    void handleTaskFinishedEvent(IrrigationTaskFinishedEvent event, NormalIrrigationExecutor executor) {
        executor.removeFinishedTask(event.getTask());

        if (!executor.isAllTaskWaiting()) {
            //存在处于非等待状态的任务，继续等待所有任务完成
            return;
        }

        if (executor.isTaskListEmpty()) {
            //没有新加入任务，进入等待状态
            executor.setState(NormalIrrigationExecutorState.STATE_WAITING);
        } else {
            //存在新加入任务，启动这些任务。
            executor.startRunnableTasks();
            executor.setState(NormalIrrigationExecutorState.STATE_WAITING_VALVES_STARTED);
        }
    }
}
