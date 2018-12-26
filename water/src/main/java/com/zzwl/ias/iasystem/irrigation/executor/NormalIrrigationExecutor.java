package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.iasystem.IasOpDevice;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.irrigation.IrrigationUtil;
import com.zzwl.ias.iasystem.irrigation.event.*;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTask;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTaskState;
import com.zzwl.ias.service.IrrigationService;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;
import com.zzwl.ias.vo.iasystem.irrigation.NormalIrrigationTaskVo;

import java.util.LinkedList;

/**
 * Description: 普通场景灌溉任务执行器。
 * User: HuXin
 * Date: 2018-04-10
 * Time: 8:49
 */
public class NormalIrrigationExecutor {
    private static NormalIrrigationExecutorStateWaiting stateWaiting = new NormalIrrigationExecutorStateWaiting();
    private static NormalIrrigationExecutorStateWaitingValvesStarted stateWaitingValvesStarted = new NormalIrrigationExecutorStateWaitingValvesStarted();
    private static NormalIrrigationExecutorStateStartingPump stateStartingPump = new NormalIrrigationExecutorStateStartingPump();
    private static NormalIrrigationExecutorStateIrrigating stateIrrigating = new NormalIrrigationExecutorStateIrrigating();
    private static NormalIrrigationExecutorStateStoppingPump stateStoppingPump = new NormalIrrigationExecutorStateStoppingPump();
    private static NormalIrrigationExecutorStateWaitingFinish stateWaitingFinish = new NormalIrrigationExecutorStateWaitingFinish();
    private NormalIrrigationExecutorState currState;

    private IaSystem iaSystem;
    private int iaSystemId;
    private int maxIrriNum;
    private LinkedList<NormalIrrigationTask> tasks;
    private NormalIrrigationExecutorOpPumpHelper opPumpHelper;

    public LinkedList<NormalIrrigationTask> getTasks() {
        return tasks;
    }

    public NormalIrrigationExecutor(IaSystem iaSystem) {
        this.iaSystem = iaSystem;
        maxIrriNum = iaSystem.getMaxIrriNum();
        tasks = new LinkedList<>();
        iaSystemId = iaSystem.getIaSystemId();
        opPumpHelper = new NormalIrrigationExecutorOpPumpHelper(this);
        currState = stateWaiting;
    }

    public int getIaSystemId() {
        return iaSystemId;
    }

    public IaSystem getIaSystem(){
        return iaSystem;
    }


    /**
     * 检查当前是否正在浇水
     *
     * @return 正在浇水返回true，否则返回false
     */
    public boolean isIrrigating() {
        return tasks.size() != 0;
    }

    /**
     * 获取当前所有任务的状态信息
     *
     * @return 任务状态列表
     */
    public LinkedList<IrrigationTaskStateVo> getTaskStateVo() {
        //获取当前任务状态
        LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos;
        irrigationTaskStateVos = new LinkedList<>();

        for (NormalIrrigationTask irrigationTask : tasks) {
            IrrigationTaskStateVo irrigationTaskStateVo = irrigationTask.getTaskStateVo();
            irrigationTaskStateVos.add(irrigationTaskStateVo);
        }

        //预估任务起始和结束时间
        IrrigationUtil.estimateTaskTime(irrigationTaskStateVos, iaSystem.getMaxIrriNum());

        return irrigationTaskStateVos;
    }

    /**
     * 设置Executor的状态
     *
     * @param state 新的状态
     */
    void setState(int state) {
        NormalIrrigationExecutorState oldState = currState;
        switch (state) {
            case NormalIrrigationExecutorState.STATE_WAITING: {
                currState = NormalIrrigationExecutor.stateWaiting;
                break;
            }
            case NormalIrrigationExecutorState.STATE_WAITING_VALVES_STARTED: {
                currState = NormalIrrigationExecutor.stateWaitingValvesStarted;
                break;
            }
            case NormalIrrigationExecutorState.STATE_STARTING_PUMP: {
                currState = NormalIrrigationExecutor.stateStartingPump;
                break;
            }
            case NormalIrrigationExecutorState.STATE_IRRIGATING: {
                currState = NormalIrrigationExecutor.stateIrrigating;
                break;
            }
            case NormalIrrigationExecutorState.STATE_STOPPING_PUMP: {
                currState = NormalIrrigationExecutor.stateStoppingPump;
                break;
            }
            case NormalIrrigationExecutorState.STATE_WAITING_FINISH: {
                currState = NormalIrrigationExecutor.stateWaitingFinish;
                break;
            }
            default: {
                //do nothing
            }
        }
        System.out.println(String.format("####Task Executor state changed: %s -> %s",
                oldState.getName(), currState.getName()));
    }


