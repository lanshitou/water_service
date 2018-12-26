package com.zzwl.ias.service.impl;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.ControllerStatusRecord;
import com.zzwl.ias.domain.DeviceOpRecord;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.dto.device.ReqSensorHistoryDataDTO;
import com.zzwl.ias.dto.device.SensorDataDTO;
import com.zzwl.ias.dto.device.SensorHistoryDataDTO;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceType;
import com.zzwl.ias.iasystem.device.Device;
import com.zzwl.ias.iasystem.device.DeviceManager;
import com.zzwl.ias.mapper.*;
import com.zzwl.ias.service.DeviceService;
import com.zzwl.ias.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by HuXin on 2017/12/13.
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IaSystemRecordMapper iaSystemRecordMapper;

    @Autowired
    private DeviceRecordMapper deviceRecordMapper;

    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private DeviceOpRecordExtMapper deviceOpRecordExtMapper;

    @Autowired
    private ControllerStatusRecordExtMapper controllerStatusRecordExtMapper;

    @Autowired
    private DeviceTypeExtMapper deviceTypeExtMapper;

    @Override
    public List<DeviceRecord> getAllDevicesSortedRecord() {
        return deviceRecordMapper.getAllDevicesOrderByAddress();
    }

    @Override
    public void recordDeviceOperation(DeviceOpRecord record) {
        deviceOpRecordExtMapper.insert(record);
    }

    @Override
    public void operateDevice(ReqOperateDevice request) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(request.getIasId());
        if (iaSystem == null) {
            request.setResult(ErrorCode.IASYSTEM_NOT_EXIST);
        }
        deviceManager.operateDevice(request);
        return;
    }

    @Override
    public DeviceVo getDeviceById(long devId) {
        Device device = deviceManager.getDevice(devId);
        if (device == null) {
            return null;
        } else {
            return device.getDeviceVo();
        }
    }

    @Override
    public ErrorCode addDevice(DeviceConfigVo deviceConfigVo) {
        DeviceRecord deviceRecord = new DeviceRecord();
        deviceRecord.setIasId(deviceConfigVo.getIasId());
        deviceRecord.setId(deviceConfigVo.getId());
        deviceRecord.setName(deviceConfigVo.getName());
        deviceRecord.setKeepAlive(deviceConfigVo.getKeepAlive());
        deviceRecord.setLowConsume(deviceConfigVo.getLowConsume());
        deviceRecord.setConfig(null);

        return deviceManager.addDevice(deviceRecord);
    }

    @Override
    public ErrorCode delDevice(int iasId, long devId) {
        return deviceManager.delDevice(devId);
    }

    @Override
    public ErrorCode updateDevice(DeviceConfigVo deviceConfigVo) {
        return deviceManager.updateDevice(deviceConfigVo);
    }

    @Override
    public List<DeviceRecord> getAllController() {
        List<DeviceRecord> devices = deviceRecordMapper.getAllDevicesOrderByAddress();
        List<DeviceRecord> controllers = new ArrayList<>();
        for (DeviceRecord device : devices) {
            if (DeviceAddr.getDevTypeById(device.getId()) == 0) {
                controllers.add(device);
            }
        }
        return controllers;
    }

    @Override
    public DeviceTopoVo getDeviceTopo(Integer id) {
        List<MasterControllerVo> masterControllerVos = new ArrayList<>();
        List<DeviceRecord> devices = deviceRecordMapper.selectAllDeviceByIasId(id);
        //查询所有类型icon，提高效率
        List<Map<String, String>> icons = deviceRecordMapper.selectAllIcon();
        Map<String, String> ids = new HashMap<>();
        for (Map map : icons) {
            ids.put(String.valueOf(map.get("cron")), String.valueOf(map.get("code")));
        }
        //找出所有首部控制器
        List<DeviceRecord> masters = new ArrayList<>();
        for (DeviceRecord device : devices) {
            byte dev_type = (byte) (device.getId() >> 8);
            device.setIcon(ids.get(String.valueOf(dev_type)));
            if (DeviceAddr.isMasterController(device.getId())) {
                masters.add(device);
            }
        }
        //遍历首部,寻找下属设备
        for (DeviceRecord master : masters) {
            byte masterDeviceType = (byte) (master.getId() >> 8);
            master.setIcon(ids.get(String.valueOf(masterDeviceType)));
            MasterControllerVo mvo = new MasterControllerVo();
            for (DeviceRecord device : devices) {
                byte devType = (byte) (device.getId() >> 8);
                device.setIcon(ids.get(String.valueOf(devType)));
                FarmControllerVo fvo = new FarmControllerVo();
                //寻找首部下的田间控制器
                if (DeviceAddr.isSlaveController(device.getId())
                        && DeviceAddr.getSysIdById(device.getId()) == DeviceAddr.getSysIdById(master.getId())) {
                    DeviceVo1 vo = new DeviceVo1();
                    vo.setDeviceRecord(device);
                    vo.setDeviceAddr(device.getId());
                    fvo.farmCo = vo;
                    //获取田间控制器下的设备
                    for (DeviceRecord device1 : devices) {
                        byte device1Type = (byte) (device1.getId() >> 8);
                        device1.setIcon(ids.get(String.valueOf(device1Type)));
                        if (DeviceAddr.getSysIdById(device1.getId()) == DeviceAddr.getSysIdById(device.getId())
                                && DeviceAddr.getControllerIdById(device1.getId()) == DeviceAddr.getControllerIdById(device.getId())
                                && device != device1) {
                            DeviceVo1 vo1 = new DeviceVo1();
                            vo1.setDeviceRecord(device1);
                            vo1.setDeviceAddr(device1.getId());
                            fvo.devices.add(vo1);
                        }
                    }
                    mvo.farmControllers.add(fvo);
                }
                //寻找首部下的其他设备
                if (DeviceAddr.getSysIdById(device.getId()) == DeviceAddr.getSysIdById(master.getId())
                        && DeviceAddr.getControllerIdById(device.getId()) == 0
                        && DeviceAddr.getDevTypeById(device.getId()) != DeviceType.DEV_CONTROLLER) {
                    DeviceVo1 vo = new DeviceVo1();
                    vo.setDeviceRecord(device);
                    vo.setDeviceAddr(device.getId());
                    mvo.devices.add(vo);
                }
            }
            DeviceVo1 vo = new DeviceVo1();
            vo.setDeviceRecord(master);
            vo.setDeviceAddr(master.getId());
            mvo.masterCo = vo;
            masterControllerVos.add(mvo);
        }
        DeviceTopoVo dvo = new DeviceTopoVo();
        dvo.iaSystem = iaSystemRecordMapper.selectIaSystemById(id);
        dvo.masterControllers = masterControllerVos;
        return dvo;
    }

    @Override
    public void recordControllerStatus(ControllerStatusRecord record) {
        controllerStatusRecordExtMapper.insert(record);
    }

    @Override
    public ErrorCode delDeviceById(long devId) {
        try {
            deviceRecordMapper.deleteByPrimaryKey(devId);
        } catch (Exception e) {
            return ErrorCode.DEVICE_IS_USING_IN_IASYSTEM;
        }
        return ErrorCode.OK;
    }

    //TODO 接口梳理

    /**
     * 获取传感器历史数据采集值
     *
     * @param reqSensorHistoryDataDTO 获取历史数据请求
     * @return 历史数据采集值
     */
    @Override
    public SensorHistoryDataDTO getSensorHistoryData(ReqSensorHistoryDataDTO reqSensorHistoryDataDTO) {
        Device device = AppContext.deviceManager.getDevice(reqSensorHistoryDataDTO.getDevId());
        if (device == null) {
            return null;
        }
        SensorHistoryDataDTO sensorHistoryDataDTO = new SensorHistoryDataDTO();
        LinkedList<SensorDataDTO> historyData = AppContext.getBean(DataCollectionRecordExtMapper.class).listSensorHistoryData(reqSensorHistoryDataDTO);
        sensorHistoryDataDTO.setHistory(historyData);
        return sensorHistoryDataDTO;
    }

    @Override
    public List<DeviceTypeVo> selectAllDeviceType() {
        return deviceTypeExtMapper.selectAllDeviceType();
    }

}
