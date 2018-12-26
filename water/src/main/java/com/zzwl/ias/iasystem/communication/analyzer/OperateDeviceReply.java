package com.zzwl.ias.iasystem.communication.analyzer;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.communication.IaFrame;

/**
 * Created by HuXin on 2018/1/13.
 */
public class OperateDeviceReply {
    private DeviceAddr addr;
    private short type;
    private short sn;
    private ErrorCode result;
    private byte state;

    public static OperateDeviceReply analyze(int systemId, IaFrame frame) {
        OperateDeviceReply operateDeviceReply = new OperateDeviceReply();

        byte[] cmdData = frame.getData();
        if (cmdData == null) {
            return null;
        }

        try {
            operateDeviceReply.result = ErrorCode.getByCommandResult(cmdData[0]);
        } catch (IllegalArgumentException e) {
            operateDeviceReply.result = ErrorCode.CMD_ERROR_TYPE_NOT_DEF;
        }

        if (operateDeviceReply.result == ErrorCode.OK) {
            //命令执行成功，返回5个字节
            if (cmdData.length != 5) {
                return null;
            }
            operateDeviceReply.addr = new DeviceAddr(systemId, cmdData[1], cmdData[2], cmdData[3]);
            operateDeviceReply.state = cmdData[4];
        } else {
            //命令执行失败，返回1个字节
            if (cmdData.length != 1) {
                return null;
            }
            operateDeviceReply.addr = null;
        }
        operateDeviceReply.type = frame.getType();
        operateDeviceReply.sn = frame.getSerialNum();
        return operateDeviceReply;
    }

    public DeviceAddr getAddr() {
        return addr;
    }

    public short getType() {
        return type;
    }

    public short getSn() {
        return sn;
    }

    public ErrorCode getResult() {
        return result;
    }

    public byte getState() {
        return state;
    }
}
