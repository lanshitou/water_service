package com.zzwl.ias.iasystem.irrigation.executor;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.iasystem.OperateArgs;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.constant.DeviceConstant;
import com.zzwl.ias.iasystem.irrigation.event.IrrigationDevOpFinishedEvent;

/**
 * Description:用户Executor操作水泵
 * User: HuXin
 * Date: 2018-04-11
 * Time: 9:18
 */
class NormalIrrigationExecutorOpPumpHelper {
    private static final int DEFAULT_OP_PUMP_WAIT_TIME = 60;
    private static final int DEFAULT_OPEN_PUMP_TRY_LIMIT = 5;
    private static final int DEFAULT_CLOSE_PUMP_TRY_LIMIT = 15;

    public static final int OP_PUMP_IN_PROGRESS = 1;
    public static final int OP_PUMP_SUCCESS = 2;
    public static final int OP_PUMP_FAIL = 3;

    private NormalIrrigationExecutor executor;
    private int iaSystemId;
    private int opType;
    private int tryLimit;
    private int waitTime;
    private int runCount;
    private ReqOperateDevice request;

    NormalIrrigationExecutorOpPumpHelper(NormalIrrigationExecutor executor) {
        this.executor = executor;
        this.iaSystemId = executor.getIaSystemId();
        waitTime = DEFAULT_OP_PUMP_WAIT_TIME;
    }

    /**
     * 在水泵上进行指定的操作
     *
     * @param opType 操作类型
     */
    void opPump(int opType) {
        runCount = 0;
        this.opType = opType;
        if (opType == IaCommandType.START_DEVICE) {
            tryLimit = DEFAULT_OPEN_PUMP_TRY_LIMIT;
        } else {
            tryLimit = DEFAULT_CLOSE_PUMP_TRY_LIMIT;
        }
        opPump();
    }

    /**
     * 检查水泵操作结果
     *
     * @param event 设备操作完成事件
     * @return OP_PUMP_IN_PROGRESS表示操作正在进行中，OP_PUMP_SUCCESS表示操作成功，OP_PUMP_FAIL表示操作失败
     */
    int checkResult(IrrigationDevOpFinishedEvent event) {
        //检查操作结果
        ReqOperateDevice request = event.getRequest();
        if (request == null) {
            //水泵不存在时的处理
            return OP_PUMP_SUCCESS;
        }

        if (this.request == request) {
            runCount++;
            if (request.getResult() == ErrorCode.OK) {
                //操作设备成功
                return OP_PUMP_SUCCESS;
            } else {
                //操作设备失败，重试
                if (runCount < tryLimit) {
                    if (request.getResult() == ErrorCode.CONNECTION_CLOSED || request.getResult() == ErrorCode.OPERATION_IN_PROGRESS){
                        AppContext.timerManager.createTimer(this::opPump, waitTime, false);
                    }else {
                        opPump();
                    }
                    return OP_PUMP_IN_PROGRESS;
                } else {
                    return OP_PUMP_FAIL;
                }
            }
        }
        return OP_PUMP_IN_PROGRESS;
    }

    /**
     * 操作水泵
     */
    private void opPump() {
        Integer pump = executor.getPump();
        if (pump == null) {
            //水泵不存在时的处理方式
            request = null;
            IrrigationDevOpFinishedEvent event = new IrrigationDevOpFinishedEvent(iaSystemId, null);
            AppContext.eventDispatcher.sendEvent(event);
            return;
        }

        request = createOpPumpRequest();
        executor.getIaSystem().operateDevice(request);
    }

    /**
     * 创建阀门操作请求
     *
     * @return 阀门操作请求
     */
    private ReqOperateDevice createOpPumpRequest() {
        Integer pump = executor.getPump();

        OperateArgs args = null;
        if (opType == IaCommandType.START_DEVICE) {
            args = new OperateArgs();
            args.setAutoStop(true); //断网自动关闭设备
            args.setDuration(0);    //设备持续运行，直到用户主动关闭
        }

        ReqOperateDevice request = ReqOperateDevice.createOpDeviceRequest(
                0, DeviceConstant.OP_DEV_SRC_IRRI_TASK, pump, opType, args, request1 ->
                {
                    IrrigationDevOpFinishedEvent event = new IrrigationDevOpFinishedEvent(iaSystemId, request1);
                    AppContext.eventDispatcher.sendEvent(event);
                });
        request.setIasId(executor.getIaSystemId());
        return request;
    }
}
