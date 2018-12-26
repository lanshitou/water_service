package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceOperateDO;
import com.zzwl.ias.dto.RequestDTO;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.operation.OperateDeviceFinishHandler;
import com.zzwl.ias.mapper.DeviceOperateDOExtMapper;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-23
 * Time: 20:55
 */
public class ReqOperateDevice extends RequestDTO{
    private Integer iasId;
    private Integer iasDevId;
    private Integer opType;
    private OperateArgs args;

    private Integer source;
    private Long devId;
    private OperateDeviceFinishHandler finishHandler;
    private ErrorCode result;
    private Boolean wakeUp;

    public ReqOperateDevice(){
        result = ErrorCode.OK;
    }

    private ReqOperateDevice(Integer userId){
        super(userId);
    }

    @Override
    public void check() {
        ErrorCode errorCode = ErrorCode.OK;
        if (opType == null){
            errorCode = ErrorCode.INVALID_PARAMS;
        }
        if (opType == IaCommandType.START_DEVICE){
            if (args == null){
                errorCode = ErrorCode.INVALID_PARAMS;
            }
            if (args.getAutoStop() == null || args.getDuration() == null){
                errorCode = ErrorCode.INVALID_PARAMS;
            }
        }else if (opType == IaCommandType.STOP_DEVICE){
            args = null;
        }else {
            errorCode = ErrorCode.INVALID_PARAMS;
        }

        AssertEx.isOK(errorCode);
    }


    /**
     * 创建一个设备操作请求
     *
     * @param userId                     进行操作的用户ID
     * @param source                     操作的来源
     * @param devId                      设备ID
     * @param opType                     操作类型
     * @param args                       操作参数。正确性需要由调用者保证。如果是操作仅有开关两种状态的设备，该参数为null时，默认断网自动关闭设备，持续执行
     * @param operateDeviceFinishHandler 操作完成时异步回调函数
     * @return
     */
    public static ReqOperateDevice  createOpDeviceRequest(Integer userId, Integer source, Integer devId, Integer opType, OperateArgs args, OperateDeviceFinishHandler operateDeviceFinishHandler) {
        ReqOperateDevice request = new ReqOperateDevice(userId);
        request.setSource(source);
        request.setIasDevId(devId);

        request.setOpType(opType);
        if (opType == IaCommandType.START_DEVICE && args == null) {
            args = new OperateArgs();
            args.setAutoStop(true);    //断网自动关闭设备
            args.setDuration(0);    //设备持续运行，直到用户主动关闭
            request.setArgs(args);
        } else {
            request.setArgs(args);
        }
        request.setFinishHandler(operateDeviceFinishHandler);
        return request;
    }

    public void setResult(ErrorCode result) {
        this.result = result;
        record();
        if (finishHandler != null){
            finishHandler.notify(this);
        }
    }

    private void record(){
        DeviceOperateDO deviceOperateDO = new DeviceOperateDO();
        deviceOperateDO.setIasDevId(iasDevId);
        deviceOperateDO.setOpType(opType);
        if (args != null){
            deviceOperateDO.setAutoStop(args.getAutoStop());
            deviceOperateDO.setDuration(args.getDuration());
            deviceOperateDO.setParam(args.getPosition());
        }
        deviceOperateDO.setResult(result.getValue());
        deviceOperateDO.setSource(source);
        deviceOperateDO.setUserId(userId);
        AppContext.getBean(DeviceOperateDOExtMapper.class)
                .insert(deviceOperateDO);
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Integer getIasDevId() {
        return iasDevId;
    }

    public void setIasDevId(Integer iasDevId) {
        this.iasDevId = iasDevId;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public OperateArgs getArgs() {
        return args;
    }

    public void setArgs(OperateArgs args) {
        this.args = args;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public OperateDeviceFinishHandler getFinishHandler() {
        return finishHandler;
    }

    public void setFinishHandler(OperateDeviceFinishHandler finishHandler) {
        this.finishHandler = finishHandler;
    }

    public ErrorCode getResult() {
        return result;
    }

    public Boolean getWakeUp() {
        return wakeUp;
    }

    public void setWakeUp(Boolean wakeUp) {
        this.wakeUp = wakeUp;
    }
}
