package com.zzwl.ias.iasystem;

import com.alibaba.druid.support.json.JSONUtils;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.configuration.CameraConfig;
import com.zzwl.ias.domain.*;
import com.zzwl.ias.dto.camera.*;
import com.zzwl.ias.dto.device.ReqSensorHistoryDataDTO;
import com.zzwl.ias.dto.device.SensorHistoryDataDTO;
import com.zzwl.ias.dto.iasystem.*;
import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;
import com.zzwl.ias.dto.warning.ReqConfigSensorThresholdWarningDTO;
import com.zzwl.ias.dto.warning.ReqGetSensorThresholdWarningCfg;
import com.zzwl.ias.dto.warning.ThresholdWarningCfgExtDTO;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.constant.CameraConstant;
import com.zzwl.ias.iasystem.constant.DeviceConstant;
import com.zzwl.ias.iasystem.constant.IaSystemConstant;
import com.zzwl.ias.iasystem.constant.IasObjectType;
import com.zzwl.ias.iasystem.device.Camera;
import com.zzwl.ias.iasystem.event.DeviceStateChangeEvent;
import com.zzwl.ias.iasystem.event.IasEvent;
import com.zzwl.ias.iasystem.irrigation.event.IrrigationEvent;
import com.zzwl.ias.iasystem.irrigation.executor.NormalIrrigationExecutor;
import com.zzwl.ias.mapper.CameraDOExtMapper;
import com.zzwl.ias.mapper.FarmlandRecordExtMapper;
import com.zzwl.ias.mapper.IasDeviceRecordExtMapper;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.vo.FarmlandBasicVo;
import com.zzwl.ias.vo.FarmlandVo;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import com.zzwl.ias.vo.iasystem.*;
import com.zzwl.ias.vo.iasystem.irrigation.NormalIrrigationTaskVo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-06-29
 * Time: 8:06
 */
public class IaSystem extends IasNormalObject {
    private IaSystemService iaSystemService;                //智慧农业系统数据库操作服务
    private IaSystemRecord iaSystemRecord;                  //智慧农业系统基础信息
    private LinkedList<Farmland> farmlands;                 //农田列表
    private IrriAndFerSystem irriAndFerSystem;              //水肥一体化系统
    private NormalIrrigationExecutor irrigationExecutor;    //灌溉任务执行器
    private LinkedList<Camera> cameras;                     //摄像头

    /**
     * 智慧农业系统构造函数
     *
     * @param iaSystemRecord 智慧农业系统在数据库中的记录
     */
    IaSystem(IaSystemRecord iaSystemRecord) {
        this.iaSystemRecord = iaSystemRecord;
        farmlands = new LinkedList<>();
        irriAndFerSystem = null;
        irrigationExecutor = new NormalIrrigationExecutor(this);
        cameras = new LinkedList<>();
        setAddr(IasObjectAddr.createIasAddr(iaSystemRecord.getId()));
        iaSystemService = AppContext.iaSystemService;
    }

    public synchronized IaSystemVo getIaSystemVo() {
        IaSystemVo iaSystemVo = new IaSystemVo(iaSystemRecord);
        iaSystemVo.setFarmlands(getFarmlandVos());
        iaSystemVo.setIrriAndFerSystems(getIrriAndFerSystemVo());
        iaSystemVo.setNormalDevices(getNormalDeviceVos());
        iaSystemVo.setDcPoints(getSensorPointVos());
        return iaSystemVo;
    }

    public synchronized IaSystemVo getIaSystemAreaVo() {
        IaSystemVo iaSystemVo = new IaSystemVo(iaSystemRecord);
        iaSystemVo.setFarmlands(getFarmlandAreaVos());
        return iaSystemVo;
    }

    @Override
    public int getId() {
        return iaSystemRecord.getId();
    }

    @Override
    String getName() {
        return iaSystemRecord.getName();
    }

    @Override
    public int getSortOrder() {
        return 0;
    }

    /**
     * 获取智慧农业系统的ID
     *
     * @return 智慧农业系统的ID
     */
    public int getIaSystemId() {
        return iaSystemRecord.getId();
    }

    public synchronized ErrorCode update(IasConfigVo iasConfigVo) {
        IaSystemRecord iaSystemRecord = new IaSystemRecord();
        iaSystemRecord.setId(iasConfigVo.getId());
        iaSystemRecord.setName(iasConfigVo.getName());
        iaSystemRecord.setSortOrder(iasConfigVo.getSortOrder());
        iaSystemRecord.setAlias(iasConfigVo.getAlias());
        iaSystemRecord.setMaxIrrNum(iasConfigVo.getMaxIrrNum());
        iaSystemRecord.setMode(iasConfigVo.getMode());
        iaSystemRecord.setType(iasConfigVo.getType());
        iaSystemRecord.setProvinceId(iasConfigVo.getProvinceId());
        iaSystemRecord.setCityId(iasConfigVo.getCityId());
        iaSystemRecord.setDistrictId(iasConfigVo.getDistrictId());
        iaSystemRecord.setTownId(iasConfigVo.getTownId());
        iaSystemRecord.setVillageId(iasConfigVo.getVillageId());
        iaSystemRecord.setAddress(iasConfigVo.getAddress());
        ErrorCode errorCode = iaSystemService.updateIaSystem(iaSystemRecord);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        this.iaSystemRecord = iaSystemRecord;
        return ErrorCode.OK;
    }

    /**
     * 向智慧农业系统中添加灌溉区域
     *
     * @param irrigationAreaRecord 灌溉区域配置信息。
     * @return 成功返回OK，失败返回相应的错误码：
     */
    public synchronized ErrorCode InnerAddIrrigationArea(IrrigationAreaRecord irrigationAreaRecord) {
        Farmland farmland = getFarmland(irrigationAreaRecord.getFmId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }

        return farmland.addIrrigationArea(irrigationAreaRecord);
    }

    public synchronized ErrorCode innerDelIrrigationArea(IrriAreaConfigVo irriAreaConfigVo) {
        Farmland farmland = getFarmland(irriAreaConfigVo.getFmId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }
        return farmland.delIrrigationArea(irriAreaConfigVo);
    }

