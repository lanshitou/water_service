package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.iasystem.irrigation.event.IrrigationDevOpFinishedEvent;
import com.zzwl.ias.iasystem.irrigation.event.IrrigationEvent;
import com.zzwl.ias.iasystem.irrigation.executor.NormalIrrigationExecutor;
import com.zzwl.ias.iasystem.message.IrrigationMessageUtil;
import com.zzwl.ias.mapper.UserMapper;
import com.zzwl.ias.timer.IaTimer;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import com.zzwl.ias.vo.UserBasicInfoVo;
import com.zzwl.ias.vo.iasystem.irrigation.NormalIrrigationTaskVo;

import java.util.Calendar;

import static com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTaskState.*;

/**
 * Description:灌溉任务
 * User: HuXin
 * Date: 2018-04-10
 * Time: 9:20
 */
public class NormalIrrigationTask {
    private static NormalIrrigationTaskStateWaiting stateWaiting = new NormalIrrigationTaskStateWaiting();
    private static NormalIrrigationTaskStateStartingValves stateStartingValves = new NormalIrrigationTaskStateStartingValves();
    private static NormalIrrigationTaskStateWaitingPumpStarted stateWaitingPumpStarted = new NormalIrrigationTaskStateWaitingPumpStarted();
    private static NormalIrrigationTaskStateIrrigating stateIrrigating = new NormalIrrigationTaskStateIrrigating();
    private static NormalIrrigationTaskStateWaitingFinish stateWaitingFinish = new NormalIrrigationTaskStateWaitingFinish();
    private static NormalIrrigationTaskStateStoppingValves stateStoppingValves = new NormalIrrigationTaskStateStoppingValves();
    private static NormalIrrigationTaskStateFinish stateFinish = new NormalIrrigationTaskStateFinish();

    private NormalIrrigationTaskState currentState;
    private NormalIrrigationTaskOpValveHelper opValveHelper;

    private UserMapper userMapper = AppContext.getBean(UserMapper.class);

    private NormalIrrigationExecutor executor;
    private IaTimer timer;
    private Integer duration;
    private Integer iaSystemId;
    private Integer farmlandId;
    private Integer areaId;
    private Integer valves[];

    private Integer createUser;             //创建任务的用户
    private Integer deleteUser;             //删除任务的用户，没有删除的用户时为null
    private Calendar addTime;               //任务添加时间
    private Calendar startedTime;           //任务启动成功时间，任务未启动时为null
    private Calendar finishTime;            //任务完成时间
    private Calendar irriStartTime;         //灌溉开始时间
    private NormalIrriTaskResult result;    //任务执行的结果
    private boolean canceled = false;       //任务是否被用户取消

    public NormalIrrigationTask(NormalIrrigationExecutor executor, NormalIrrigationTaskVo taskVo, int userId) {
        this.executor = executor;
        this.iaSystemId = executor.getIaSystemId();
        this.valves = taskVo.getValves();
        this.opValveHelper = new NormalIrrigationTaskOpValveHelper(this);
        this.duration = taskVo.getDuration();
        this.createUser = userId;
        this.deleteUser = null;
        this.farmlandId = taskVo.getFarmlandId();
        this.areaId = taskVo.getAreaId();
        this.addTime = Calendar.getInstance();
        this.result = NormalIrriTaskResult.OK;
        this.currentState = stateWaiting;
    }


    public int getIaSystemId() {
        return iaSystemId;
    }

    public IaSystem getIaSystem(){
        return executor.getIaSystem();
    }


    public void handleEvent(IrrigationEvent event) {
        currentState.handleEvent(event, this);
    }


    public IrrigationTaskStateVo getTaskStateVo() {
        IrrigationTaskStateVo irrigationTaskStateVo = new IrrigationTaskStateVo();
        irrigationTaskStateVo.setIasId(getIaSystemId());
        irrigationTaskStateVo.setFarmlandId(farmlandId);
        irrigationTaskStateVo.setIrriAreaId(areaId);
        irrigationTaskStateVo.setExpDuration(duration);
        irrigationTaskStateVo.setStatus(getState());
        irrigationTaskStateVo.setAddTime(addTime.getTime());
        irrigationTaskStateVo.setStartTime(addTime.getTime());
        irrigationTaskStateVo.setIrriStartTime(irriStartTime != null ? irriStartTime.getTime() : null);
        irrigationTaskStateVo.setFinishTime(finishTime != null ? finishTime.getTime() : null);
        irrigationTaskStateVo.setResult(result.getCode());
        irrigationTaskStateVo.setCreateUser(new UserBasicInfoVo(createUser));
        if (deleteUser != null){
            irrigationTaskStateVo.setDeleteUser(new UserBasicInfoVo(deleteUser));
        }else {
            irrigationTaskStateVo.setDeleteUser(null);
        }

        return irrigationTaskStateVo;
    }

