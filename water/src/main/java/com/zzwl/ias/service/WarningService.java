package com.zzwl.ias.service;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.warning.*;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-21
 * Time: 12:37
 */
public interface WarningService {
    /** 获取告警信息详情
     * @param warningId 告警的ID
     * @return 告警的详情
     */
    WarningDTO getWarningDetail(Long warningId);

    /** 配置传感器阈值告警
     * @param config 传感器阈值告警的配置
     * @return 配置结果
     */
    ErrorCode configSensorThresholdWarning(ReqConfigSensorThresholdWarningDTO config);

    /** 获取传感器阈值配置
     * @param request 获取请求
     * @return 获取结果
     */
    ThresholdWarningCfgExtDTO getSensorThresholdWarningConfig(ReqGetSensorThresholdWarningCfg request);

    /** 获取告警摘要信息列表
     * @param userId 用户ID
     * @param iasId 智慧农业系统ID
     * @param type 告警类型
     * @param cleared 告警是否清除
     * @param offset 起始位置
     * @param limit 偏移量
     * @return 摘要信息列表
     */
    List<WarningSummaryDTO> listWarningSummary(Integer userId, Integer iasId, Integer type, Integer cleared, Integer offset, Integer limit);


    /** 清除告警
     * @param userId 用户ID
     * @param warningId 告警ID
     */
    void clearWarning(Integer userId, Long warningId);
}
