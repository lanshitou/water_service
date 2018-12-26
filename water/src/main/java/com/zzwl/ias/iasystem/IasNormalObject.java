package com.zzwl.ias.iasystem;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.dto.iasystem.DeviceDTO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-05-03
 * Time: 11:34
 */
public abstract class IasNormalObject extends IasCollectableObject {
    List<IasOpDevice> normalDevice;

    public IasNormalObject() {
        normalDevice = new ArrayList<>();
    }

    public ErrorCode addNormalDevice(IasOpDevice iasOpDevice) {
        for (IasOpDevice iasOpDevice1 : normalDevice) {
            if (iasOpDevice1.getId() == iasOpDevice.getId()) {
                return ErrorCode.IAS_DEVICE_EXIST;
            }
        }
        normalDevice.add(iasOpDevice);
        return ErrorCode.OK;
    }

    public ErrorCode delNormalDevice(IasDeviceRecord iasDeviceRecord) {
        for (IasOpDevice iasOpDevice1 : normalDevice) {
            if (iasOpDevice1.getDevId() == iasDeviceRecord.getDevId()) {
                normalDevice.remove(iasOpDevice1);
                return ErrorCode.OK;
            }
        }
        return ErrorCode.IAS_DEVICE_NOT_EXIST;
    }

    protected LinkedList<DeviceDTO> getNormalDeviceVos() {
        LinkedList<DeviceDTO> deviceVos = new LinkedList<>();
        if (normalDevice.size() != 0) {
            for (IasOpDevice normal : normalDevice) {
                deviceVos.add(normal.getNormalDeviceVo());
            }
            return deviceVos;
        }
        return null;
    }

    @Override
    IasObject getDevById(long id) {
        IasObject iasObject;

        //检查传感器
        iasObject = super.getDevById(id);
        if (iasObject != null) {
            return iasObject;
        }

        //检查可操作设备
        for (IasOpDevice iasOpDevice : normalDevice) {
            iasObject = iasOpDevice.getDeviceById(id);
            if (iasObject != null) {
                return iasObject;
            }
        }
        return null;
    }

    @Override
    IasObject findByAddr(IasObjectAddr addr) {
        IasObject iasObject;

        //在传感器中找
        iasObject = super.findByAddr(addr);
        if (iasObject != null) {
            return iasObject;
        }

        //在可操作设备中找
        for (IasOpDevice iasOpDevice : normalDevice) {
            iasObject = iasOpDevice.findIasObjectByAddr(addr);
            if (iasObject != null) {
                return iasObject;
            }
        }
        return null;
    }
}
