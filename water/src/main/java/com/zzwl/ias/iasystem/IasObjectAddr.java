package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.domain.IrrigationAreaRecord;
import com.zzwl.ias.dto.common.IasObjectAddrDTO;
import com.zzwl.ias.iasystem.constant.IasObjectType;
import com.zzwl.ias.mapper.*;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-15
 * Time: 8:56
 */
public class IasObjectAddr {
    private Integer type;           //类型，类型定义参看IasObjectType
    private Integer iasId;          //智慧农业系统ID
    private Integer irriFerId;      //水肥一体化子系统ID
    private Integer farmlandId;     //农田ID
    private Integer irriAreaId;     //灌溉区ID
    private Integer parentDevId;    //传感器所挂接的可操作设备的ID
    private Integer devId;          //设备ID

    public IasObjectAddr(){
        type = null;
        iasId = null;
        irriFerId = null;
        farmlandId = null;
        irriAreaId = null;
        parentDevId = null;
        devId = null;
    }

    public static IasObjectAddr createIasAddr(Integer iasId){
        IasObjectAddr addr = new IasObjectAddr();
        addr.setType(IasObjectType.IASYSTEM);
        addr.setIasId(iasId);
        return addr;
    }

    public static IasObjectAddr createIasFarmlandAddr(Integer iasId, Integer farmlandId){
        IasObjectAddr addr = new IasObjectAddr();
        addr.setType(IasObjectType.FARMLAND);
        addr.setIasId(iasId);
        addr.setFarmlandId(farmlandId);
        return addr;
    }

    public static IasObjectAddr createIasIrriAreaAddr(Integer iasId, Integer farmlandId, Integer irriAreaId){
        IasObjectAddr addr = new IasObjectAddr();
        addr.setType(IasObjectType.IRRI_AREA);
        addr.setIasId(iasId);
        addr.setFarmlandId(farmlandId);
        addr.setIrriAreaId(irriAreaId);
        return addr;
    }

    public static IasObjectAddr createIasIrriFerAddr(Integer iasId, Integer irriFerId){
        IasObjectAddr addr = new IasObjectAddr();
        addr.setType(IasObjectType.IRRI_FER);
        addr.setIasId(iasId);
        addr.setIrriFerId(irriFerId);
        return addr;
    }

    public static IasObjectAddr createIasDevAddr(IasDeviceRecord deviceRecord){
        IasObjectAddr addr = new IasObjectAddr();
        addr.setType(deviceRecord.getUsageType());
        addr.setIasId(deviceRecord.getIasId());
        addr.setFarmlandId(deviceRecord.getFarmlandId());
        addr.setIrriAreaId(deviceRecord.getIrriAreaId());
        addr.setIrriFerId(deviceRecord.getIrriFerId());
        if (deviceRecord.getUsageType() == IasObjectType.OP_DEV_SENSOR)
        {
            //TODO:当前数据库中ias_dev_id字段保存的是设备的ID，需要进行修改
            addr.setParentDevId(deviceRecord.getIasDevId().intValue());
        }
        else{
            addr.setParentDevId(null);
        }
        addr.setDevId(deviceRecord.getId());
        return addr;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof IasObjectAddr)){
            return false;
        }

        IasObjectAddr object = (IasObjectAddr)obj;

        if (type.equals(object.type) && iasId.equals(object.iasId) && irriFerId.equals(object.irriFerId) && farmlandId.equals(object.farmlandId)
                && irriAreaId.equals(object.irriAreaId) && parentDevId.equals(object.parentDevId) && devId.equals(object.devId)){
            return true;
        }

        return false;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIasId() {
        return iasId;
    }

    public void setIasId(Integer iasId) {
        this.iasId = iasId;
    }

    public Integer getIrriFerId() {
        return irriFerId;
    }

    public void setIrriFerId(Integer irriFerId) {
        this.irriFerId = irriFerId;
    }

    public Integer getFarmlandId() {
        return farmlandId;
    }

    public void setFarmlandId(Integer farmlandId) {
        this.farmlandId = farmlandId;
    }

    public Integer getIrriAreaId() {
        return irriAreaId;
    }

    public void setIrriAreaId(Integer irriAreaId) {
        this.irriAreaId = irriAreaId;
    }

    public Integer getParentDevId() {
        return parentDevId;
    }

    public void setParentDevId(Integer parentDevId) {
        this.parentDevId = parentDevId;
    }

    public Integer getDevId() {
        return devId;
    }

    public void setDevId(Integer devId) {
        this.devId = devId;
    }

    public static String getIasName(Integer iasId) {
        if (iasId != null){
            return AppContext.getBean(IaSystemRecordMapper.class).selectByPrimaryKey(iasId).getName();
        }
        return null;
    }

    public static String getIrriFerName(Integer irriFerId) {
        if (irriFerId != null){
            return AppContext.getBean(IrriFerRecordMapper.class).selectByPrimaryKey(irriFerId).getName();
        }
        return null;
    }

    public static String getFarmlandName(Integer farmlandId) {
        if (farmlandId != null){
            return AppContext.getBean(FarmlandRecordExtMapper.class).selectByPrimaryKey(farmlandId).getName();
        }
        return null;
    }

    public static String getIrriAreaName(Integer irriAreaId) {
        if (irriAreaId != null){
            return AppContext.getBean(IrrigationAreaRecordExtMapper.class).selectByPrimaryKey(irriAreaId).getName();
        }
        return null;
    }

    public static String getParentDevName(Integer parentDevId) {
        if (parentDevId != null){
            return AppContext.getBean(IasDeviceRecordExtMapper.class).selectByPrimaryKey(parentDevId).getName();
        }
        return null;
    }

    public static String getDevName(Integer devId) {
        if (devId != null){
            return AppContext.getBean(IasDeviceRecordExtMapper.class).selectByPrimaryKey(devId).getName();
        }
        return null;
    }

    public static void getIasObjectAddrName(IasObjectAddrDTO iasObjectAddrDTO){
        iasObjectAddrDTO.setIasName(getIasName(iasObjectAddrDTO.getIasId()));
        iasObjectAddrDTO.setIrriFerName(getIrriFerName(iasObjectAddrDTO.getIrriFerId()));
        iasObjectAddrDTO.setFarmlandName(getFarmlandName(iasObjectAddrDTO.getFarmlandId()));
        iasObjectAddrDTO.setAreaName(getIrriAreaName(iasObjectAddrDTO.getAreaId()));
        iasObjectAddrDTO.setDevName(getDevName(iasObjectAddrDTO.getDevId()));
        iasObjectAddrDTO.setParentDevName(getParentDevName(iasObjectAddrDTO.getParentDevId()));
    }
}
