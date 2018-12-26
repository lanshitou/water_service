package com.zzwl.ias.iasystem.communication.analyzer;

import com.zzwl.ias.iasystem.common.ProtoUtil;
import com.zzwl.ias.iasystem.common.SensorValue;
import com.zzwl.ias.iasystem.communication.IaFrame;

import java.util.LinkedList;

/**
 * Created by HuXin on 2018/3/26.
 */
public class ControllerStatusInfo {
    int systemId;
    LinkedList<SensorValue> sensorValues;

    private ControllerStatusInfo(int systemId) {
        this.systemId = systemId;
    }

    public static ControllerStatusInfo analyze(int systemId, IaFrame frame) {
        byte[] data;
        int len, index, count;
        SensorValue value;
        byte type;
        boolean valid;

        data = frame.getData();
        if (data == null) {
            return null;
        }
        len = data.length;

        //报文长度检查
        valid = false;
        index = 0;
        count = 0;
        while (index < len) {
            count++;
            //状态信息类型长度为1个字节
            if (index + 1 > len) {
                break;
            }
            type = data[index];
            index += 1;

            //状态信息长度检查
            index += ProtoUtil.getCtlerInfoLen(type);
            if (index > len) {
                break;
            }

            if (index == len) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            return null;
        }

        ControllerStatusInfo info = new ControllerStatusInfo(systemId);
        info.sensorValues = new LinkedList<>();
        index = 0;
        while (index < len) {
            type = data[index];
            index++;

            value = ProtoUtil.ctlerInfoN2H(type, data, index);
            if (value == null) {
                return null;
            }
            info.sensorValues.add(value);

            index += ProtoUtil.getCtlerInfoLen(type);
        }
        return info;
    }

    public int getSystemId() {
        return systemId;
    }

    public LinkedList<SensorValue> getSensorValues() {
        return sensorValues;
    }
}
