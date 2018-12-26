package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description: 灌溉任务执行器等待状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:12
 */
class NormalIrrigationExecutorStateWaiting extends NormalIrrigationExecutorState {
    @Override
    void handleAddTaskEvent(AddIrrigationTasksEvent event, NormalIrrigationExecutor executor) {
        //添加任务
        executor.addTasks(event);
        //启动可以运行的任务
        executor.startRunnableTasks();
        //进入等待阀门启动状态
        executor.setState(NormalIrrigationExecutorState.STATE_WAITING_VALVES_STARTED);
    }

    @Override
    void handleRemoveTaskEvent(RemoveIrrigationTasksEvent event, NormalIrrigationExecutor executor) {
        receiveUnexpectedEventHandler(event);
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
        receiveUnexpectedEventHandler(event);
    }
}
