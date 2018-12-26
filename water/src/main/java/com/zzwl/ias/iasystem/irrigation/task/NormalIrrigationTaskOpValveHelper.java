package com.zzwl.ias.iasystem.irrigation.task;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.iasystem.OperateArgs;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.constant.DeviceConstant;
import com.zzwl.ias.iasystem.irrigation.event.IrrigationDevOpFinishedEvent;

/**
 * Created by huxin on 2018/4/10.
 */
class NormalIrrigationTaskOpValveHelper {
    private static final int DEFAULT_OP_VALVE_WAIT_TIME = 60;
    private static final int DEFAULT_OP_VALVE_TRY_LIMIT = 5;

    public static final int OP_VALVES_IN_PROGRESS = 1;
    public static final int OP_VALVES_SUCCESS = 2;
    public static final int OP_VALVES_FAIL = 3;

    private NormalIrrigationTask task;
    private int opType;
    private int tryLimit;
    private int waitTime;
    private Integer valves[];
    private boolean success[];
    private int runCount[];
    private ReqOperateDevice requests[];


    NormalIrrigationTaskOpValveHelper(NormalIrrigationTask task) {
        this.task = task;
        valves = task.getValves();
        tryLimit = DEFAULT_OP_VALVE_TRY_LIMIT;
        waitTime = DEFAULT_OP_VALVE_WAIT_TIME;
        if (valves != null) {
            success = new boolean[valves.length];
            runCount = new int[valves.length];
            requests = new ReqOperateDevice[valves.length];
        } else {
            success = null;
            runCount = null;
            requests = null;
        }
    }

    /**
     * 在所有的阀门上进行指定的操作
     *
     * @param opType 操作类型
     */
    void OpValves(int opType) {
        //阀门不存在时的处理
        if (valves == null || valves.length == 0) {
            IrrigationDevOpFinishedEvent event = new IrrigationDevOpFinishedEvent(task, null);
            AppContext.eventDispatcher.sendEvent(event);
            return;
        }

        this.opType = opType;
        for (int i = 0; i < valves.length; i++) {
            success[i] = false;
            runCount[i] = 0;
            requests[i] = null;
            opValve(i);
        }
    }

    /**
     * 检查阀门操作的结果
     *
     * @param event 设备操作完成事件
     * @return OP_VALVES_IN_PROGRESS表示操作为完成，需要继续等待；OP_VALVES_SUCCESS表示操作成功；OP_VALVES_FAIL表示操作失败
     */
    int checkResult(IrrigationDevOpFinishedEvent event) {
        //检查操作结果
        ReqOperateDevice request = event.getRequest();
        if (request == null) {
            //阀门不存在时的处理
            return OP_VALVES_SUCCESS;
        }

        int valveId = request.getIasDevId();
        for (int i = 0; i < valves.length; i++) {
            if (valves[i] == valveId) {
                if (requests[i] == request) {
                    runCount[i]++;
                    if (request.getResult() == ErrorCode.OK) {
                        //操作设备成功
                        success[i] = true;
                    } else {
                        //操作设备失败，重试
                        if (runCount[i] < tryLimit) {
                            if (request.getResult() == ErrorCode.CONNECTION_CLOSED || request.getResult() == ErrorCode.OPERATION_IN_PROGRESS){
                                int j = i;
                                AppContext.timerManager.createTimer(() -> opValve(j), waitTime, false);
                            }else {
                                opValve(i);
                            }
                        }
                    }
                    break;
                }
            }
        }

        //检查所有阀门操作结果
        boolean anyFail = false;
        for (int i = 0; i < valves.length; i++) {
            if (!success[i]) {
                anyFail = true;
                if (runCount[i] == tryLimit) {
                    return OP_VALVES_FAIL;
                }
            }
        }
        if (anyFail) {
            return OP_VALVES_IN_PROGRESS;
        } else {
            return OP_VALVES_SUCCESS;
        }
    }

    /**
     * 在指定的阀门进行操作
     *
     * @param index 阀门的下标
     */
    private void opValve(int index) {
        ReqOperateDevice request = createOpValveRequest(valves[index]);
        requests[index] = request;
        task.getIaSystem().operateDevice(request);
    }

    /**
     * 创建阀门操作请求
     *
     * @param valveId 阀门的ID
     * @return 阀门操作请求
     */
    private ReqOperateDevice createOpValveRequest(Integer valveId) {
        OperateArgs args;
        if (opType == IaCommandType.START_DEVICE) {
            args = new OperateArgs();
            args.setAutoStop(true); //断网自动关闭设备
            args.setDuration(0);    //设备持续运行，直到用户主动关闭
        }

        ReqOperateDevice request =  ReqOperateDevice.createOpDeviceRequest(
                0, DeviceConstant.OP_DEV_SRC_IRRI_TASK, valveId, opType, null, request1 ->
                {
                    IrrigationDevOpFinishedEvent event = new IrrigationDevOpFinishedEvent(task, request1);
                    AppContext.eventDispatcher.sendEvent(event);
                });

        request.setIasId(task.getIaSystemId());
        return request;
    }
}