    /**
     * 设置灌溉任务状态
     *
     * @param state 灌溉任务状态
     */
    void setState(int state) {
        NormalIrrigationTaskState oldState = currentState;
        switch (state) {
            case STATE_WAITING: {
                currentState = NormalIrrigationTask.stateWaiting;
                break;
            }
            case STATE_STARTING_VALVES: {
                //创建灌溉任务开始执行的消息
                startedTime = Calendar.getInstance();
                currentState = NormalIrrigationTask.stateStartingValves;
                IrrigationMessageUtil.createTaskStateChangeMsg(getTaskStateVo());
                break;
            }
            case STATE_WAITING_PUMP_STARTED: {
                currentState = NormalIrrigationTask.stateWaitingPumpStarted;
                break;
            }
            case STATE_IRRIGATING: {
                currentState = NormalIrrigationTask.stateIrrigating;
                break;
            }
            case STATE_WAITING_FINISH: {
                finishTime = Calendar.getInstance();
                currentState = NormalIrrigationTask.stateWaitingFinish;
                break;
            }
            case STATE_STOPPING_VALVES: {
                currentState = NormalIrrigationTask.stateStoppingValves;
                break;
            }
            case STATE_FINISH: {
                currentState = NormalIrrigationTask.stateFinish;
                break;
            }
            default: {
                //do nothing
            }
        }

        System.out.println(String.format("********IrriTask(%d, %d) state changed: %s -> %s",
                farmlandId, areaId, oldState.getName(), currentState.getName()));
    }

    /**
     * 获取灌溉任务当前状态
     *
     * @return 设备当前状态
     */
    public int getState() {
        return currentState.getState();
    }

    /**
     * 在所有的阀门上进行指定的操作
     *
     * @param opType 操作类型
     */
    void opValves(int opType) {
        opValveHelper.OpValves(opType);
    }

    /**
     * 检查阀门操作的结果
     *
     * @param event 设备操作完成事件
     * @return OP_VALVES_IN_PROGRESS表示操作为完成，需要继续等待；OP_VALVES_SUCCESS表示操作成功；OP_VALVES_FAIL表示操作失败
     */
    int checkResult(IrrigationDevOpFinishedEvent event) {
        return opValveHelper.checkResult(event);
    }

    /**
     * 获取灌溉任务对应的灌溉区域中的所有阀门ID
     *
     * @return 存放阀门ID的数组
     */
    Integer[] getValves() {
        return valves;
    }


    /**
     * 获取任务执行时间
     *
     * @return 任务的执行时间，单位为秒
     */
    public int getDuration() {
        return duration;
    }

    /**
     * 移除任务超时定时器
     *
     * @return 任务超时定时器
     */
    void removeTimer() {
        if (timer != null) {
            AppContext.timerManager.deleteTimer(timer);
        }
    }

    /**
     * 设置任务超时定时器
     *
     * @param timer 任务超时定时器
     */
    void setTimer(IaTimer timer) {
        this.timer = timer;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(Integer farmlandId) {
        this.farmlandId = farmlandId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(Integer deleteUser) {
        this.deleteUser = deleteUser;
    }

    public Calendar getAddTime() {
        return addTime;
    }

    public void setAddTime(Calendar addTime) {
        this.addTime = addTime;
    }

    public Calendar getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Calendar startedTime) {
        this.startedTime = startedTime;
    }

    public Calendar getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Calendar finishTime) {
        this.finishTime = finishTime;
    }

    public NormalIrriTaskResult getResult() {
        return result;
    }

    public void setResult(NormalIrriTaskResult result) {
        if (this.result == NormalIrriTaskResult.OK) {
            this.result = result;
        }
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public Calendar getIrriStartTime() {
        return irriStartTime;
    }

    public void setIrriStartTime(Calendar irriStartTime) {
        this.irriStartTime = irriStartTime;
    }


    //TODO 代码梳理


}
