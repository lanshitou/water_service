package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceType;
import com.zzwl.ias.iasystem.communication.IaCommServer;
import com.zzwl.ias.iasystem.communication.IaFrame;
import com.zzwl.ias.mapper.DeviceRecordMapper;
import com.zzwl.ias.timer.IaTimer;
import com.zzwl.ias.vo.DeviceConfigVo;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by HuXin on 2017/9/1.
 */
public class DeviceManager {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static int KEEP_ALIVE_INTERVAL = 30;

    private IaCommServer iaCommServer;
    private IaTimer keepAliveTimer;
    private ConcurrentHashMap<Integer, MasterController> masterControllers;
    private DeviceRecordMapper deviceRecordMapper = AppContext.getBean(DeviceRecordMapper.class);
    private IaTimer dataCollectionTimer;

    public DeviceManager(IaCommServer iaCommServer) {
        this.masterControllers = new ConcurrentHashMap<>(10000);
        this.iaCommServer = iaCommServer;
    }

    public void start() {
        keepAliveTimer = AppContext.timerManager.createTimer(() -> check(), KEEP_ALIVE_INTERVAL, true);
        dataCollectionTimer = AppContext.timerManager.createCronTimer(this::collectSensorData, "0 0 * * * ?");
        iaCommServer.start();
    }

    public void load() {
        List<DeviceRecord> deviceRecords;
        deviceRecords = AppContext.deviceService.getAllDevicesSortedRecord();

        DeviceAddr addr = new DeviceAddr();
        //加载所有的首部控制
        for (DeviceRecord record : deviceRecords) {
            addr.setId(record.getId());
            if (addr.getDevType() == DeviceType.DEV_CONTROLLER && addr.getCtlerId() == 0) {
                loadDevice(record);
            }
        }
        //加载所有的田间控制器
        for (DeviceRecord record : deviceRecords) {
            addr.setId(record.getId());
            if (addr.getDevType() == DeviceType.DEV_CONTROLLER && addr.getCtlerId() != 0) {
                loadDevice(record);
            }
        }
        //加载普通设备
        for (DeviceRecord record : deviceRecords) {
            addr.setId(record.getId());
            if (addr.getDevType() != DeviceType.DEV_CONTROLLER) {
                loadDevice(record);
            }
        }

        logger.info("设备加载....");
        for (Integer key : masterControllers.keySet()) {
            logger.info(masterControllers.get(key).toString());
        }
    }

    ErrorCode loadDevice(DeviceRecord deviceRecord) {
        DeviceAddr addr = new DeviceAddr(deviceRecord.getId());
        if (!addr.isValid()) {
            return ErrorCode.DEV_ADDR_INVALID;
        }

        if (addr.getDevType() == DeviceType.DEV_CONTROLLER && addr.getCtlerId() == 0) {
            MasterController masterController = new MasterController(deviceRecord, this);
            if (null != masterControllers.putIfAbsent(addr.getSysId(), masterController)) {
                return ErrorCode.MASTER_CONTROLLER_EXIST;
            }
            return ErrorCode.OK;
        } else {
            MasterController masterController = masterControllers.get(addr.getSysId());
            if (null == masterController) {
                return ErrorCode.MASTER_CONTROLLER_NOT_EXIST;
            }
            return masterController.addDevice(deviceRecord);
        }
    }

    public ErrorCode addDevice(DeviceRecord deviceRecord) {
        ErrorCode errorCode = loadDevice(deviceRecord);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }
        //设备加入数据库
        try {
            deviceRecordMapper.insert(deviceRecord);
        } catch (Exception e) {
            //从DeviceManager中删除设备
            delDevice(deviceRecord.getId());
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    public ErrorCode delDevice(long id) {
        MasterController masterController = masterControllers.get(DeviceAddr.getSysIdById(id));
        if (masterController == null) {
            return ErrorCode.DEVICE_NOT_EXIST;
        }

        ErrorCode errorCode = masterController.delDevice(id);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }

        //如果是首部控制器，则需要从masterControllers中删除
        if (DeviceAddr.getDevTypeById(id) == DeviceType.DEV_CONTROLLER && DeviceAddr.getControllerIdById(id) == 0) {
            masterControllers.remove(DeviceAddr.getSysIdById(id));
        }

        return ErrorCode.OK;
    }

    public ErrorCode updateDevice(DeviceConfigVo deviceConfigVo) {
        try {
            DeviceRecord deviceRecord = deviceRecordMapper.selectByPrimaryKey(deviceConfigVo.getId());
            if (deviceRecord == null) {
                return ErrorCode.DEVICE_NOT_EXIST;
            }
            deviceRecord.setName(deviceConfigVo.getName());
            if (deviceConfigVo.getKeepAlive() != null) {
                deviceRecord.setKeepAlive(deviceConfigVo.getKeepAlive());
            }
            if (deviceConfigVo.getLowConsume() != null) {
                deviceRecord.setLowConsume(deviceConfigVo.getLowConsume());
            }
            deviceRecordMapper.updateByPrimaryKeySelective(deviceRecord);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }

        MasterController masterController = masterControllers.get(DeviceAddr.getSysIdById(deviceConfigVo.getId()));
        if (null == masterController) {
            return ErrorCode.DEVICE_NOT_EXIST;
        }
        return masterController.updateDevice(deviceConfigVo);
    }

    private void check() {
        for (Map.Entry<Integer, MasterController> entry : masterControllers.entrySet()) {
            MasterController masterController = entry.getValue();
            //检查主控制器是否在线
            masterController.checkOnline();
            //检查是否需要同步设备状态信息
            masterController.syncDeviceInfo();
        }
    }

    public ErrorCode dispatchFrame(ChannelHandlerContext ctx, IaFrame frame) {
        MasterController masterController;
        masterController = masterControllers.get(frame.getSysId());
        if (null != masterController) {
            masterController.handleFrame(ctx, frame);
            return ErrorCode.OK;
        } else {
            return ErrorCode.MASTER_CONTROLLER_NOT_EXIST;
        }
    }

    public void operateDevice(ReqOperateDevice request) {
        Device dev = getDevice(request.getDevId());
        if (dev == null) {
            request.setResult(ErrorCode.DEVICE_NOT_EXIST);
        } else {
            dev.operate(request);
        }
    }

    public Device getDevice(long devId) {
        MasterController masterController = masterControllers.get(DeviceAddr.getSysIdById(devId));
        if (masterController == null) {
            return null;
        }
        return masterController.getDevice(devId);
    }

    private void collectSensorData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        for (Map.Entry<Integer, MasterController> entry : masterControllers.entrySet()) {
            MasterController masterController = entry.getValue();
            masterController.collectSensorData(date);
        }
    }
}
