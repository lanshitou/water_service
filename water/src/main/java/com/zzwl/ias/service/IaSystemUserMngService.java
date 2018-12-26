package com.zzwl.ias.service;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.DeviceOperateDO;
import com.zzwl.ias.dto.device.DeviceOperateDTO;
import com.zzwl.ias.dto.device.ReqSensorHistoryDataDTO;
import com.zzwl.ias.dto.device.SensorHistoryDataDTO;
import com.zzwl.ias.dto.iasystem.DeviceDTO;
import com.zzwl.ias.iasystem.ReqOperateDevice;

import java.util.List;

/**
 * Description:智慧农业系统用户管理服务
 * User: HuXin
 * Date: 2018-08-29
 * Time: 16:21
 */
public interface IaSystemUserMngService {
    /** 用户操作设备接口
     * @param request 操作设备相关的参数
     * @return 操作结束后设备状态
     */
    DeviceDTO operateDevice(ReqOperateDevice request);

    /** 用户获取传感器历史采集值
     * @param request 获取历史采集值相关接口
     * @return 获取结果
     */
    SensorHistoryDataDTO getHistorySensorDate(ReqSensorHistoryDataDTO request);

    /** 获取设备操作记录
     * @param userId 用户ID
     * @param iasId 智慧农业系统ID
     * @param deviceId 设备ID
     * @param type 查询类型
     * @param offset 起始位置
     * @param limit 偏移量
     * @return 设备操作记录
     */
    List<DeviceOperateDTO> listHistoryOperation(Integer userId, Integer iasId, Integer deviceId, Integer type, Integer offset, Integer limit);
}
