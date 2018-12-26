package com.zzwl.ias.controller.iasystem;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.warning.ReqConfigSensorThresholdWarningDTO;
import com.zzwl.ias.dto.warning.ReqGetSensorThresholdWarningCfg;
import com.zzwl.ias.dto.warning.ThresholdWarningCfgDTO;
import com.zzwl.ias.dto.warning.ThresholdWarningCfgExtDTO;
import com.zzwl.ias.service.WarningService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-25
 * Time: 16:58
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/iaSystems/{iaSystemId}/devices/{devId}/warning")
public class ThresholdWarningController {

    private final WarningService warningService;

    @Autowired
    public ThresholdWarningController(WarningService warningService) {
        this.warningService = warningService;
    }

    /** 配置传感器采集阈值告警
     * @param iaSystemId 智慧农业系统ID
     * @param devId 传感器ID
     * @param dataType 数据类型
     * @param thresholdAlarmConfigs 告警配置
     * @return 配置结果
     */
    @RequiresAuthentication
    @RequestMapping(path = "/configuration", method = RequestMethod.POST)
    public Object configSensorThresholdWarning(@PathVariable Integer iaSystemId, @PathVariable Integer devId, @RequestParam Integer dataType,
                                    @RequestBody LinkedList<ThresholdWarningCfgDTO> thresholdAlarmConfigs) {
        ReqConfigSensorThresholdWarningDTO config = new ReqConfigSensorThresholdWarningDTO();
        config.setIasId(iaSystemId);
        config.setDevId(devId);
        config.setDataType(dataType);
        config.setConfig(thresholdAlarmConfigs);
        config.check();
        ErrorCode errorCode = warningService.configSensorThresholdWarning(config);
        if (errorCode != ErrorCode.OK){
            return Result.error(errorCode);
        }
        return getWarningConfig(iaSystemId, devId, dataType);
    }

    /** 获取采集值告警配置信息
     * @param iaSystemId 智慧农业系统ID
     * @param devId 传感器ID
     * @param dataType 数据类型
     * @return 传感器采集值预警配置
     */
    @RequiresAuthentication
    @RequestMapping(path = "/configuration", method = RequestMethod.GET)
    public Object getWarningConfig(@PathVariable Integer iaSystemId, @PathVariable Integer devId, @RequestParam Integer dataType) {
        ReqGetSensorThresholdWarningCfg request = new ReqGetSensorThresholdWarningCfg();
        request.setIasId(iaSystemId);
        request.setDevId(devId);
        request.setDataType(dataType);
        request.check();

        ThresholdWarningCfgExtDTO cfg = warningService.getSensorThresholdWarningConfig(request);
        if (cfg == null){
            return Result.error(ErrorCode.EMPTY);
        }
        return Result.ok(cfg);
    }
}