    /**
     * 添加灌溉任务
     *
     * @param event 添加任务请求事件
     */
    void addTasks(AddIrrigationTasksEvent event) {
        ReqUpdateNormalIrrigationDTO requestVo = event.getRequestVo();
        for (NormalIrrigationTaskVo taskVo : requestVo.getTasks()) {
            NormalIrrigationTask task = new NormalIrrigationTask(this, taskVo, requestVo.getUserId());
            tasks.add(task);
        }
    }

    /**
     * 删除(停止)灌溉任务
     *
     * @param event 删除灌溉任务事件
     */
    void removeTasks(RemoveIrrigationTasksEvent event) {
        ReqUpdateNormalIrrigationDTO requestVo = event.getRequestVo();
        for (NormalIrrigationTaskVo taskVo : requestVo.getTasks()) {
            if (taskVo.getResult() == ErrorCode.OK) {
                NormalIrrigationTask task = findTask(taskVo.getFarmlandId(), taskVo.getAreaId());
                if (task != null) {
                    task.setDeleteUser(requestVo.getUserId());
                    StopIrrigationTaskEvent stopIrrigationTaskEvent = new StopIrrigationTaskEvent(task);
                    stopIrrigationTaskEvent.setUserCancel(true);
                    task.handleEvent(stopIrrigationTaskEvent);
                }
            }
        }
    }

    /**
     * 启动可以运行的任务
     */
    void startRunnableTasks() {
        while (getRunningTaskNum() < maxIrriNum) {
            NormalIrrigationTask task = getNextWaitingTask();
            if (task == null) {
                break;
            }
            StartIrrigationEvent event = new StartIrrigationEvent(task);
            task.handleEvent(event);
        }
    }

    /**
     * 获取当前处于运行状态的灌溉任务的数量
     *
     * @return 处于运行状态的灌溉任务数量
     */
    public int getRunningTaskNum() {
        int count;
        int state;

        count = 0;
        for (NormalIrrigationTask task : tasks) {
            state = task.getState();
            if (state == NormalIrrigationTaskState.STATE_STARTING_VALVES
                    || state == NormalIrrigationTaskState.STATE_WAITING_PUMP_STARTED
                    || state == NormalIrrigationTaskState.STATE_IRRIGATING) {
                count++;
            }
        }
        return count;
    }

    /**
     * 对水泵进行执行的操作
     *
     * @param opType 指定的操作
     */
    void opPump(int opType) {
        opPumpHelper.opPump(opType);
    }

    /**
     * 检查水泵操作结果
     *
     * @param event 水泵操作结果
     * @return OP_PUMP_IN_PROGRESS表示操作正在进行中，OP_PUMP_SUCCESS表示操作成功，OP_PUMP_FAIL表示操作失败
     */
    int checkOpPumpResult(IrrigationDevOpFinishedEvent event) {
        return opPumpHelper.checkResult(event);
    }

    /**
     * 停止处于等待结束状态的任务
     */
    void stopWaitingFinishTasks() {
        int state;
        for (NormalIrrigationTask task : tasks) {
            state = task.getState();
            if (state == NormalIrrigationTaskState.STATE_WAITING_FINISH) {
                IrrigationTaskFinishableEvent event = new IrrigationTaskFinishableEvent(task);
                task.handleEvent(event);
            }
        }
    }

    /**
     * 通知所有等待水泵开启的任务水泵已经开启了
     */
    void notifyPumpStarted() {
        int state;
        for (NormalIrrigationTask task : tasks) {
            state = task.getState();
            if (state == NormalIrrigationTaskState.STATE_WAITING_PUMP_STARTED) {
                ExecutorPumpStartedEvent event = new ExecutorPumpStartedEvent(task);
                task.handleEvent(event);
            }
        }
    }

    /**
     * 停止所有的任务
     */
    public void stopAllTasks() {
        for (NormalIrrigationTask task : tasks) {
            StopIrrigationTaskEvent event = new StopIrrigationTaskEvent(task);
            task.handleEvent(event);
        }
    }

    /**
     * 停止处于等待停止状态的任务
     *
     * @param task 等待停止的任务
     */
    void stopWaitFinishTask(NormalIrrigationTask task) {
        startRunnableTasks();
        if (existOtherValveOpenedTask(task)) {
            IrrigationTaskFinishableEvent finishableEvent = new IrrigationTaskFinishableEvent(task);
            task.handleEvent(finishableEvent);
        } else {
            if (getRunningTaskNum() == 0) {
                opPump(IaCommandType.STOP_DEVICE);
                setState(NormalIrrigationExecutorState.STATE_STOPPING_PUMP);
            }
        }
    }


