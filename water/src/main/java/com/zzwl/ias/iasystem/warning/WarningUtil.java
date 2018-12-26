package com.zzwl.ias.iasystem.warning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.IrrigationTaskRecord;
import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.dto.common.IasObjectAddrDTO;
import com.zzwl.ias.dto.irrigation.IrrigationWarningExtDTO;
import com.zzwl.ias.dto.warning.WarningStatisticsDTO;
import com.zzwl.ias.dto.warning.WarningDTO;
import com.zzwl.ias.iasystem.IasObjectAddr;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.iasystem.constant.WarningConstant;
import com.zzwl.ias.iasystem.message.IasMessage;
import com.zzwl.ias.iasystem.message.IasMessageExt;
import com.zzwl.ias.iasystem.message.WarningMessageExt;
import com.zzwl.ias.iasystem.permission.PermissionManager;
import com.zzwl.ias.mapper.IrrigationTaskRecordExtMapper;
import com.zzwl.ias.mapper.MessageDOExtMapper;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import sun.awt.image.ImageWatched;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-23
 * Time: 14:55
 */
public class WarningUtil {
    static private MessageDOExtMapper messageDOExtMapper = AppContext.getBean(MessageDOExtMapper.class);
    static private PermissionManager permissionManager = AppContext.getBean(PermissionManager.class);
    static private IasMessage iasMessage = AppContext.getBean(IasMessage.class);

    public static WarningDTO getWarningDTO(WarningDO warningDO) {
        WarningDTO warningDTO = new WarningDTO();
        warningDTO.setId(warningDO.getId());
        warningDTO.setType(warningDO.getType());
        warningDTO.setSubType(warningDO.getSubType());
        warningDTO.setLevel(warningDO.getLevel());
        warningDTO.setCleared(warningDO.getCleared());
        warningDTO.setProduceTime(warningDO.getProduceTime());
        warningDTO.setClearTime(warningDO.getClearTime());
        warningDO.setClearReason(warningDO.getClearReason());
        //地址
        IasObjectAddrDTO addr = new IasObjectAddrDTO();
        addr.setType(warningDO.getAddrType());
        addr.setIasId(warningDO.getAddrIas());
        addr.setIrriFerId(warningDO.getAddrIrriFer());
        addr.setFarmlandId(warningDO.getAddrFarmland());
        addr.setAreaId(warningDO.getAddrArea());
        addr.setDevId(warningDO.getAddrDev());
        addr.setParentDevId(warningDO.getAddrParentDev());
        IasObjectAddr.getIasObjectAddrName(addr);
        warningDTO.setAddr(addr);
        //扩展
        if (warningDO.getExtension() != null) {
            if (warningDTO.getType() == WarningConstant.IRRIGATION_FAIL){
                IrrigationTaskStateVo ext = IrrigationTaskStateVo.getFromJson((String) warningDO.getExtension());
                if (ext == null){
                    return null;
                }
                ext.insertUserInfo();
                ext.stateTranslateForApi();
                warningDTO.setExt(ext);
            }else {
                Map<String, Object> ext;
                try {
                    ext = AppContext.objectMapper
                            .readValue((String) warningDO.getExtension(), new TypeReference<Map<String, Object>>() {
                            });
                } catch (IOException e) {
                    return null;
                }
                warningDTO.setExt(ext);
            }
        }



        return warningDTO;
    }

    public static String getWarningLocation(WarningDO warningDO){
        String location = "";

        //智慧农业系统名称
        if (warningDO.getAddrIas() != null) {
            location = IasObjectAddr.getIasName(warningDO.getAddrIas());
        }

        if (warningDO.getAddrFarmland() != null){
            //农田名称
            location += "->" + IasObjectAddr.getFarmlandName(warningDO.getAddrFarmland());
            if (warningDO.getAddrArea() != null){
                //灌溉区名称
                location += "->" + IasObjectAddr.getIrriAreaName(warningDO.getAddrArea());
                if (warningDO.getAddrParentDev() != null){
                    location += "->" + IasObjectAddr.getParentDevName(warningDO.getAddrParentDev());
                }
                //设备名称
                if (warningDO.getAddrDev() != null){
                    location += "->" + IasObjectAddr.getDevName(warningDO.getAddrDev());
                }
            }
        }else if(warningDO.getAddrIrriFer() != null){
            //水肥一体化系统名称
            location += "->" + IasObjectAddr.getIrriFerName(warningDO.getAddrIrriFer());
            if (warningDO.getAddrParentDev() != null){
                location += "->" + IasObjectAddr.getParentDevName(warningDO.getAddrParentDev());
            }
            //设备名称
            if (warningDO.getAddrDev() != null){
                location += "->" + IasObjectAddr.getDevName(warningDO.getAddrDev());
            }
        }else{
            //设备名称
            if (warningDO.getAddrDev() != null){
                location += "->" + IasObjectAddr.getDevName(warningDO.getAddrDev());
            }
        }
        return location;
    }

