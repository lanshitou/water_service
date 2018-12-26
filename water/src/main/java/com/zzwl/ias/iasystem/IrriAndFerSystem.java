package com.zzwl.ias.iasystem;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.domain.IrriFerRecord;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.dto.iasystem.IrriAndFerSystemDTO;
import com.zzwl.ias.dto.iasystem.DeviceDTO;

/**
 * Description:水肥一体化系统
 * User: HuXin
 * Date: 2018-04-10
 * Time: 14:25
 */
public class IrriAndFerSystem extends IasCollectableObject {
    IrriFerRecord irriFerRecord;
    IasOpDevice pump;

    public IrriAndFerSystem(IrriFerRecord irriFerRecord) {
        super();
        this.irriFerRecord = irriFerRecord;
        setAddr(IasObjectAddr.createIasIrriFerAddr(irriFerRecord.getIasId(), irriFerRecord.getId()));
        pump = null;
    }

    public ErrorCode updateIrriFer(IrriFerRecord irriFerRecord) {
        this.irriFerRecord.setName(irriFerRecord.getName());
        return ErrorCode.OK;
    }

    public ErrorCode addPump(IasOpDevice pump) {
        if (this.pump != null) {
            return ErrorCode.PUMP_ALREADY_EXIST;
        }

        if (!DeviceAddr.isPump(pump.getDevId())) {
            return ErrorCode.DEV_NOT_PUMP;
        }

        this.pump = pump;

        return ErrorCode.OK;
    }

    public ErrorCode delPump(IasDeviceRecord iasDeviceRecord) {
        if (this.pump.getDevId() == iasDeviceRecord.getDevId()) {
            this.pump = null;
            return ErrorCode.OK;
        }
        return ErrorCode.IAS_DEVICE_NOT_EXIST;
    }

    public Integer getPumpId() {
        if (pump == null) {
            return null;
        }
        return pump.getId();
    }

    public IasOpDevice getPump(){
        return pump;
    }

    @Override
    public int getId() {
        return irriFerRecord.getId();
    }

    @Override
    String getName() {
        return irriFerRecord.getName();
    }

    @Override
    public int getSortOrder() {
        return 0;
    }

    private DeviceDTO getPumpVo() {
        if (pump != null) {
            return pump.getNormalDeviceVo();
        }
        return null;
    }

    public IasObject getDeviceById(long id) {
        if (pump != null) {
            return pump.getDeviceById(id);
        }
        return null;
    }

    public IasObject findIasObjectByAddr(IasObjectAddr addr) {
        IasObject object;
        if (getAddr().equals(addr)){
            return this;
        }

        //在传感器中查找
        object = findByAddr(addr);
        if (object !=null){
            return object;
        }

        //查找水泵
        if (pump != null && pump.getAddr().equals(addr)){
            return pump;
        }

        return null;
    }


    /** 获取水肥一体化系统信息
     * @return 水肥一体化系统信息
     */
    IrriAndFerSystemDTO getIrriAndFerSystemVo() {
        IrriAndFerSystemDTO irriAndFerSystemDTO = new IrriAndFerSystemDTO(irriFerRecord);
        irriAndFerSystemDTO.setPump(getPumpVo());
        irriAndFerSystemDTO.setDcPoints(getDcPointVos());
        return irriAndFerSystemDTO;
    }
}
