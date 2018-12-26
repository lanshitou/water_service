package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.*;
import com.zzwl.ias.iasystem.device.DeviceManager;
import com.zzwl.ias.mapper.CameraDOExtMapper;
import com.zzwl.ias.mapper.DataCollectionRecordMapper;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.timer.IaTimer;
import com.zzwl.ias.vo.iasystem.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:智慧农业系统管理器
 * User: HuXin
 * Date: 2018-04-26
 * Time: 10:36
 */

public class IaSystemManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private IaSystemService iaSystemService;
    private DeviceManager deviceManager;

    private ConcurrentHashMap<Integer, IaSystem> iaSystems;

    private IaTimer dataCollectionTimer;
    private IaTimer systemStatusMonitoringTimer;

    @Autowired
    private DataCollectionRecordMapper dataCollectionRecordMapper;

    public IaSystemManager() {

    }

    public IaSystemManager(DeviceManager deviceManager, IaSystemService iaSystemService) {
        iaSystems = new ConcurrentHashMap<>();
        this.deviceManager = deviceManager;
        this.iaSystemService = iaSystemService;

//        //整点采集一次数据
//        dataCollectionTimer = AppContext.timerManager.createCronTimer(this::collectSensorData, "0 0 * * * ?");
//        //定时检测系统状态
//        systemStatusMonitoringTimer = AppContext.timerManager.createTimer(this::monitorSystem, 10, true);
    }

    public void start() {
        //加载设备信息
        deviceManager.load();
        //加载智慧农业系统
        load();
        //启动设备管理器
        deviceManager.start();
        System.out.println("IaSystem started");

    }

    /**
     * 根据系统id获取某一系统
     *
     * @param iasId
     * @return
     */
    public IaSystemVo getIaSystemVo(int iasId) {
        IaSystem iaSystem = iaSystems.get(iasId);
        if (iaSystem != null) {
            return iaSystem.getIaSystemVo();
        }
        return null;
    }

    public IaSystem getIaSystemById(int id) {
        return iaSystems.get(id);
    }

    /**
     * 添加智慧农业系统
     *
     * @param iaSystemRecord 智慧农业系统配置信息
     * @return 成功返回ErrorCode.OK, 失败返回响应的错误码：
     */
    public ErrorCode addIaSystem(IaSystemRecord iaSystemRecord) {
        //向系统中添加智慧农业系统
        return innerAddIaSystem(iaSystemRecord);
    }

    /**
     * 删除智慧农业系统
     *
     * @param iasId 智慧农业系统id
     * @return 成功返回ErrorCode.OK, 失败返回响应的错误码：
     * 这里要说明返回的错误类型
     */
    public ErrorCode delIaSystem(int iasId) {
        //数据库中删除智慧农业系统
        ErrorCode errorCode = iaSystemService.delIaSystem(iasId);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }

        iaSystems.remove(iasId);
        return ErrorCode.OK;
    }

    /**
     * 删除智慧农业系统
     *
     * @param iasConfigVo 智慧农业系统配置信息
     * @return 成功返回ErrorCode.OK, 失败返回响应的错误码：
     * 这里要说明返回的错误类型
     */
    public ErrorCode updateIaSystem(IasConfigVo iasConfigVo) {
        IaSystem iaSystem = iaSystems.get(iasConfigVo.getId());
        if (iaSystem == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.update(iasConfigVo);
    }

    /**
     * 删除农田
     *
     * @param id 农田id iasId 所属系统id
     * @return 成功返回ErrorCode.OK, 失败返回响应的错误码：
     * 这里要说明返回的错误类型
     */
    public ErrorCode delFarmland(int id, int iasId) {
        //删除数据库中的农田
        ErrorCode errorCode = iaSystemService.delFarmland(id);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //删除系统中的农田
        IaSystem iaSystem = iaSystems.get(iasId);
        return iaSystem.innerDelFarmland(id);
    }

    /**
     * 更新农田
     *
     * @param farmlandConfigVo 农田信息
     * @return 成功返回ErrorCode.OK, 失败返回响应的错误码：
     * 这里要说明返回的错误类型
     */
    public ErrorCode updateFarmland(FarmlandConfigVo farmlandConfigVo) {
        IaSystem iaSystem = iaSystems.get(farmlandConfigVo.getIaSystemId());
        if (iaSystem == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.innerUpdateFarmland(farmlandConfigVo);
    }

    /**
     * 添加灌溉区
     *
     * @param irriAreaConfigVo 灌溉区配置信息
     * @return 成功返回ErrorCode.OK，失败返回响应的错误码
     */
    public ErrorCode addIrrigationArea(IrriAreaConfigVo irriAreaConfigVo) {
        //向数据库添加灌溉区
        IrrigationAreaRecord irrigationAreaRecord = new IrrigationAreaRecord();
        irrigationAreaRecord.setIasId(irriAreaConfigVo.getIasId());
        irrigationAreaRecord.setFmId(irriAreaConfigVo.getFmId());
        irrigationAreaRecord.setName(irriAreaConfigVo.getName());
        irrigationAreaRecord.setSortOrder(irriAreaConfigVo.getSortOrder());
        irrigationAreaRecord.setIsDelete(false);
        ErrorCode errorCode = iaSystemService.addIrrigationArea(irrigationAreaRecord);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //向系统中添加灌溉区
        return innerAddIrrigationArea(irrigationAreaRecord);
    }

    /**
     * 删除灌溉区
     *
     * @param irriAreaConfigVo 灌溉区配置信息
     * @return 成功返回ErrorCode.OK，失败返回响应的错误码
     */
    public ErrorCode delIrrigationArea(IrriAreaConfigVo irriAreaConfigVo) {
        //删除数据库中的灌溉区
        ErrorCode errorCode = iaSystemService.delIrrigationArea(irriAreaConfigVo.getId());
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //删除系统中的灌溉区
        IaSystem iaSystem = iaSystems.get(irriAreaConfigVo.getIasId());
        if (iaSystems == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.innerDelIrrigationArea(irriAreaConfigVo);
    }

    /**
     * 更新灌溉区
     *
     * @param irriAreaConfigVo 灌溉区配置信息
     * @return 成功返回ErrorCode.OK，失败返回响应的错误码
     */
    public ErrorCode updateIrrigationArea(IrriAreaConfigVo irriAreaConfigVo) {
        IaSystem iaSystem = iaSystems.get(irriAreaConfigVo.getIasId());
        if (iaSystem == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.innerUpdateIrrigation(irriAreaConfigVo);
    }

    /**
     * 添加水肥一体化系统
     *
     * @param irriFerSystemConfigVo 水肥一体化系统配置
     * @return 成功返回OK，失败返回相应的错误码：
     */
    public ErrorCode addIrriAndFerSystem(IrriFerSystemConfigVo irriFerSystemConfigVo) {
        IrriFerRecord irriFerRecord = new IrriFerRecord();
        irriFerRecord.setIasId(irriFerSystemConfigVo.getIasId());
        irriFerRecord.setName(irriFerSystemConfigVo.getName());
        irriFerRecord.setIsDelete(false);
        //向数据库中添加水肥一体化
        if (iaSystemService.countIrriFer(irriFerRecord.getIasId()) > 0) {
            return ErrorCode.IRRIFER_JUST_ONE;
        }
        ErrorCode errorCode = iaSystemService.addIrriFer(irriFerRecord);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //向系统中添加水肥一体化系统
        errorCode = innerAddIrriFerSystem(irriFerRecord);

        return errorCode;
    }

    public ErrorCode delIrriAndFerSystem(int id, int iasId) {
        //删除数据库中的水肥
        ErrorCode errorCode = iaSystemService.delIrriFer(id);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //删除系统中的水肥
        IaSystem iaSystem = iaSystems.get(iasId);
        iaSystem.innerDelIrriFer(id);
        return ErrorCode.OK;
    }

    public ErrorCode updateIrriAndFerSystem(IrriFerSystemConfigVo irriFerSystemConfigVo) {
        IaSystem iaSystem = iaSystems.get(irriFerSystemConfigVo.getIasId());
        if (iaSystem == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.innerUpdateIrriFer(irriFerSystemConfigVo);
    }

    /**
     * 向智慧农业系统中添加设备
     *
     * @param iasDeviceConfigVo 设备的配置信息
     * @return 成功返回OK，失败返回相应的错误码：
     */
    public ErrorCode addIasDevice(IasDeviceConfigVo iasDeviceConfigVo) {

        IasDeviceRecord iasDeviceRecord = iasDeviceConfigVo.createIasDeviceRecord();

        //向数据库中插入设备记录
        ErrorCode errorCode = iaSystemService.addDev(iasDeviceRecord);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //向系统中添加设备
        errorCode = innerAddIasDevice(iasDeviceRecord);
        if (errorCode != ErrorCode.OK) {
            iaSystemService.delDev(iasDeviceRecord.getDevId());
        }
        return errorCode;
    }

    public ErrorCode delIasDevice(Integer id) {
        IasDeviceRecord iasDeviceRecord = iaSystemService.selectDeviceById(id);
        if (iasDeviceRecord == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        //删除数据库中的设备
        ErrorCode errorCode = iaSystemService.delDev(iasDeviceRecord.getDevId());
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //删除系统中的设备
        IaSystem iaSystem = getIaSystemById(iasDeviceRecord.getIasId());
        if (null == iaSystem) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        errorCode = iaSystem.delIasDevice(iasDeviceRecord);
        return errorCode;
    }

    /**
     * 获取用户的灌溉区列表
     *
     * @return 成功返回OK，失败返回相应的错误码：
     */
    public IaSystemVo getIaSystemAreaVo(int iasId) {
        IaSystem iaSystem = iaSystems.get(iasId);
        if (iaSystem != null) {
            return iaSystem.getIaSystemAreaVo();
        }
        return null;
    }

    private void load() {
        //加载所有的智慧农业系统
        List<IaSystemRecord> iaSystemRecords = iaSystemService.getAllIaSystemRecords();
        for (IaSystemRecord iaSystemRecord : iaSystemRecords) {
            innerAddIaSystem(iaSystemRecord);
        }
        //加载农田
        List<FarmlandRecord> farmlandRecords = iaSystemService.getAllFarmlandRecords();
        for (FarmlandRecord farmlandRecord : farmlandRecords) {
            innerAddFarmland(farmlandRecord);
        }
        //加载灌溉区域
        List<IrrigationAreaRecord> irrigationAreaRecords = iaSystemService.getAllIrrigationAreaRecords();
        for (IrrigationAreaRecord irrigationAreaRecord : irrigationAreaRecords) {
            innerAddIrrigationArea(irrigationAreaRecord);
        }
        //加载水肥一体化子系统
        List<IrriFerRecord> irriFerRecords = iaSystemService.getAllIrriFerRecords();
        for (IrriFerRecord irriFerRecord : irriFerRecords) {
            innerAddIrriFerSystem(irriFerRecord);
        }
        //获取所有设备
        List<IasDeviceRecord> iasDeviceRecords = iaSystemService.getAllIasDeviceRecords();
        //加载可操作设备
        for (IasDeviceRecord iasDeviceRecord : iasDeviceRecords) {
            if (iasDeviceRecord.getUsageType() >= IasDeviceUsageType.OP_DEV_MIN_VALUE && iasDeviceRecord.getUsageType() <= IasDeviceUsageType.OP_DEV_MAX_VALUE) {
                innerAddIasDevice(iasDeviceRecord);
            }
        }
        //加载传感器设备
        for (IasDeviceRecord iasDeviceRecord : iasDeviceRecords) {
            if (iasDeviceRecord.getUsageType() >= IasDeviceUsageType.SENSOR_MIN_VALUE && iasDeviceRecord.getUsageType() <= IasDeviceUsageType.SENSOR_MAX_VALUE) {
                innerAddIasDevice(iasDeviceRecord);
            }
        }

        //加载摄像头
        List<CameraDO> cameraDOS = AppContext.getBean(CameraDOExtMapper.class).getAllCameras();
        for (CameraDO cameraDO : cameraDOS) {
            innerAddCamera(cameraDO);
        }
    }

    private ErrorCode innerAddIaSystem(IaSystemRecord iaSystemRecord) {
        IaSystem iaSystem = new IaSystem(iaSystemRecord);
        if (null != iaSystems.putIfAbsent(iaSystem.getIaSystemId(), iaSystem)) {
            return ErrorCode.IASYSTEM_EXIST;
        }
        return ErrorCode.OK;
    }

    private ErrorCode innerAddFarmland(FarmlandRecord farmlandRecord) {
        IaSystem iaSystem = getIaSystemById(farmlandRecord.getIasystemId());
        if (null == iaSystem) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }

        return iaSystem.innerAddFarmland(farmlandRecord);
    }

    private ErrorCode innerAddIrrigationArea(IrrigationAreaRecord irrigationAreaRecord) {
        IaSystem iaSystem = getIaSystemById(irrigationAreaRecord.getIasId());
        if (null == iaSystem) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }

        return iaSystem.InnerAddIrrigationArea(irrigationAreaRecord);
    }

    private ErrorCode innerAddIrriFerSystem(IrriFerRecord irriFerRecord) {
        IaSystem iaSystem = getIaSystemById(irriFerRecord.getIasId());
        if (null == iaSystem) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }

        return iaSystem.addIrriFerSystem(irriFerRecord);
    }

    private ErrorCode innerAddIasDevice(IasDeviceRecord iasDeviceRecord) {
        IaSystem iaSystem = getIaSystemById(iasDeviceRecord.getIasId());
        if (null == iaSystem) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.addIasDevice(iasDeviceRecord);
    }

    private ErrorCode innerAddCamera(CameraDO cameraDO) {
        IaSystem iaSystem = getIaSystemById(cameraDO.getIasId());
        if (null == iaSystem) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.innerAddCamera(cameraDO);
    }

}
