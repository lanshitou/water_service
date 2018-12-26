package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.iasystem.event.IasEvent;
import com.zzwl.ias.iasystem.irrigation.event.*;

/**
 * Description: 灌溉任务执行器状态
 * User: HuXin
 * Date: 2018-04-10
 * Time: 8:53
 */
abstract class NormalIrrigationExecutorState {
    static final int STATE_WAITING = 1;
    static final int STATE_WAITING_VALVES_STARTED = 2;
    static final int STATE_STARTING_PUMP = 3;
    static final int STATE_IRRIGATING = 4;
    static final int STATE_STOPPING_PUMP = 5;
    static final int STATE_WAITING_FINISH = 6;

    abstract void handleAddTaskEvent(AddIrrigationTasksEvent event, NormalIrrigationExecutor executor);

    abstract void handleRemoveTaskEvent(RemoveIrrigationTasksEvent event, NormalIrrigationExecutor executor);

    abstract void handleValvesStartedEvent(IrrigationTaskValvesStartedEvent event, NormalIrrigationExecutor executor);

    abstract void handleDevOpFinishedEvent(IrrigationDevOpFinishedEvent event, NormalIrrigationExecutor executor);

    abstract void handleTaskWaitFinishEvent(IrrigationTaskWaitFinishEvent event, NormalIrrigationExecutor executor);

    abstract void handleTaskFinishedEvent(IrrigationTaskFinishedEvent event, NormalIrrigationExecutor executor);

    void handleEvent(IrrigationEvent event, NormalIrrigationExecutor executor) {
        if (event instanceof AddIrrigationTasksEvent) {
            handleAddTaskEvent((AddIrrigationTasksEvent) event, executor);
        } else if (event instanceof RemoveIrrigationTasksEvent) {
            handleRemoveTaskEvent((RemoveIrrigationTasksEvent) event, executor);
        } else if (event instanceof IrrigationTaskValvesStartedEvent) {
            handleValvesStartedEvent((IrrigationTaskValvesStartedEvent) event, executor);
        } else if (event instanceof IrrigationDevOpFinishedEvent) {
            handleDevOpFinishedEvent((IrrigationDevOpFinishedEvent) event, executor);
        } else if (event instanceof IrrigationTaskWaitFinishEvent) {
            handleTaskWaitFinishEvent((IrrigationTaskWaitFinishEvent) event, executor);
        } else if (event instanceof IrrigationTaskFinishedEvent) {
            handleTaskFinishedEvent((IrrigationTaskFinishedEvent) event, executor);
        } else {
            //到这里就出错啦
            receiveUnsupportedEventHandler(event);
        }
    }

    private void receiveUnsupportedEventHandler(IasEvent event) {
        String output = String.format("#####Receive unsupported event %s in %s state#####", event.getClass(), this.getClass());
        System.out.println(output);
    }

    void receiveUnexpectedEventHandler(IasEvent event) {
        String output = String.format("#####Receive unexpected event %s in %s state#####", event.getClass(), this.getClass());
        System.out.println(output);
    }

    void receiveUnconcernedEventHandler(IasEvent event) {
        String output = String.format("#####Receive unconcerned event %s in %s state#####", event.getClass(), this.getClass());
        System.out.println(output);
    }

    String getName() {
        String[] temps = this.getClass().getName().split("\\.");
        return temps[temps.length - 1];
    }
}
