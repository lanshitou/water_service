package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.ProtoUtil;
import com.zzwl.ias.iasystem.communication.IaCommandType;
import com.zzwl.ias.iasystem.communication.IaFrame;
import com.zzwl.ias.iasystem.communication.analyzer.OperateDeviceReply;
import com.zzwl.ias.timer.IaTimer;
import com.zzwl.ias.timer.TimeoutHandler;

/**
 * Created by HuXin on 2018/1/15.
 */
public class DeviceOperation{
    private ReqOperateDevice request;
    private Device device;          //设备自身
    private short commandSn;        //命令序列号
    private byte[] cmdData;         //命令数据
    private IaTimer timer;          //超时定时器

    private DeviceOperation() {
    }

    public short getCommandSn() {
        return commandSn;
    }

    public static DeviceOperation create(ReqOperateDevice request, Device device) {
        byte[] cmdData = new byte[ProtoUtil.getOpCmdDataLen(DeviceAddr.getDevTypeById(request.getDevId()), request.getOpType())];
        ErrorCode errorCode = ProtoUtil.getOpCmdData(request, cmdData);
        if (errorCode != ErrorCode.OK) {
            request.setResult(errorCode);
            return null;
        }
        DeviceOperation operation = new DeviceOperation();
        operation.request = request;
        operation.device = device;
        operation.cmdData = cmdData;
        return operation;
    }

    ErrorCode execute(short commandSn, int timeout) {
        this.commandSn = commandSn;

        if (request.getWakeUp()) {
            //构造一个唤醒报文并发送
            IaFrame frame = new IaFrame(DeviceAddr.getSysIdById(request.getDevId()), commandSn, (short) IaCommandType.KEEP_ALIVE, null);
            device.getMasterController().sendFrame(frame);

            //延时一段时间，尽量保证该报文与后续的操作命令报文不会合并为一个包发送。后续考虑用异步的方式实现
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //构造并发送命令
        IaFrame frame = new IaFrame(DeviceAddr.getSysIdById(request.getDevId()), commandSn, request.getOpType().shortValue(), cmdData);
        ErrorCode error = device.getMasterController().sendFrame(frame);
        if (error != ErrorCode.OK) {
            return error;
        }

        //创建超时定时器
        timer = AppContext.timerManager.createTimer(
                ()-> device.handleOperationTimeout(), timeout, false);

        return ErrorCode.OK;
    }

    void handleAck(OperateDeviceReply reply) {
        if (reply.getType() != request.getOpType() && reply.getSn() != commandSn) {
            return;
        }
        //删除超时定时器
        AppContext.timerManager.deleteTimer(timer);
        //获取命令执行结果
        if (reply.getResult() == ErrorCode.OK) {
            //更新设备状态
            device.updateDevInfo(reply.getState(), null);
        }
        request.setResult(reply.getResult());
    }

    void timeout() {
        //命令执行超时
        request.setResult(ErrorCode.OPERATION_TIMEOUT);
    }
}