    public static LinkedList<Integer> getWarningTypeByCat(Integer cat){
        LinkedList<Integer> types = new LinkedList<>();
        switch (cat){
            case WarningConstant.WARNING_CAT_THRESHOLD: {
                //阈值告警
                types.add(WarningConstant.OVER_UPPER_LIMIT_WARN);
                types.add(WarningConstant.UNDER_LOWER_LIMIT_WARN);
                break;
            }
            case WarningConstant.WARNING_CAT_IRRIGATION:{
                //灌溉任务告警
                types.add(WarningConstant.IRRIGATION_FAIL);
                break;
            }
            case WarningConstant.WARNING_CAT_OFFLINE:{
                //离线告警
                types.add(WarningConstant.DEVICE_OFFLINE);
                break;
            }
            case WarningConstant.WARNING_CAT_OTHER:{
                //其他告警，目前没有，使用没有告警来替代
                types.add(WarningConstant.WARNING_NONE);
                break;
            }
            default: {
                types.add(WarningConstant.WARNING_NONE);
            }
        }

        return types;
    }

    public final static int WARNING_NONE = 0;               //没有告警
    public final static int OVER_UPPER_LIMIT_WARN = 1;     //上限告警
    public final static int UNDER_LOWER_LIMIT_WARN = 2;    //下限告警
    public final static int DEVICE_OFFLINE = 3;             //设备离线告警
    public final static int IRRIGATION_FAIL = 4;            //灌溉任务执行失败

    public static String getWarningDescription(WarningDO warningDO) {
        String desc;
        switch (warningDO.getType()) {
            case WarningConstant.OVER_UPPER_LIMIT_WARN: {
                desc = "传感器采集值超过告警上限阈值";
                break;
            }
            case WarningConstant.UNDER_LOWER_LIMIT_WARN:{
                desc = "传感器采集值低于告警下限阈值";
                break;
            }
            case WarningConstant.DEVICE_OFFLINE:{
                desc = "设备长时间离线";
                break;
            }
            case WarningConstant.IRRIGATION_FAIL:{
                desc = "灌溉任务执行失败";
                break;
            }
            default: {
                desc = "";
            }
        }

        return desc;
    }

    /** 给告警消息扩展字段
     * @param messageDO 消息
     * @param warningDO 告警
     */
    public static void createWarningMsgExt(MessageDO messageDO, WarningDO warningDO){
        WarningMessageExt ext = new WarningMessageExt();
        ext.setWarningId(warningDO.getId());
        String extension;
        try {
            extension = AppContext.objectMapper.writeValueAsString(ext);
        } catch (JsonProcessingException e) {
            //这里不应该失败
            return;
        }
        messageDO.setExtension(extension);
    }

    /** 获取告警统计信息
     * @param warningDOS 告警列表
     * @param farmlandId 农田ID。如果农田ID不为空，则只统计该农田下的告警，如果ID为空，则统计所有告警
     * @return 告警统计信息
     */
    public static WarningStatisticsDTO getWarningStat(LinkedList<WarningDO> warningDOS, Integer farmlandId){
        WarningStatisticsDTO warningStatisticsDTO = new WarningStatisticsDTO();
        int threshold = 0;
        int offline = 0;
        int irri = 0;
        int other = 0;
        for (WarningDO warningDO : warningDOS){
            if (farmlandId != null && !farmlandId.equals(warningDO.getAddrFarmland())){
                continue;
            }

            if (warningDO.getType() == WarningConstant.OVER_UPPER_LIMIT_WARN
                    || warningDO.getType() == WarningConstant.UNDER_LOWER_LIMIT_WARN){
                threshold++;
            }else if (warningDO.getType() == WarningConstant.DEVICE_OFFLINE){
                offline++;
            }else if (warningDO.getType() == WarningConstant.IRRIGATION_FAIL){
                irri++;
            }else {
                other++;
            }
        }
        warningStatisticsDTO.setThresholdWarningCount(threshold);
        warningStatisticsDTO.setOfflineWarningCount(offline);
        warningStatisticsDTO.setIrriWarningCount(irri);
        warningStatisticsDTO.setOtherWarningCount(other);

        return warningStatisticsDTO;
    }
}