    /**
     * 检查是否所有的任务都处于等待状态
     *
     * @return 不存在任何任务或者任务都处于等待状态则返回true，否则返回false
     */
    boolean isAllTaskWaiting() {
        for (NormalIrrigationTask task : tasks) {
            if (task.getState() != NormalIrrigationTaskState.STATE_WAITING) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断任务队列是否为空
     *
     * @return 为空返回true，否则返回false
     */
    boolean isTaskListEmpty() {
        return tasks.isEmpty();
    }

    public void irrigationRequestCheck(ReqUpdateNormalIrrigationDTO requestVo) {
        for (NormalIrrigationTaskVo taskVo : requestVo.getTasks()) {
            NormalIrrigationTask task = getTask(taskVo.getFarmlandId(), taskVo.getAreaId());
            if (requestVo.getRequestType() == ReqUpdateNormalIrrigationDTO.REQUEST_TYPE_ADD){
                AssertEx.isTrue(task == null, ErrorCode.IRRIGATION_TASK_EXIST);
            }else {
                AssertEx.isTrue(task != null, ErrorCode.IRRIGATION_TASK_NOT_EXIST);
            }
        }
    }

    private NormalIrrigationTask getTask(int farmlandId, int areaId) {
        for (NormalIrrigationTask task : tasks) {
            if (task.getFarmlandId() == farmlandId && task.getAreaId() == areaId) {
                return task;
            }
        }
        return null;
    }


    /**
     * 获取下一个处于等待状态的任务
     *
     * @return 下一个处于等待状态的任务
     */
    private NormalIrrigationTask getNextWaitingTask() {
        int state;
        for (NormalIrrigationTask task : tasks) {
            state = task.getState();
            if (state == NormalIrrigationTaskState.STATE_WAITING) {
                return task;
            }
        }
        return null;
    }

    /**
     * 根据农田ID和灌溉区域ID查找灌溉任务
     *
     * @param farmlandId 农田ID
     * @param areaId     灌溉区域ID
     * @return 找到的灌溉任务，未找到时返回null
     */
    private NormalIrrigationTask findTask(int farmlandId, int areaId) {
        for (NormalIrrigationTask task : tasks) {
            if (task.getFarmlandId() == farmlandId && task.getAreaId() == areaId) {
                return task;
            }
        }
        return null;
    }

    /**
     * 检查除指定任务外，是否还存在其它阀门已经打开的任务
     *
     * @param task 指定任务
     * @return 存在返回true，否则返回false
     */
    private boolean existOtherValveOpenedTask(NormalIrrigationTask task) {
        int state;
        for (NormalIrrigationTask irrigationTask : tasks) {
            if (irrigationTask == task) {
                continue;
            }

            state = irrigationTask.getState();
            if (state == NormalIrrigationTaskState.STATE_WAITING_PUMP_STARTED
                    || state == NormalIrrigationTaskState.STATE_IRRIGATING) {
                return true;
            }
        }
        return false;
    }

    public Integer getPump() {
        Integer pumpId;

        if (iaSystem.getIrriAndFerSystem() != null && iaSystem.getIrriAndFerSystem().getPumpId() != null) {
            pumpId = iaSystem.getIrriAndFerSystem().getPumpId();
        } else {
            pumpId = null;
        }
        return pumpId;
    }

    //TODO 接口梳理

    /** 灌溉任务相关事件处理函数
     * @param event 灌溉任务相关事件
     */
    public void handleEvent(IrrigationEvent event) {
        if (event.getTargetType() == IrrigationEvent.TARGET_TYPE_TASK) {
            //Task相关的事件，由task进行处理
            event.getTask().handleEvent(event);
        } else {
            //Executor相关的事件，由自身进行处理
            currState.handleEvent(event, this);
        }
    }

    /** 更新灌溉任务
     * @param requestVo 更新灌溉任务请求
     */
    public void updateIrrigationTasks(ReqUpdateNormalIrrigationDTO requestVo) {
        IrrigationEvent event;
        if (requestVo.getRequestType() == ReqUpdateNormalIrrigationDTO.REQUEST_TYPE_ADD) {
            event = new AddIrrigationTasksEvent(requestVo);
        } else {
            event = new RemoveIrrigationTasksEvent(requestVo);
        }
        handleEvent(event);
    }

    /** 删除已经停止的灌溉任务
     * @param task 已经停止的灌溉任务
     */
    void removeFinishedTask(NormalIrrigationTask task) {
        AppContext.getBean(IrrigationService.class).recordIrrigationTaskAndNotify(task.getTaskStateVo());
        tasks.remove(task);
        startRunnableTasks();
        if (tasks.isEmpty()) {
            setState(NormalIrrigationExecutorState.STATE_WAITING);
        }
    }
}
