package com.zzwl.ias.iasystem;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.iasystem.DcPointDTO;

import java.util.LinkedList;

/**
 * Description:智慧农业普通对象。指的是除传感器设备以外的其它对象，这些对象都可以挂接传感器设备
 * User: HuXin
 * Date: 2018-04-27
 * Time: 9:02
 */
public abstract class IasCollectableObject extends IasObject {
    LinkedList<IasSensor> dcPoints;     //数据采集点


    public IasCollectableObject() {
        dcPoints = new LinkedList<>();
    }


    /**
     * 向智慧农业系统对象添加数据采集点
     *
     * @param iasSensor
     * @return
     */
    public ErrorCode addDcPoint(IasSensor iasSensor) {
        for (IasSensor dcPoint : dcPoints) {
            if (iasSensor.getDevId() == dcPoint.getDevId()) {
                return ErrorCode.DEVICE_EXIST;
            }
        }
        dcPoints.add(iasSensor);
        return ErrorCode.OK;
    }

    /**
     * 从智慧农业系统对象中移除数据采集点
     *
     * @param iasDeviceId
     * @return
     */
    public ErrorCode delDcPoint(long iasDeviceId) {
        for (IasSensor dcPoint : dcPoints) {
            if (dcPoint.getDevId() == iasDeviceId) {
                dcPoints.remove(dcPoint);
                return ErrorCode.OK;
            }
        }
        return ErrorCode.DEVICE_NOT_EXIST;
    }

    /**
     * 获取传感器点Vo
     *
     * @return
     */
    protected LinkedList<DcPointDTO> getSensorPointVos() {
        return getDcPointVos();
    }

    IasObject getDevById(long id) {
        for (IasSensor iasSensor : dcPoints) {
            if (iasSensor.getDevId() == id) {
                return iasSensor;
            }
        }
        return null;
    }

    IasObject findByAddr(IasObjectAddr addr){
        for (IasSensor iasSensor : dcPoints) {
            if (iasSensor.getAddr().equals(addr)) {
                return iasSensor;
            }
        }
        return null;
    }

    //TODO 接口梳理

    /** 获取数据采集点信息
     * @return 数据采集点信息
     */
    LinkedList<DcPointDTO> getDcPointVos() {
        if (dcPoints.size() != 0) {
            LinkedList<DcPointDTO> dcPointDTOList = new LinkedList<>();
            for (IasSensor sensor : dcPoints) {
                dcPointDTOList.addAll(sensor.getDcPointVo());
            }
            return dcPointDTOList;
        }
        return null;
    }
}