    public synchronized ErrorCode innerUpdateIrrigation(IrriAreaConfigVo irriAreaConfigVo) {
        //更新数据库
        IrrigationAreaRecord irrigationAreaRecord = new IrrigationAreaRecord();
        irrigationAreaRecord.setId(irriAreaConfigVo.getId());
        irrigationAreaRecord.setName(irriAreaConfigVo.getName());
        irrigationAreaRecord.setSortOrder(irriAreaConfigVo.getSortOrder());
        ErrorCode errorCode = iaSystemService.updateIrrigationArea(irrigationAreaRecord);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //更新系统中的灌溉区
        Farmland farmland = getFarmland(irriAreaConfigVo.getFmId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }
        return farmland.updateIrrigation(irriAreaConfigVo);
    }

    /**
     * 向智慧农业系统中添加水肥一体化系统
     *
     * @param irriFerRecord 水肥一体化系统在数据库中的记录
     * @return 成功返回OK，失败返回相应的错误码：
     */
    public synchronized ErrorCode addIrriFerSystem(IrriFerRecord irriFerRecord) {
        if (irriAndFerSystem != null) {
            return ErrorCode.IRRI_FER_SYSTEM_EXIST;
        }
        irriAndFerSystem = new IrriAndFerSystem(irriFerRecord);
        return ErrorCode.OK;
    }

    public synchronized ErrorCode innerDelIrriFer(int id) {
        if (irriAndFerSystem != null && irriAndFerSystem.getId() == id) {
            this.irriAndFerSystem = null;
        }
        return ErrorCode.OK;
    }

    public synchronized ErrorCode innerUpdateIrriFer(IrriFerSystemConfigVo irriFerSystemConfigVo) {
        IrriFerRecord irriFerRecord = new IrriFerRecord();
        irriFerRecord.setId(irriFerSystemConfigVo.getId());
        irriFerRecord.setName(irriFerSystemConfigVo.getName());
        ErrorCode errorCode = iaSystemService.updateIrriFer(irriFerRecord);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        if (irriAndFerSystem != null) {
            return irriAndFerSystem.updateIrriFer(irriFerRecord);
        }
        return ErrorCode.IRRI_FER_SYSTEM_NOT_EXIST;
    }

    /**
     * 向智慧农业系统中添加设备
     *
     * @param iasDeviceRecord 要添加的设备
     * @return 成功返回OK，失败返回相应的错误码
     */
    public synchronized ErrorCode addIasDevice(IasDeviceRecord iasDeviceRecord) {
        ErrorCode errorCode;
        if (IasDeviceUsageType.isOpDevice(iasDeviceRecord.getUsageType())) {
            errorCode = addOpDevice(iasDeviceRecord);
        } else if (IasDeviceUsageType.isSensor(iasDeviceRecord.getUsageType())) {
            errorCode = addSensorDevice(iasDeviceRecord);
        } else {
            errorCode = ErrorCode.DEVICE_USAGE_TYPE_INVALID;
        }
        return errorCode;
    }

    /**
     * 删除智慧农业中的设备
     *
     * @param iasDeviceRecord 要删除的设备
     * @return 成功返回OK，失败返回相应的错误码
     */
    public synchronized ErrorCode delIasDevice(IasDeviceRecord iasDeviceRecord) {
        ErrorCode errorCode;
        if (IasDeviceUsageType.isOpDevice(iasDeviceRecord.getUsageType())) {
            errorCode = delOpDevice(iasDeviceRecord);
        } else if (IasDeviceUsageType.isSensor(iasDeviceRecord.getUsageType())) {
            errorCode = delSensorDevice(iasDeviceRecord);
        } else {
            errorCode = ErrorCode.DEVICE_USAGE_TYPE_INVALID;
        }
        return errorCode;
    }

    /**
     * 向智慧农业系统中添加农田
     *
     * @param farmlandRecord 农田配置信息。
     * @return 成功返回ErrorCode.OK，失败返回相应的错误码：
     */
    ErrorCode innerAddFarmland(FarmlandRecord farmlandRecord) {
        for (Farmland farmland : farmlands) {
            if (farmland.getId() == farmlandRecord.getId()) {
                return ErrorCode.FARMLAND_EXIST;
            }
        }
        Farmland farmland = new Farmland(farmlandRecord);
        farmlands.add(farmland);
        return ErrorCode.OK;
    }

    public synchronized ErrorCode innerDelFarmland(int id) {
        for (Farmland farmland : farmlands) {
            if (farmland.getId() == id) {
                farmlands.remove(farmland);
                return ErrorCode.OK;
            }
        }
        return ErrorCode.FARMLAND_NOT_EXIST;
    }

    public synchronized ErrorCode innerUpdateFarmland(FarmlandConfigVo farmlandConfigVo) {
        FarmlandRecord farmlandRecord = new FarmlandRecord();
        farmlandRecord.setId(farmlandConfigVo.getId());
        farmlandRecord.setIasystemId(farmlandConfigVo.getIaSystemId());
        farmlandRecord.setName(farmlandConfigVo.getName());
        farmlandRecord.setSortOrder(farmlandConfigVo.getSortOrder());
        ErrorCode errorCode = iaSystemService.updateFarmland(farmlandRecord);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }

