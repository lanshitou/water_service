package com.zzwl.ias.controller.iasystem;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.domain.DeviceOperateDO;
import com.zzwl.ias.dto.device.DeviceOperateDTO;
import com.zzwl.ias.dto.device.ReqSensorHistoryDataDTO;
import com.zzwl.ias.dto.device.SensorHistoryDataDTO;
import com.zzwl.ias.dto.iasystem.DeviceDTO;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.constant.DeviceConstant;
import com.zzwl.ias.service.IaSystemUserMngService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Description: 用户设备管理相关接口
 * User: HuXin
 * Date: 2018-09-21
 * Time: 16:01
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/iaSystems/{iaSystemId}/devices")
public class DeviceController {
    private final IaSystemUserMngService iaSystemUserMngService;

    @Autowired
    public DeviceController(IaSystemUserMngService iaSystemUserMngService) {
        this.iaSystemUserMngService = iaSystemUserMngService;
    }

    /** 设备操作接口
     * @param iaSystemId 智慧农业系统ID
     * @param deviceId 设备的ID
     * @param request 用户操作请求
     * @return 操作结果
     */
    @RequiresAuthentication
    @RequestMapping(path = "/{deviceId}/operate", method = RequestMethod.POST)
    public Object operateDevice(@PathVariable int iaSystemId, @PathVariable  Integer deviceId, @RequestBody ReqOperateDevice request) {
        request.setIasId(iaSystemId);
        request.setIasDevId(deviceId);
        request.setSource(DeviceConstant.OP_DEV_SRC_USER);
        request.check();

        DeviceDTO deviceDTO = iaSystemUserMngService.operateDevice(request);
        return Result.ok(deviceDTO);
    }

    @RequiresAuthentication
    @RequestMapping(path = "/{deviceId}/operate/history", method = RequestMethod.GET)
    public Result<?> listHistoryOperation(@PathVariable Integer iaSystemId, @PathVariable Integer deviceId, @RequestParam Integer type,
                                          @RequestParam Integer offset, @RequestParam Integer limit){
        AssertEx.isTrue(type == DeviceConstant.OP_DEV_SRC_USER || type == DeviceConstant.OP_DEV_SRC_IRRI_TASK
                || type == DeviceConstant.OP_DEV_SRC_ANY, ErrorCode.INVALID_PARAMS);
        if (type == DeviceConstant.OP_DEV_SRC_ANY){
            type = null;
        }

        List<DeviceOperateDTO> deviceOperateDOList = iaSystemUserMngService.listHistoryOperation(
                CurrentUserUtil.getCurrentUserId(), iaSystemId, deviceId, type, offset, limit);
        return Result.ok(deviceOperateDOList);
    }

    /** 获取传感器历史采集值
     * @param iaSystemId 智慧农业系统ID
     * @param deviceId 传感器
     * @param dataType 采集值类型
     * @param startDate 采集开始时间
     * @param endDate 采集结束时间
     * @return 采集历史记录
     */
    @RequiresAuthentication
    @RequestMapping(path = "/{deviceId}/data_collections", method = RequestMethod.GET)
    public Object getDcPointData(@PathVariable Integer iaSystemId, @PathVariable Integer deviceId,
                                 @RequestParam Integer dataType,
                                 @RequestParam Long startDate,
                                 @RequestParam Long endDate) {

        ReqSensorHistoryDataDTO reqSensorHistoryDataDTO = new ReqSensorHistoryDataDTO();
        reqSensorHistoryDataDTO.setIasId(iaSystemId);
        reqSensorHistoryDataDTO.setIasDevId(deviceId);
        reqSensorHistoryDataDTO.setDataType(dataType);
        reqSensorHistoryDataDTO.setCollectionType(DeviceConstant.COLLECT_TYPE_PERIOD);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            reqSensorHistoryDataDTO.setStartDate(format.parse(format.format(startDate)));
            reqSensorHistoryDataDTO.setEndDate(format.parse(format.format(endDate)));
        } catch (ParseException e) {
            return Result.error(ErrorCode.INVALID_PARAMS);
        }

        SensorHistoryDataDTO sensorHistoryDataDTO = iaSystemUserMngService.getHistorySensorDate(reqSensorHistoryDataDTO);
        if (sensorHistoryDataDTO == null){
            return Result.error(ErrorCode.DEVICE_NOT_EXIST);
        }

        return Result.ok(sensorHistoryDataDTO);
    }
}
