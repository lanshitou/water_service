package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.dto.iasystem.DeviceDTO;
import com.zzwl.ias.iasystem.constant.WarningConstant;
import com.zzwl.ias.mapper.WarningDOExtMapper;

import java.util.List;

/**
 * Description:智慧农业系统中的可操作设备
 * User: HuXin
 * Date: 2018-04-26
 * Time: 10:52
 */
public class IasOpDevice extends IasCollectableObject {
    private IasDeviceRecord deviceRecord;

    public IasOpDevice(IasDeviceRecord deviceRecord) {
        this.deviceRecord = deviceRecord;
        setAddr(IasObjectAddr.createIasDevAddr(deviceRecord));
    }

    public int getIasId() {
        return deviceRecord.getIasId();
    }

    public int getFmId() {
        return deviceRecord.getFarmlandId();
    }

    public int getIrriAreaId() {
        return deviceRecord.getIrriAreaId();
    }

    public long getDevId() {
        return deviceRecord.getDevId();
    }


    @Override
    public int getId() {
        return deviceRecord.getId();
    }

    @Override
    String getName() {
        return deviceRecord.getName();
    }

    @Override
    public int getSortOrder() {
        return deviceRecord.getSortOrder();
    }

    public IasObject getDeviceById(long id) {
        if (getDevId() == id) {
            return this;
        } else {
            return getDevById(id);
        }
    }

    public IasObject findIasObjectByAddr(IasObjectAddr addr) {
        //检查是否为自身
        if (getAddr().equals(addr)){
            return this;
        }
        //检查传感器
        return findByAddr(addr);
    }

    /** 对设备进行操作
     * @param request 操作请求
     */
    public void operateDevice(ReqOperateDevice request) {
        request.setDevId(deviceRecord.getDevId());
        AppContext.deviceManager.operateDevice(request);
    }

    /** 获取设备信息
     * @return 设备信息
     */
    DeviceDTO getNormalDeviceVo() {
        DeviceDTO deviceDTO = new DeviceDTO(deviceRecord);
        deviceDTO.setStatus(AppContext.deviceManager.getDevice(deviceRecord.getDevId()).getState());
        deviceDTO.setDcPoints(getDcPointVos());
        //检查设备是否有告警
        List<WarningDO> warningDOS = AppContext.getBean(WarningDOExtMapper.class).listCurrWarningByAddrAndType(getAddr(), WarningConstant.DEVICE_OFFLINE, null);
        if (warningDOS.size() != 0){
            deviceDTO.setAlarmType(warningDOS.get(0).getType());
        }else{
            deviceDTO.setAlarmType(WarningConstant.WARNING_NONE);
        }
        return deviceDTO;
    }
}
