package com.zzwl.ias.iasystem.device;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DataCollectionRecord;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.domain.DeviceStatusDO;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.common.SensorValue;
import com.zzwl.ias.iasystem.communication.analyzer.OperateDeviceReply;
import com.zzwl.ias.iasystem.constant.DeviceConstant;
import com.zzwl.ias.iasystem.event.DeviceStateChangeEvent;
import com.zzwl.ias.mapper.DataCollectionRecordExtMapper;
import com.zzwl.ias.mapper.DataCollectionRecordMapper;
import com.zzwl.ias.mapper.DeviceStatusDOMapper;
import com.zzwl.ias.vo.DeviceVo;

import java.util.Calendar;
import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-23
 * Time: 20:40
 */
public abstract class Device {
    private final static int DEFAULT_TIMEOUT = 60;      //操作默认超时时间：60秒
    private MasterController masterController;

    private DeviceRecord deviceRecord;
    protected int state;
    SensorValue[] sensorValues;
    private DeviceOperation operation;

    public Device(DeviceRecord deviceRecord, MasterController masterController) {
        this.deviceRecord = deviceRecord;
        this.masterController = masterController;
        state = DeviceState.DEV_STATE_OFFLINE;
        operation = null;
        sensorValues = null;
    }

    public long getId() {
        return deviceRecord.getId();
    }

    public synchronized int getState() {
        return state;
    }

    public synchronized SensorValue[] getSensorValues() {
        if (sensorValues == null) {
            return null;
        }

        SensorValue[] values = new SensorValue[sensorValues.length];
        for (int index = 0; index < sensorValues.length; index++) {
            values[index] = sensorValues[index].clone();
        }
        return values;
    }

    public String getName() {
        return deviceRecord.getName();
    }

    public MasterController getMasterController() {
        return masterController;
    }

    public DeviceVo getDeviceVo() {
        DeviceVo deviceVo = new DeviceVo();
        deviceVo.setId(deviceRecord.getId());
        deviceVo.setType(DeviceAddr.getDevTypeById(deviceRecord.getId()));
        deviceVo.setName(deviceRecord.getName());
        deviceVo.setState(getState());
        deviceVo.setSensorValues(getSensorValues());
        return deviceVo;
    }

    synchronized boolean isCmdSnMatch(short cmdSn) {
        if (operation != null) {
            if (operation.getCommandSn() == cmdSn) {
                return true;
            }
        }
        return false;
    }

    abstract public ErrorCode updateState(int state, int[] sensorValues);

    synchronized ErrorCode updateDevInfo(int state, int[] sensorValues) {
        //获取设备当前状态信息
        DeviceStateChangeEvent event = new DeviceStateChangeEvent();
        event.setId(getId());
        event.setIaSystemId(deviceRecord.getIasId());
        event.setOldState(getState());
        event.setOldSensorValues(getSensorValues());

        //更新设备状态信息
        ErrorCode errorCode = updateState(state, sensorValues);
        if (errorCode != ErrorCode.OK) {
            return errorCode;
        }

        //获取设备更新后状态信息
        event.setNewState(getState());
        event.setNewSensorValues(getSensorValues());

        if (event.isChanged()) {
            //记录采集值
            collectSensorData(Calendar.getInstance().getTime(), DeviceConstant.COLLECT_TYPE_CHANGE);
            //记录设备状态
            collectStatus(event.getOldState(), event.getNewState());
            //通知IaSystem设备状态有变化
            AppContext.eventDispatcher.sendEvent(event);
        }

        return ErrorCode.OK;
    }

    void updateSensorData(int type, int value) {
        for (SensorValue sensorValue : sensorValues) {
            if (type == sensorValue.getType()) {
                sensorValue.setValue(value);
            }
        }
    }

    /**
     * 用于更新电磁阀、水泵等只有开关两个操作设备的状态
     *
     * @param state
     * @return
     */
    ErrorCode updateStateTemplate1(int state) {
        if (state != DeviceState.DEV_STATE_ON && state != DeviceState.DEV_STATE_OFF && state != DeviceState.DEV_STATE_OFFLINE) {
            return ErrorCode.OPERATION_ERR_REPLY_INVALID;
        }
        this.state = state;
        return ErrorCode.OK;
    }

    /**
     * 用于更新卷帘等有上卷、下卷、停止三个操作方式的设备的状态
     *
     * @param state
     * @return
     */
    ErrorCode updateStateTemplate2(int state) {
        if (state != DeviceState.DEV_STATE_OFFLINE && (state < DeviceState.DEV_STATE_CLOSED || state > DeviceState.DEV_STATE_CLOSING)) {
            return ErrorCode.OPERATION_ERR_REPLY_INVALID;
        }
        this.state = state;
        return ErrorCode.OK;
    }

    public void setName(String name) {
        deviceRecord.setName(name);
    }



    void collectSensorData(Date date, int type) {
        if (DeviceAddr.isSensor(getId())){
            //记录传感器采集值
            for (SensorValue sensorValue : sensorValues) {
                DataCollectionRecord record = new DataCollectionRecord();
                record.setSensorId(deviceRecord.getId());
                record.setTime(date);
                record.setType(sensorValue.getType());
                record.setValue(sensorValue.getValue());
                record.setCollectionType(type);
                AppContext.getBean(DataCollectionRecordExtMapper.class).insert(record);
            }
        }
    }

    /** 记录可操作设备的状态
     * @param oldStatus 老的设备状态
     * @param newStatus 新的设备状态
     */
    void collectStatus(Integer oldStatus, Integer newStatus){
        if (!DeviceAddr.isSensor(getId())) {
            DeviceStatusDO deviceStatusDO = new DeviceStatusDO();
            deviceStatusDO.setDevId(getId());
            deviceStatusDO.setTime(Calendar.getInstance().getTime());
            deviceStatusDO.setOldState(oldStatus);
            deviceStatusDO.setNewState(newStatus);
            AppContext.getBean(DeviceStatusDOMapper.class).insert(deviceStatusDO);
        }
    }

    public Integer getIasId() {
        return deviceRecord.getIasId();
    }


    /**
     * 执行设备操作
     *
     * @param request 设备操作请求
     */
    synchronized void operate(ReqOperateDevice request) {
        //主控制器为低功耗版本时，执行命令前需要先唤醒
        if (masterController.isLowConsumeBoard()) {
            request.setWakeUp(true);
        } else {
            request.setWakeUp(false);
        }

        //创建操作
        DeviceOperation newOp = DeviceOperation.create(request, this);
        if (newOp == null) {
            request.setResult(ErrorCode.OPERATION_INVALID);
            return;
        }

        if (operation != null) {
            request.setResult(ErrorCode.OPERATION_IN_PROGRESS);
            return;
        }

        //执行操作
        operation = newOp;
        ErrorCode errorCode = operation.execute(masterController.getCommandSn(), DEFAULT_TIMEOUT);
        if (errorCode != ErrorCode.OK){
            request.setResult(errorCode);
            operation = null;
        }
    }

    /**
     * 处理设备返回的操作结果
     *
     * @param reply 设备操作的结果
     */
    synchronized void handleOperationAck(OperateDeviceReply reply) {
        if (operation != null) {
            operation.handleAck(reply);
        }
        operation = null;
    }

    /**
     * 处理设备操作超时
     */
    synchronized void handleOperationTimeout() {
        if (operation != null) {
            operation.timeout();
        }
        operation = null;
    }
}
