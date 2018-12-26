package com.zzwl.ias.service;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.ControllerStatusRecord;
import com.zzwl.ias.domain.DeviceOpRecord;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.dto.device.ReqSensorHistoryDataDTO;
import com.zzwl.ias.dto.device.SensorHistoryDataDTO;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.vo.DeviceConfigVo;
import com.zzwl.ias.vo.DeviceTopoVo;
import com.zzwl.ias.vo.DeviceTypeVo;
import com.zzwl.ias.vo.DeviceVo;

import java.util.List;

/**
 * Created by HuXin on 2017/12/13.
 */
public interface DeviceService {
    List<DeviceRecord> getAllDevicesSortedRecord();

    void recordDeviceOperation(DeviceOpRecord record);

    void recordControllerStatus(ControllerStatusRecord record);

    DeviceVo getDeviceById(long devId);

    ErrorCode addDevice(DeviceConfigVo deviceConfigVo);

    ErrorCode delDevice(int iasId, long devId);

    ErrorCode updateDevice(DeviceConfigVo deviceConfigVo);

    List<DeviceRecord> getAllController();

    DeviceTopoVo getDeviceTopo(Integer id);

    ErrorCode delDeviceById(long devId);

    //TODO 接口梳理

    /**
     * 执行设备操作
     *
     * @param request 设备操作请求
     */
    void operateDevice(ReqOperateDevice request);

    /**
     * 获取传感器历史数据采集值
     *
     * @param reqSensorHistoryDataDTO 获取历史数据请求
     * @return 历史数据采集值
     */
    SensorHistoryDataDTO getSensorHistoryData(ReqSensorHistoryDataDTO reqSensorHistoryDataDTO);

    /**
     * 所有设备类型及图标
     *
     * @return
     */
    List<DeviceTypeVo> selectAllDeviceType();
}
