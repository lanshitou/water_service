package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description:灌溉任务执行器灌溉状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:17
 */
public class NormalIrrigationExecutorStateIrrigating extends NormalIrrigationExecutorState {
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
        ExecutorPumpStartedEvent pumpStartedEvent = new ExecutorPumpStartedEvent(event.getTask());
        event.getTask().handleEvent(pumpStartedEvent);

        //任务(最后一个任务除外)停止前需要等待后续任务的阀门启动
        //这里有新的阀门开启了，则这些任务就可以停止了
        executor.stopWaitingFinishTasks();
    }

    @Override
    void handleDevOpFinishedEvent(IrrigationDevOpFinishedEvent event, NormalIrrigationExecutor executor) {
        receiveUnexpectedEventHandler(event);
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