        for (Farmland farmland : farmlands) {
            if (farmland.getId() == farmlandConfigVo.getId()) {
                return farmland.updateFarmland(farmlandRecord);
            }
        }
        return ErrorCode.FARMLAND_NOT_EXIST;
    }

    public IrriAndFerSystem getIrriAndFerSystem() {
        return irriAndFerSystem;
    }

    /**
     * 根据农田ID查找农田
     *
     * @param farmlandId 农田ID
     * @return 未找到时返回null，找到时返回农田
     */
    private Farmland getFarmland(int farmlandId) {
        for (Farmland farmland : farmlands) {
            if (farmland.getId() == farmlandId) {
                return farmland;
            }
        }
        return null;
    }

    private IrriAndFerSystem getIrriAndFerSystem(int IrriFerId) {
        if (irriAndFerSystem.getId() == IrriFerId) {
            return irriAndFerSystem;
        }
        return null;
    }

    public int getMaxIrriNum() {
        return iaSystemRecord.getMaxIrrNum();
    }

    public int getWorkMode() {
        return iaSystemRecord.getMode();
    }

    public synchronized ErrorCode handleEvent(IasEvent event) {
        if (event instanceof DeviceStateChangeEvent) {
            deviceStateChangeHandle((DeviceStateChangeEvent) event);
        } else {
            irrigationExecutor.handleEvent((IrrigationEvent) event);
        }
        return ErrorCode.OK;
    }

    private void deviceStateChangeHandle(DeviceStateChangeEvent event) {
        IasObject iasObject = getDeviceById(event.getId());
        if (iasObject == null) {
            return;
        }
        if (iasObject instanceof IasSensor) {
            ((IasSensor) iasObject).checkThresholdWarning(event);
        }
        iasObject.offlineCheck(event.getOldState(), event.getNewState());
    }

    private ErrorCode addOpDevice(IasDeviceRecord iasDeviceRecord) {
        ErrorCode errorCode;
        IasOpDevice iasOpDevice;
        if (DeviceAddr.isSensor(iasDeviceRecord.getDevId())) {
            return ErrorCode.NOT_OP_DEVICE;
        } else {
            iasOpDevice = new IasOpDevice(iasDeviceRecord);
        }
        switch (iasDeviceRecord.getUsageType()) {
            case IasDeviceUsageType.IRRI_FER_PUMP: {
                errorCode = addPumpForIrriFerSystem(iasOpDevice);
                break;
            }
            case IasDeviceUsageType.IRRI_VALVE: {
                errorCode = addValveForIrriArea(iasOpDevice);
                break;
            }
            case IasDeviceUsageType.IAS_NORMAL_DEVICE: {
                errorCode = addDevForIaSystem(iasOpDevice);
                break;
            }
            case IasDeviceUsageType.FM_NORMAL_DEVICE: {
                errorCode = addDevForFarmland(iasOpDevice);
                break;
            }
            case IasDeviceUsageType.AREA_NORMAL_DEVICE: {
                errorCode = addDevForIrriArea(iasOpDevice);
                break;
            }
            default: {
                //不会运行到这里
                errorCode = ErrorCode.OK;
            }
        }
        return errorCode;
    }

    private ErrorCode addSensorDevice(IasDeviceRecord iasDeviceRecord) {
        ErrorCode errorCode;
        IasSensor iasSensor;
        if (!DeviceAddr.isSensor(iasDeviceRecord.getDevId())) {
            return ErrorCode.NOT_SENSOR;
        } else {
            iasSensor = new IasSensor(iasDeviceRecord);
        }
        switch (iasDeviceRecord.getUsageType()) {
            case IasDeviceUsageType.IAS_SENSOR: {
                errorCode = addSensorForIaSystem(iasSensor);
                break;
            }
            case IasDeviceUsageType.FM_SENSOR: {
                errorCode = addSensorForFarmland(iasSensor);
                break;
            }
            case IasDeviceUsageType.AREA_SENSOR: {
                errorCode = addSensorForIrriArea(iasSensor);
                break;
            }
            case IasDeviceUsageType.OP_DEV_SENSOR: {
                errorCode = addSensorForOpDev(iasSensor);
                break;
            }
            default: {
                //不会运行到这里
                errorCode = ErrorCode.OK;
            }
        }
        return errorCode;
    }

    private ErrorCode delOpDevice(IasDeviceRecord iasDeviceRecord) {
        ErrorCode errorCode;
        if (DeviceAddr.isSensor(iasDeviceRecord.getDevId())) {
            return ErrorCode.NOT_OP_DEVICE;
        }
        switch (iasDeviceRecord.getUsageType()) {
            case IasDeviceUsageType.IRRI_FER_PUMP: {
                errorCode = delPumpForIrriFerSystem(iasDeviceRecord);
                break;
            }
            case IasDeviceUsageType.IRRI_VALVE: {
                errorCode = delValveForIrriArea(iasDeviceRecord);
                break;
            }
            case IasDeviceUsageType.IAS_NORMAL_DEVICE: {
                errorCode = delDevForIaSystem(iasDeviceRecord);
                break;
            }
            case IasDeviceUsageType.FM_NORMAL_DEVICE: {
                errorCode = delDevForFarmland(iasDeviceRecord);
                break;
            }
            case IasDeviceUsageType.AREA_NORMAL_DEVICE: {
                errorCode = delDevForIrriArea(iasDeviceRecord);
                break;
            }
            default: {
                //不会运行到这里
                errorCode = ErrorCode.OK;
            }
        }
        return errorCode;
    }

    private ErrorCode delSensorDevice(IasDeviceRecord iasDeviceRecord) {
        ErrorCode errorCode;
        if (!DeviceAddr.isSensor(iasDeviceRecord.getDevId())) {
            return ErrorCode.NOT_SENSOR;
        }
        switch (iasDeviceRecord.getUsageType()) {
            case IasDeviceUsageType.IAS_SENSOR: {
                errorCode = delSensorForIaSystem(iasDeviceRecord);
                break;
            }
            case IasDeviceUsageType.FM_SENSOR: {
                errorCode = delSensorForFarmland(iasDeviceRecord);
                break;
            }
            case IasDeviceUsageType.AREA_SENSOR: {
                errorCode = delSensorForIrriArea(iasDeviceRecord);
                break;
            }
            case IasDeviceUsageType.OP_DEV_SENSOR: {
                errorCode = delSensorForOpDev(iasDeviceRecord);
                break;
            }
            default: {
                //不会运行到这里
                errorCode = ErrorCode.OK;
            }
        }
        return errorCode;
    }

    private LinkedList<FarmlandVo> getFarmlandVos() {
        LinkedList<FarmlandVo> farmlandVos = new LinkedList<>();
        if (farmlands.size() != 0) {
            for (Farmland farmland : farmlands) {
                farmlandVos.add(farmland.getFarmlandVo());
            }
            return farmlandVos;
        }
        return null;
    }

    private LinkedList<FarmlandBasicVo> getFarmlandBasicVos() {
        LinkedList<FarmlandBasicVo> farmlandVos = new LinkedList<>();
        if (farmlands.size() != 0) {
            for (Farmland farmland : farmlands) {
                farmlandVos.add(farmland.getFarmlandBasicVo());
            }
            return farmlandVos;
        }
        return null;
    }

    private LinkedList<FarmlandVo> getFarmlandAreaVos() {
        LinkedList<FarmlandVo> farmlandVos = new LinkedList<>();
        if (farmlands.size() != 0) {
            for (Farmland farmland : farmlands) {
                farmlandVos.add(farmland.getFarmlandAreaVo());
            }
            return farmlandVos;
        }
        return null;
    }

    private IrrigationArea getIrrigationArea(int farmlandId, int irrigationAreaId) {
        Farmland farmland = getFarmland(farmlandId);
        if (null == farmland) {
            return null;
        }
        return farmland.getIrrigationArea(irrigationAreaId);
    }

    private ErrorCode addPumpForIrriFerSystem(IasOpDevice iasOpDevice) {
        if (irriAndFerSystem == null) {
            return ErrorCode.IRRI_FER_SYSTEM_NOT_EXIST;
        }

        return irriAndFerSystem.addPump(iasOpDevice);
    }

    private ErrorCode delPumpForIrriFerSystem(IasDeviceRecord iasDeviceRecord) {
        if (irriAndFerSystem == null) {
            return ErrorCode.IRRI_FER_SYSTEM_NOT_EXIST;
        }

        return irriAndFerSystem.delPump(iasDeviceRecord);
    }

    private ErrorCode addValveForIrriArea(IasOpDevice iasOpDevice) {
        Farmland farmland = getFarmland(iasOpDevice.getFmId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }
        IrrigationArea irrigationArea = farmland.getIrrigationArea(iasOpDevice.getIrriAreaId());
        if (null == irrigationArea) {
            return ErrorCode.IRRIGATION_AREA_NOT_EXIST;
        }

        return irrigationArea.addValve(iasOpDevice);
    }

    private ErrorCode delValveForIrriArea(IasDeviceRecord iasDeviceRecord) {
        Farmland farmland = getFarmland(iasDeviceRecord.getFarmlandId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }
        IrrigationArea irrigationArea = farmland.getIrrigationArea(iasDeviceRecord.getIrriAreaId());
        if (null == irrigationArea) {
            return ErrorCode.IRRIGATION_AREA_NOT_EXIST;
        }
        return irrigationArea.delValve(iasDeviceRecord);
    }

    private ErrorCode addDevForIaSystem(IasOpDevice iasOpDevice) {
        return addNormalDevice(iasOpDevice);
    }

    private ErrorCode delDevForIaSystem(IasDeviceRecord iasDeviceRecord) {
        return delNormalDevice(iasDeviceRecord);
    }

    private ErrorCode addDevForFarmland(IasOpDevice iasOpDevice) {
        Farmland farmland = getFarmland(iasOpDevice.getFmId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }

        return farmland.addNormalDevice(iasOpDevice);
    }

    private ErrorCode delDevForFarmland(IasDeviceRecord iasDeviceRecord) {
        Farmland farmland = getFarmland(iasDeviceRecord.getFarmlandId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }
        return farmland.delNormalDevice(iasDeviceRecord);
    }

    private ErrorCode addDevForIrriArea(IasOpDevice iasOpDevice) {
        Farmland farmland = getFarmland(iasOpDevice.getFmId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }

        IrrigationArea irrigationArea = farmland.getIrrigationArea(iasOpDevice.getIrriAreaId());
        if (null == irrigationArea) {
            return ErrorCode.IRRIGATION_AREA_NOT_EXIST;
        }

        return irrigationArea.addNormalDevice(iasOpDevice);
    }

    private ErrorCode delDevForIrriArea(IasDeviceRecord iasDeviceRecord) {
        Farmland farmland = getFarmland(iasDeviceRecord.getFarmlandId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }

        IrrigationArea irrigationArea = farmland.getIrrigationArea(iasDeviceRecord.getIrriAreaId());
        if (null == irrigationArea) {
            return ErrorCode.IRRIGATION_AREA_NOT_EXIST;
        }

        return irrigationArea.delNormalDevice(iasDeviceRecord);
    }

    private ErrorCode addSensorForIaSystem(IasSensor iasSensor) {
        return addDcPoint(iasSensor);
    }

    private ErrorCode delSensorForIaSystem(IasDeviceRecord iasDeviceRecord) {
        return delDcPoint(iasDeviceRecord.getDevId());
    }

    private ErrorCode addSensorForFarmland(IasSensor iasSensor) {
        Farmland farmland = getFarmland(iasSensor.getFmId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }

        return farmland.addDcPoint(iasSensor);
    }

    private ErrorCode delSensorForFarmland(IasDeviceRecord iasDeviceRecord) {
        Farmland farmland = getFarmland(iasDeviceRecord.getFarmlandId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }
        return farmland.delDcPoint(iasDeviceRecord.getDevId());
    }

    private ErrorCode addSensorForIrriArea(IasSensor iasSensor) {
        Farmland farmland = getFarmland(iasSensor.getFmId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }
        IrrigationArea irrigationArea = farmland.getIrrigationArea(iasSensor.getIrriAreaId());
        if (null == irrigationArea) {
            return ErrorCode.IRRIGATION_AREA_NOT_EXIST;
        }

        return irrigationArea.addDcPoint(iasSensor);
    }

    private ErrorCode delSensorForIrriArea(IasDeviceRecord iasDeviceRecord) {
        Farmland farmland = getFarmland(iasDeviceRecord.getFarmlandId());
        if (null == farmland) {
            return ErrorCode.FARMLAND_NOT_EXIST;
        }
        IrrigationArea irrigationArea = farmland.getIrrigationArea(iasDeviceRecord.getIrriAreaId());
        if (null == irrigationArea) {
            return ErrorCode.IRRIGATION_AREA_NOT_EXIST;
        }
        return irrigationArea.delDcPoint(iasDeviceRecord.getDevId());
    }

    private ErrorCode addSensorForOpDev(IasSensor iasSensor) {
        IasOpDevice iasOpDevice = getOpDev(iasSensor);
        if (null == iasOpDevice) {
            return ErrorCode.IAS_DEVICE_NOT_EXIST;
        }

        return iasOpDevice.addDcPoint(iasSensor);
    }

    private ErrorCode delSensorForOpDev(IasDeviceRecord iasDeviceRecord) {
        IasSensor iasSensor = new IasSensor(iasDeviceRecord);
        IasOpDevice iasOpDevice = getOpDev(iasSensor);
        if (null == iasOpDevice) {
            return ErrorCode.IAS_DEVICE_NOT_EXIST;
        }

        return iasOpDevice.delDcPoint(iasDeviceRecord.getDevId());
    }

    //根据传感器获取其所在的可操作设备
    private IasOpDevice getOpDev(IasSensor iasSensor) {
        Integer usageType = iaSystemService.selectUsageTypeByDevId(iasSensor.getIasDevId());
        switch (usageType) {
            case IasDeviceUsageType.IRRI_FER_PUMP: {
                IrriAndFerSystem irriAndFerSystem = getIrriAndFerSystem(iasSensor.getIrriFerId());
                return irriAndFerSystem.pump;
            }
            case IasDeviceUsageType.IRRI_VALVE: {
                Farmland farmland = getFarmland(iasSensor.getFmId());
                IrrigationArea irrigationArea = farmland.getIrrigationArea(iasSensor.getIrriAreaId());
                List<IasOpDevice> valves = irrigationArea.valves;
                for (IasOpDevice valve : valves) {
                    if (valve.getDevId() == iasSensor.getIasDevId()) {
                        return valve;
                    }
                }
                break;
            }
            case IasDeviceUsageType.IAS_NORMAL_DEVICE: {
                List<IasOpDevice> normalDevices = this.normalDevice;
                for (IasOpDevice nomal : normalDevices) {
                    if (nomal.getDevId() == iasSensor.getIasDevId()) {
                        return nomal;
                    }
                }
                break;
            }
            case IasDeviceUsageType.FM_NORMAL_DEVICE: {
                Farmland farmland = getFarmland(iasSensor.getFmId());
                List<IasOpDevice> normalDevices = farmland.normalDevice;
                for (IasOpDevice nomal : normalDevices) {
                    if (nomal.getDevId() == iasSensor.getIasDevId()) {
                        return nomal;
                    }
                }
                break;
            }
            case IasDeviceUsageType.AREA_NORMAL_DEVICE: {
                Farmland farmland = getFarmland(iasSensor.getFmId());
                IrrigationArea irrigationArea = farmland.getIrrigationArea(iasSensor.getIrriAreaId());
                List<IasOpDevice> normalDevices = irrigationArea.normalDevice;
                for (IasOpDevice nomal : normalDevices) {
                    if (nomal.getDevId() == iasSensor.getIasDevId()) {
                        return nomal;
                    }
                }
                break;
            }
        }
        return null;
    }

    /**
     * 检查当前是否在执行灌溉任务
     *
     * @return 正在执行灌溉任务返回true，否则返回true
     */
    private boolean isIrrigating() {
        return irrigationExecutor.isIrrigating();
    }

    public synchronized ErrorCode addCamera(CameraConfigRequestDTO cameraConfigDTO) {
        CameraConfig cameraConfig = AppContext.getBean(CameraConfig.class);
        String accessToken = cameraConfig.getAccessToken();
        if (accessToken == null) {
            return ErrorCode.REGISTER_CAMERA_TO_YS_FAIL;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request;
        String deviceSerial = cameraConfigDTO.getSn();
        String validateCode = cameraConfigDTO.getCode();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        CommonOpOnYsResultDTO commonOpOnYsResultDTO;
        OpenVideoOnYsResultDTO openVideoOnYsResultDTO;
        GetVideoAddressFromYsResultDTO getVideoAddressFromYsResultDTO;
        GetVideoAddressFromYsSingleResult videoAddress;
        GetWsAddressFromYsResultDTO getWsAddressFromYsResultDTO;
        GetWsAddressFromYsSingleResult wsAddress;
        GetCapabilityFromYsDTO getCapabilityFromYsDTO;

        //注册设备到萤石云平台
        try {
            //向萤石云平台添加设备
            String addCameraUrl = cameraConfig.getAddDevUrl();
            map.add("accessToken", accessToken);
            map.add("deviceSerial", deviceSerial);
            map.add("validateCode", validateCode);
            request = new HttpEntity<>(map, headers);
            commonOpOnYsResultDTO = restTemplate.postForObject(addCameraUrl, request, CommonOpOnYsResultDTO.class);
            if (!(commonOpOnYsResultDTO.getCode().equals(CameraConstant.CODE_OK)
                    || commonOpOnYsResultDTO.getCode().equals(CameraConstant.CAMERA_ALREADY_ADDED))) {
                return ErrorCode.REGISTER_CAMERA_TO_YS_FAIL;
            }

            //开通直播功能
            String openVideoUrl = cameraConfig.getOpenVideoUrl();
            map.clear();
            map.add("accessToken", accessToken);
            map.add("source", deviceSerial + ":1");
            request = new HttpEntity<>(map, headers);
            openVideoOnYsResultDTO = restTemplate.postForObject(openVideoUrl, request, OpenVideoOnYsResultDTO.class);
            if (!(openVideoOnYsResultDTO.getCode().equals(CameraConstant.CODE_OK)
                    && (openVideoOnYsResultDTO.getData().get(0).getRet().equals(CameraConstant.CODE_OK)
                    || openVideoOnYsResultDTO.getData().get(0).getRet().equals(CameraConstant.VIDEO_ALREADY_OPENED)))) {
                return ErrorCode.REGISTER_CAMERA_TO_YS_FAIL;
            }

            //获取直播地址
            String getVideoAddrUrl = cameraConfig.getQueryVideoAddrUrl();
            map.clear();
            map.add("accessToken", accessToken);
            map.add("source", deviceSerial + ":1");
            request = new HttpEntity<>(map, headers);
            getVideoAddressFromYsResultDTO = restTemplate.postForObject(getVideoAddrUrl, request, GetVideoAddressFromYsResultDTO.class);
            if (!(getVideoAddressFromYsResultDTO.getCode().equals(CameraConstant.CODE_OK)
                    && getVideoAddressFromYsResultDTO.getData().get(0).getRet().equals(CameraConstant.CODE_OK))) {
                return ErrorCode.REGISTER_CAMERA_TO_YS_FAIL;
            }
            videoAddress = getVideoAddressFromYsResultDTO.getData().get(0);

            //开通WS直播功能
            String getOpenWsUrl = cameraConfig.getOpenWsUrl();
            map.clear();
            map.add("accessToken", accessToken);
            map.add("source", deviceSerial + ":1");
            request = new HttpEntity<>(map, headers);
            //开通WS直播功能和开通普通视频功能的返回结果格式是一致的
            openVideoOnYsResultDTO = restTemplate.postForObject(getOpenWsUrl, request, OpenVideoOnYsResultDTO.class);
            if (!(openVideoOnYsResultDTO.getCode().equals(CameraConstant.CODE_OK)
                    && (openVideoOnYsResultDTO.getData().get(0).getRet().equals(CameraConstant.CODE_OK)
                    || openVideoOnYsResultDTO.getData().get(0).getRet().equals(CameraConstant.WS_ALREADY_OPENED)))) {
                wsAddress = null;
            } else {
                //获取WS直播地址
                String getWsUrl = cameraConfig.getQueryWsUrl();
                map.clear();
                map.add("accessToken", accessToken);
                map.add("source", deviceSerial + ":1");
                request = new HttpEntity<>(map, headers);
                getWsAddressFromYsResultDTO = restTemplate.postForObject(getWsUrl, request, GetWsAddressFromYsResultDTO.class);
                if (!(getVideoAddressFromYsResultDTO.getCode().equals(CameraConstant.CODE_OK)
                        && getVideoAddressFromYsResultDTO.getData().get(0).getRet().equals(CameraConstant.CODE_OK))) {
                    wsAddress = null;
                } else {
                    wsAddress = getWsAddressFromYsResultDTO.getData().get(0);
                }
            }

            //获取能力集合
            String getCapabilityUrl = cameraConfig.getQueryDevCapabilityUrl();
            map.clear();
            map.add("accessToken", accessToken);
            map.add("deviceSerial", deviceSerial);
            request = new HttpEntity<>(map, headers);
            getCapabilityFromYsDTO = restTemplate.postForObject(getCapabilityUrl, request, GetCapabilityFromYsDTO.class);
            if (!getCapabilityFromYsDTO.getCode().equals(CameraConstant.CODE_OK)) {
                return ErrorCode.REGISTER_CAMERA_TO_YS_FAIL;
            }
        } catch (Exception e) {
            return ErrorCode.REGISTER_CAMERA_TO_YS_FAIL;
        }

        //添加设备到数据库
        try {
            CameraDO cameraDO = new CameraDO();
            cameraDO.setName(cameraConfigDTO.getName());
            cameraDO.setSn(cameraConfigDTO.getSn());
            cameraDO.setCode(cameraConfigDTO.getCode());
            cameraDO.setUrlHls(videoAddress.getHls());
            cameraDO.setUrlHlsHd(videoAddress.getHlsHd());
            cameraDO.setRtmp(videoAddress.getRtmp());
            cameraDO.setRtmpHd(videoAddress.getRtmpHd());
            cameraDO.setIasId(cameraConfigDTO.getIaSystemId());
            cameraDO.setFarmlandId(cameraConfigDTO.getFarmlandId());
            cameraDO.setAreaId(cameraConfigDTO.getAreaId());
            cameraDO.setLocation(cameraConfigDTO.getLocation());
            if (wsAddress != null) {
                cameraDO.setWsAddr(wsAddress.getWsAddress());
            }
            cameraDO.setCapability(JSONUtils.toJSONString(getCapabilityFromYsDTO.getData()));
            AppContext.getBean(CameraDOExtMapper.class).insert(cameraDO);

            return innerAddCamera(cameraDO);
        } catch (Exception e) {
            return ErrorCode.ADD_CAMERA_FAIL;
        }
    }

    public ErrorCode innerAddCamera(CameraDO cameraDO) {
        Camera camera = new Camera(cameraDO);
        cameras.add(camera);
        return ErrorCode.OK;
    }

    public ErrorCode removeCamera(int cameraId) {
        Camera camera = null;
        for (Camera c : cameras) {
            if (c.getId() == cameraId) {
                camera = c;
                break;
            }
        }
        if (camera == null) {
            return ErrorCode.CAMERA_NOT_EXIST;
        }

        //从萤石平台删除设备
        CameraConfig cameraConfig = AppContext.getBean(CameraConfig.class);
        String accessToken = cameraConfig.getAccessToken();
        if (accessToken == null) {
            return ErrorCode.REGISTER_CAMERA_TO_YS_FAIL;
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request;
        String deviceSerial = camera.getSn();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        CommonOpOnYsResultDTO commonOpOnYsResultDTO;
        try {
            String rmCameraUrl = cameraConfig.getRmDevUrl();
            map.add("accessToken", accessToken);
            map.add("deviceSerial", deviceSerial);
            request = new HttpEntity<>(map, headers);
            commonOpOnYsResultDTO = restTemplate.postForObject(rmCameraUrl, request, CommonOpOnYsResultDTO.class);
            if (!(commonOpOnYsResultDTO.getCode().equals(CameraConstant.CODE_OK)
                    || commonOpOnYsResultDTO.getCode().equals(CameraConstant.CAMERA_NOT_EXIST))) {
                return ErrorCode.UNREGISTER_CAMERA_FROM_YS_FAIL;
            }
        } catch (Exception e) {
            return ErrorCode.UNREGISTER_CAMERA_FROM_YS_FAIL;
        }

        //从数据库中删除设备
        try {
            AppContext.getBean(CameraDOExtMapper.class).deleteByPrimaryKey(camera.getId());
            cameras.remove(camera);
        } catch (Exception e) {
            return ErrorCode.REMOVE_CAMERA_FAIL;
        }

        return ErrorCode.OK;
    }

    public ErrorCode getAllCameras(LinkedList<CameraDTO> cameraDTOS) {
        if (cameras.size() == 0) {
            return ErrorCode.IASYSTEM_HAS_NOT_CAMERA;
        }

        for (Camera camera : cameras) {
            cameraDTOS.add(camera.getCameraDTO(IaSystemConstant.QUERY_TYPE_SUMMARY));
        }
        return ErrorCode.OK;
    }

    private IasObject findIasObjectByAddr(IasObjectAddr addr) {
        IasObject object;

        //检查是否为自身
        if (this.getAddr().equals(addr)) {
            return this;
        }

        //检查是否为自身的可操作设备或者传感器
        object = findByAddr(addr);
        if (object != null) {
            return object;
        }

        //在农田中查找
        for (Farmland farmland : farmlands) {
            object = farmland.findIasObjectByAddr(addr);
            if (object != null) {
                return object;
            }
        }

        //在水肥一体化系统中查找
        object = irriAndFerSystem.findIasObjectByAddr(addr);
        if (object != null) {
            return object;
        }

        return null;
    }

    //TODO 接口梳理

    /**
     * 向智慧农业系统中添加农田
     *
     * @param farmlandConfigVo 农田配置信息。当添加成功时，会通过该对象返回新添加农田的ID
     * @return 成功返回ErrorCode.OK，失败返回相应的错误码：
     */
    public synchronized ErrorCode addFarmland(FarmlandConfigVo farmlandConfigVo) {
        FarmlandRecord farmlandRecord = new FarmlandRecord();
        farmlandRecord.setName(farmlandConfigVo.getName());
        farmlandRecord.setIasystemId(farmlandConfigVo.getIaSystemId());
        farmlandRecord.setSortOrder(farmlandConfigVo.getSortOrder());
        try {
            FarmlandRecordExtMapper farmlandRecordExtMapper = AppContext.getBean(FarmlandRecordExtMapper.class);
            farmlandRecordExtMapper.insert(farmlandRecord);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return innerAddFarmland(farmlandRecord);
    }

    /**
     * 获取智慧农业系统信息
     *
     * @param type 查询类型。查询类型分类定义在IaSystemConstant中
     * @return 智慧农业系统信息
     */
    public synchronized IaSystemDTO getIaSystemInfo(Integer type) {
        IaSystemDTO iaSystemDTO = new IaSystemDTO(iaSystemRecord);
        if (type == IaSystemConstant.QUERY_TYPE_SUMMARY || type == IaSystemConstant.QUERY_TYPE_ALL) {
            //环境信息
            iaSystemDTO.setDcPoints(getDcPointVos());
            //可操作设备信息
            iaSystemDTO.setDevices(getNormalDeviceVos());
            //水肥一体化系统信息
            iaSystemDTO.setIrriAndFerSystem(getIrriAndFerSystemVo());
        }
        //农田信息
        iaSystemDTO.setFarmlands(listFarmland(type));
        //摄像头信息
        iaSystemDTO.setCameras(listCamera(type));
        //灌溉任务信息
        LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos = irrigationExecutor.getTaskStateVo();
        for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos) {
            for (FarmlandDTO farmlandDTO : iaSystemDTO.getFarmlands()) {
                for (IrriAreaDTO irriAreaDTO : farmlandDTO.getIrriAreas()) {
                    if (irrigationTaskStateVo.getFarmlandId().equals(farmlandDTO.getFarmlandId())
                            && irrigationTaskStateVo.getIrriAreaId().equals(irriAreaDTO.getIrriAreaId())) {
                        if (type == IaSystemConstant.QUERY_TYPE_IRRI_TASK) {
                            irrigationTaskStateVo.setCreateUser(null);
                            irrigationTaskStateVo.setDeleteUser(null);
                        }
                        irrigationTaskStateVo.stateTranslateForApi();
                        irriAreaDTO.setIrriTask(irrigationTaskStateVo);
                    }
                }
            }
        }
        return iaSystemDTO;
    }

    /**
     * 获取农田详情
     *
     * @param farmlandId 农田ID
     * @return 农田详情
     */
    public synchronized FarmlandDTO getFarmlandDetail(Integer farmlandId) {
        FarmlandDTO farmlandDTO = null;
        for (Farmland farmland : farmlands) {
            if (farmland.getId() == farmlandId) {
                farmlandDTO = farmland.getFarmlandDetail(IaSystemConstant.QUERY_TYPE_ALL);
                break;
            }
        }
        if (farmlandDTO == null) {
            return null;
        }

        //填充灌溉任务信息
        LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos = irrigationExecutor.getTaskStateVo();
        for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos) {
            for (IrriAreaDTO irriAreaDTO : farmlandDTO.getIrriAreas()) {
                if (irrigationTaskStateVo.getFarmlandId().equals(farmlandDTO.getFarmlandId())
                        && irrigationTaskStateVo.getIrriAreaId().equals(irriAreaDTO.getIrriAreaId())) {
                    irrigationTaskStateVo.setCreateUser(null);
                    irrigationTaskStateVo.setDeleteUser(null);
                    irriAreaDTO.setIrriTask(irrigationTaskStateVo);
                }
            }
        }

        farmlandDTO.setMode(getWorkMode());

        return farmlandDTO;
    }

    /**
     * 设置智慧农业系统的工作模式。当设置为手动模式时，将停止所有灌溉任务。
     *
     * @param workMode 工作模式
     * @return 设置工作模式的结果
     */
    public synchronized ErrorCode setWorkMode(int workMode) {
        if (irrigationExecutor.isIrrigating()) {
            return ErrorCode.IRRIGATION_IN_PROGRESS;
        }

        iaSystemRecord.setMode(workMode);
        try {
            iaSystemService.updateIaSystemRecord(iaSystemRecord);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }

        return ErrorCode.OK;
    }

    /**
     * 执行设备操作命令
     *
     * @param request 设备操作请求
     */
    public synchronized void operateDevice(ReqOperateDevice request) {
        IasOpDevice iasOpDevice = getOpDeviceByIasDevId(request.getIasDevId());
        if (iasOpDevice == null) {
            request.setResult(ErrorCode.DEVICE_NOT_EXIST);
            return;
        }

        //自动模式下不允许对水泵和阀门进行手动操作
        if (request.getSource() != DeviceConstant.OP_DEV_SRC_IRRI_TASK) {
            if (iasOpDevice.getAddr().getType() == IasObjectType.IRRI_FER_PUMP
                    || iasOpDevice.getAddr().getType() == IasObjectType.IRRI_VALVE) {
                if (getWorkMode() == IaSystemConstant.WORK_MODE_AUTO) {
                    request.setResult(ErrorCode.IASYSTEM_IN_AUTO_MODE);
                    return;
                }
            }
        }

        //操作设备
        iasOpDevice.operateDevice(request);
    }

    /**
     * 获取可操作设备信息
     *
     * @param iasDevId 设备ID
     * @return 设备信息
     */
    public DeviceDTO getOpDeviceInfo(Integer iasDevId) {
        IasOpDevice iasOpDevice = getOpDeviceByIasDevId(iasDevId);
        if (iasOpDevice == null) {
            return null;
        }
        return iasOpDevice.getNormalDeviceVo();
    }

    /**
     * 获取传感器历史采集值
     *
     * @param reqSensorHistoryDataDTO 获取历史采集值请求
     * @return 历史采集值
     */
    public synchronized SensorHistoryDataDTO getSensorHistoryData(ReqSensorHistoryDataDTO reqSensorHistoryDataDTO) {
        IasSensor iasSensor = getSensorByIasDevId(reqSensorHistoryDataDTO.getIasDevId());
        if (iasSensor == null) {
            return null;
        }
        return iasSensor.getSensorHistoryData(reqSensorHistoryDataDTO);
    }

    /**
     * 配置传感器阈值告警
     *
     * @param config 传感器阈值告警的配置
     * @return 配置结果
     */
    public synchronized ErrorCode configSensorThresholdWarning(ReqConfigSensorThresholdWarningDTO config) {
        IasSensor iasSensor = getSensorByIasDevId(config.getDevId());
        if (iasSensor == null) {
            return ErrorCode.DEVICE_NOT_EXIST;
        }

        return iasSensor.configSensorThresholdWarning(config);
    }

    /**
     * 获取传感器阈值告警配置
     *
     * @param request 获取请求
     * @return 告警配置
     */
    public synchronized ThresholdWarningCfgExtDTO getSensorThresholdWarningConfig(ReqGetSensorThresholdWarningCfg request) {
        IasSensor iasSensor = getSensorByIasDevId(request.getDevId());
        if (iasSensor == null) {
            return null;
        }

        return iasSensor.getSensorThresholdWarningConfig(request);
    }

    /**
     * @return 水肥一体化系统信息
     */
    private IrriAndFerSystemDTO getIrriAndFerSystemVo() {
        if (irriAndFerSystem != null) {
            return irriAndFerSystem.getIrriAndFerSystemVo();
        }
        return null;
    }

    /**
     * 获取农田信息
     *
     * @param type 查询类型
     * @return 农田信息
     */
    private LinkedList<FarmlandDTO> listFarmland(Integer type) {
        LinkedList<FarmlandDTO> farmlandDTOS = new LinkedList<>();
        if (farmlands.size() != 0) {
            for (Farmland farmland : farmlands) {
                farmlandDTOS.add(farmland.getFarmlandDetail(type));
            }
            return farmlandDTOS;
        }
        return null;
    }

    /**
     * 更新灌溉任务
     *
     * @param requestVo 灌溉任务更新请求
     */
    public synchronized void updateIrrigationTasks(ReqUpdateNormalIrrigationDTO requestVo) {
        irrigationRequestCheckAndSetValves(requestVo);
        irrigationExecutor.updateIrrigationTasks(requestVo);
    }

    /**
     * @return 智慧农业系统当前的灌溉任务
     */
    public LinkedList<IrrigationTaskStateVo> listTaskStateVo() {
        LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos = irrigationExecutor.getTaskStateVo();
        //设置农田和灌溉区的名字
        for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos) {
            for (Farmland farmland : farmlands) {
                if (farmland.getId() == irrigationTaskStateVo.getFarmlandId()) {
                    irrigationTaskStateVo.setFarmlandName(farmland.getName());
                    IrrigationArea area = farmland.getIrrigationArea(irrigationTaskStateVo.getIrriAreaId());
                    irrigationTaskStateVo.setIrriAreaName(area.getName());
                }
            }
        }
        return irrigationTaskStateVos;
    }

    /**
     * 根据设备的物理地址查找设备
     *
     * @param id 设备的物理地址
     * @return 找到的设备，未找到返回null
     */
    private IasObject getDeviceById(long id) {
        IasObject iasObject;

        //查找直接挂接在IaSystem中的设备
        iasObject = getDevById(id);
        if (iasObject != null) {
            return iasObject;
        }

        //在农田中查找设备
        for (Farmland farmland : farmlands) {
            iasObject = farmland.getDeviceById(id);
            if (iasObject != null) {
                return iasObject;
            }
        }

        //在水肥一体化系统中查找设备
        iasObject = irriAndFerSystem.getDeviceById(id);
        if (iasObject != null) {
            return iasObject;
        }

        return null;
    }

    /**
     * 根据传感器物理地址查找设备
     *
     * @param id 设备的物理地址
     * @return 传感器设备
     */
    private IasSensor getSensorById(long id) {
        if (!DeviceAddr.isSensor(id)) {
            return null;
        }
        return (IasSensor) getDeviceById(id);
    }

    /**
     * 根据可操作设备物理地址查找设备
     *
     * @param id 可操作设备的物理地址
     * @return 可操作设备
     */
    private IasOpDevice getOpDeviceById(long id) {
        if (DeviceAddr.isSensor(id)) {
            return null;
        }
        return (IasOpDevice) getDeviceById(id);
    }

    /**
     * 根据传感器设备的逻辑地址查找设备
     *
     * @param id 传感器的逻辑地址
     * @return 传感器
     */
    private IasSensor getSensorByIasDevId(Integer id) {
        IasDeviceRecord iasDeviceRecord = AppContext.getBean(IasDeviceRecordExtMapper.class)
                .selectByPrimaryKey(id);
        if (iasDeviceRecord == null) {
            return null;
        }
        if (!DeviceAddr.isSensor(iasDeviceRecord.getDevId())) {
            return null;
        }
        return (IasSensor) getDeviceById(iasDeviceRecord.getDevId());
    }

    /**
     * 根据可操作设备的逻辑地址查找设备
     *
     * @param id 可操作设备的逻辑地址
     * @return 可操作设备
     */
    private IasOpDevice getOpDeviceByIasDevId(Integer id) {
        IasDeviceRecord iasDeviceRecord = AppContext.getBean(IasDeviceRecordExtMapper.class)
                .selectByPrimaryKey(id);
        if (iasDeviceRecord == null) {
            return null;
        }
        if (DeviceAddr.isSensor(iasDeviceRecord.getDevId())) {
            return null;
        }
        return (IasOpDevice) getDeviceById(iasDeviceRecord.getDevId());
    }

    /**
     * 对灌溉任务更新请求进行检查，并获取灌溉任务执行需要的阀门信息
     *
     * @param requestVo 灌溉任务更新请求
     */
    private void irrigationRequestCheckAndSetValves(ReqUpdateNormalIrrigationDTO requestVo) {
        NormalIrrigationTaskVo[] tasks = requestVo.getTasks();
        for (NormalIrrigationTaskVo task : tasks) {
            Farmland farmland = getFarmland(task.getFarmlandId());
            AssertEx.isTrue(farmland != null, ErrorCode.FARMLAND_NOT_EXIST);

            IrrigationArea irrigationArea = farmland.getIrrigationArea(task.getAreaId());
            AssertEx.isTrue(irrigationArea != null, ErrorCode.IRRIGATION_TASK_NOT_EXIST);
            task.setValves(irrigationArea.getValveIds());
        }
        irrigationExecutor.irrigationRequestCheck(requestVo);
    }

    /**
     * 获取摄像头信息
     *
     * @param type 查询类型
     * @return 摄像头信息
     */
    private LinkedList<CameraDTO> listCamera(Integer type) {
        if (cameras.size() == 0) {
            return null;
        }
        LinkedList<CameraDTO> cameraDTOS = new LinkedList<>();
        for (Camera camera : cameras) {
            cameraDTOS.add(camera.getCameraDTO(type));
        }
        return cameraDTOS;
    }
}
