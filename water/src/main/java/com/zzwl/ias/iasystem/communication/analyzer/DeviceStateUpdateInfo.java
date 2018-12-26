package com.zzwl.ias.iasystem.communication.analyzer;

import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.common.DeviceType;
import com.zzwl.ias.iasystem.common.ProtoUtil;
import com.zzwl.ias.iasystem.communication.IaFrame;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by HuXin on 2018/1/13.
 */
public class DeviceStateUpdateInfo {
    private DeviceAddr addr;
    private int state;
    private int[] sensorValues;

    public static List<DeviceStateUpdateInfo> analyze(int systemId, IaFrame frame) {
        byte[] data;
        int len, index;
        byte ctlerId, devType, devId;
        boolean online, valid;

        data = frame.getData();
        if (data == null) {
            return null;
        }
        len = data.length;

        //报文长度检查
        valid = false;
        index = 0;
        while (index < len) {
            //控制器ID，设备类型，设备ID长度为3个字节
            if (index + 3 > len) {
                break;
            }
            devType = data[index + 1];
            index += 3;

            //设备信息长度
            index += ProtoUtil.getDevInfoLen(devType);
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

        LinkedList<DeviceStateUpdateInfo> infos = new LinkedList<>();
        index = 0;
        while (index < len) {
            DeviceStateUpdateInfo info = new DeviceStateUpdateInfo();

            //获取设备地址及是否在线信息
            ctlerId = data[index];
            index++;
            devType = data[index];
            index++;
            devId = data[index];
            index++;
            online = (devId & 0x80) != 0;
            devId = (byte) (devId & 0x7F);
            info.addr = new DeviceAddr(systemId, ctlerId, devType, devId);

            if (!info.addr.isValid()) {
                return null;
            }

            if (!online) {
                info.state = DeviceState.DEV_STATE_OFFLINE;
                info.sensorValues = null;
            } else {
                if (devType == DeviceType.DEV_CONTROLLER) {
                    info.state = DeviceState.DEV_STATE_OK;
                    info.sensorValues = null;
                } else if (devType >= DeviceType.OP_DEV_TYPE_MIN_ID && devType <= DeviceType.OP_DEV_TYPE_MAX_ID) {
                    int[] devInfo = ProtoUtil.devInfoN2H(devType, data, index);
                    info.state = devInfo[0];
                    info.sensorValues = null;
                } else {
                    info.state = DeviceState.DEV_STATE_OK;
                    info.sensorValues = ProtoUtil.devInfoN2H(devType, data, index);
                }
            }
            infos.add(info);

            index += ProtoUtil.getDevInfoLen(devType);
        }
        return infos;
    }

    public DeviceAddr getAddr() {
        return addr;
    }

    public int getState() {
        return state;
    }

    public int[] getSensorValues() {
        return sensorValues;
    }
}
