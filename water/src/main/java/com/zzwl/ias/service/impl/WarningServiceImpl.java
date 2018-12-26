package com.zzwl.ias.service.impl;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.dto.warning.*;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.iasystem.constant.PermissionConstant;
import com.zzwl.ias.iasystem.constant.WarningConstant;
import com.zzwl.ias.iasystem.permission.PermissionManager;
import com.zzwl.ias.iasystem.permission.UserIasPermission;
import com.zzwl.ias.iasystem.warning.WarningUtil;
import com.zzwl.ias.mapper.WarningDOExtMapper;
import com.zzwl.ias.service.WarningService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-21
 * Time: 12:38
 */
@Service
public class WarningServiceImpl implements WarningService {
    /** 获取告警信息详情
     * @param warningId 告警的ID
     * @return 告警的详情
     */
    @Override
    public WarningDTO getWarningDetail(Long warningId) {
        WarningDO warningDO = AppContext.getBean(WarningDOExtMapper.class)
                .selectByPrimaryKey(warningId);
        AssertEx.isTrue(warningDO != null, ErrorCode.WARNING_NOT_EXIST);

        //权限管理
        UserIasPermission userIasPermission = AppContext.getBean(PermissionManager.class)
                .getUserIasPermission(warningDO.getAddrIas(), CurrentUserUtil.getCurrentUserId());
        if (warningDO.getAddrFarmland() != null) {
            AssertEx.isTrue(userIasPermission.checkFarmlandPermission(warningDO.getAddrFarmland()), ErrorCode.USER_HAS_NO_PERMISSION);
        }

        return WarningUtil.getWarningDTO(warningDO);
    }

    /**
     * 配置传感器阈值告警
     *
     * @param config 传感器阈值告警的配置
     * @return 配置结果
     */
    @Override
    public ErrorCode configSensorThresholdWarning(ReqConfigSensorThresholdWarningDTO config) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(config.getIasId());
        if (iaSystem == null){
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }

        //TODO 用户权限管理，暂时不用处理

        return iaSystem.configSensorThresholdWarning(config);
    }

    /**
     * 获取传感器阈值配置
     *
     * @param request 获取请求
     * @return 获取结果
     */
    @Override
    public ThresholdWarningCfgExtDTO getSensorThresholdWarningConfig(ReqGetSensorThresholdWarningCfg request) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(request.getIasId());
        if (iaSystem == null){
            return null;
        }

        //TODO 用户权限管理，暂时不用处理

        return iaSystem.getSensorThresholdWarningConfig(request);
    }

    /**
     * 获取告警摘要信息列表
     *
     * @param userId 用户ID
     * @param iasId  智慧农业系统ID
     * @param type   告警类型
     * @param cleared 告警是否清除
     * @param offset 起始位置
     * @param limit  偏移量
     * @return 摘要信息列表
     */
    @Override
    public List<WarningSummaryDTO> listWarningSummary(Integer userId, Integer iasId, Integer type, Integer cleared, Integer offset, Integer limit) {
        UserIasPermission userIasPermission = AppContext.getBean(PermissionManager.class)
                .getUserIasPermission(iasId, userId);

        List<WarningDO> warningDOS;
        if (cleared == 0){
            offset = null;
        }

        if (userIasPermission.getRole() == PermissionConstant.IASYSTEM_USER_RETRIEVE){
            warningDOS = AppContext.getBean(WarningDOExtMapper.class).listIasWarningByTypeAndFarmland(
                    cleared, WarningUtil.getWarningTypeByCat(type), iasId, userIasPermission.getFarmlands(), offset, limit);
        }else{
            warningDOS = AppContext.getBean(WarningDOExtMapper.class).listIasWarningByTypeAndFarmland(
                    cleared, WarningUtil.getWarningTypeByCat(type), iasId, null, offset, limit);
        }

        List<WarningSummaryDTO> warningSummaryDTOS = new LinkedList<>();
        for (WarningDO warningDO : warningDOS){
            WarningSummaryDTO warningSummaryDTO = new WarningSummaryDTO();
            warningSummaryDTO.setId(warningDO.getId());
            warningSummaryDTO.setProduceTime(warningDO.getProduceTime());
            warningSummaryDTO.setLocation(WarningUtil.getWarningLocation(warningDO));
            warningSummaryDTO.setSummary(WarningUtil.getWarningDescription(warningDO));
            warningSummaryDTOS.add(warningSummaryDTO);
        }
        AssertEx.isNotEmpty(warningSummaryDTOS);

        return warningSummaryDTOS;
    }

    /**
     * 清除告警
     *
     * @param userId    用户ID
     * @param warningId 告警ID
     */
    @Override
    public void clearWarning(Integer userId, Long warningId) {
        WarningDO warningDO = AppContext.getBean(WarningDOExtMapper.class).selectByPrimaryKey(warningId);
        AssertEx.isTrue(warningDO != null, ErrorCode.WARNING_NOT_EXIST);

        AssertEx.isTrue(warningDO.getType() == WarningConstant.IRRIGATION_FAIL, ErrorCode.WARNING_NOT_SUPPORT_MANUAL_CLEAR);

        //权限管理
        UserIasPermission userIasPermission = AppContext.getBean(PermissionManager.class)
                .getUserIasPermission(warningDO.getAddrIas(), userId);

        if (warningDO.getAddrFarmland() != null) {
            if (userIasPermission.getRole() == PermissionConstant.IASYSTEM_USER_RETRIEVE) {
                AssertEx.isTrue(userIasPermission.getFarmlands().contains(warningDO.getAddrFarmland()), ErrorCode.WARNING_NOT_EXIST);
            }
        }

        //清除告警
        AppContext.getBean(WarningDOExtMapper.class)
                .clearWarningById(warningId, Calendar.getInstance().getTime(), WarningConstant.RELEASE_TYPE_USER_CLEAR);
    }
}
