package com.zzwl.ias.iasystem.warning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.dto.warning.DeviceOfflineWarningExtDTO;
import com.zzwl.ias.iasystem.IasObject;
import com.zzwl.ias.iasystem.IasObjectAddr;
import com.zzwl.ias.iasystem.IasOpDevice;
import com.zzwl.ias.iasystem.IasSensor;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.iasystem.constant.WarningConstant;
import com.zzwl.ias.iasystem.message.MessageUtil;
import com.zzwl.ias.iasystem.message.WarningMessageExt;
import com.zzwl.ias.mapper.WarningDOExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-26
 * Time: 15:28
 */
@Component
public class DeviceOfflineWarning {
    @Autowired
    private WarningDOExtMapper warningDOExtMapper;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public void createOfflineWarning(IasObject iasObject){
        //如果当前有告警，则不产生新的告警
        List<WarningDO> warningDOS = warningDOExtMapper.listCurrWarningByAddrAndType(
                iasObject.getAddr(), WarningConstant.DEVICE_OFFLINE, null);
        if (warningDOS.size() != 0){
            return;
        }

        IasObjectAddr addr = iasObject.getAddr();
        WarningDO warningDO = new WarningDO();
        warningDO.setType(WarningConstant.DEVICE_OFFLINE);
        warningDO.setSubType(0);    //离线告警没有子类型
        warningDO.setLevel(WarningConstant.WARNING_LEVEL_HIGH);
        warningDO.setCleared(false);
        warningDO.setProduceTime(Calendar.getInstance().getTime());
        warningDO.setAddrType(addr.getType());
        warningDO.setAddrIas(addr.getIasId());
        warningDO.setAddrIrriFer(addr.getIrriFerId());
        warningDO.setAddrFarmland(addr.getFarmlandId());
        warningDO.setAddrArea(addr.getIrriAreaId());
        warningDO.setAddrParentDev(addr.getParentDevId());
        warningDO.setAddrDev(addr.getDevId());

        DeviceOfflineWarningExtDTO extDTO = new DeviceOfflineWarningExtDTO();
        Date offlineTime = Calendar.getInstance().getTime();
        offlineTime.setTime(warningDO.getProduceTime().getTime() - WarningConstant.DEVICE_OFFLINE_WARNING_TIME * 1000);
        extDTO.setOfflineTime(offlineTime);
        if (iasObject instanceof IasSensor){
            int type = DeviceAddr.getDevTypeById(((IasSensor) iasObject).getDevId());
            extDTO.setDevType(type);
        }else if (iasObject instanceof IasOpDevice){
            int type = DeviceAddr.getDevTypeById(((IasOpDevice) iasObject).getDevId());
            extDTO.setDevType(type);
        }
        try {
            String ext = AppContext.objectMapper.writeValueAsString(extDTO);
            warningDO.setExtension(ext);
        } catch (JsonProcessingException e) {
            return;
        }

        AppContext.getBean(WarningDOExtMapper.class).insert(warningDO);

        //消除阈值告警（不发送告警消除消息）
        AppContext.getBean(WarningDOExtMapper.class).clearWarning(WarningConstant.OVER_UPPER_LIMIT_WARN, null,
                Calendar.getInstance().getTime(), WarningConstant.RELEASE_TYPE_DEVICE_OFFLINE, addr);

        AppContext.getBean(WarningDOExtMapper.class).clearWarning(WarningConstant.UNDER_LOWER_LIMIT_WARN, null,
                Calendar.getInstance().getTime(), WarningConstant.RELEASE_TYPE_DEVICE_OFFLINE, addr);

        //发送告警消息
        MessageDO messageDO = createOfflineWarningMsg(warningDO);
        MessageUtil.sendIasMsg(messageDO, warningDO.getAddrIas(), warningDO.getAddrFarmland());
    }

    public void clearOfflineWarning(IasObject iasObject){
        List<WarningDO> warningDOS = warningDOExtMapper.listCurrWarningByAddrAndType(
                iasObject.getAddr(), WarningConstant.DEVICE_OFFLINE, null);
        if (warningDOS.size() == 0){
            return;
        }

        //当前的告警应该只有1个
        for (WarningDO warningDO : warningDOS){
            warningDO.setCleared(true);
            warningDO.setClearReason(WarningConstant.RELEASE_TYPE_DEVICE_ONLINE);
            warningDO.setClearTime(Calendar.getInstance().getTime());
            AppContext.getBean(WarningDOExtMapper.class).updateByPrimaryKey(warningDO);
            //发送告警消除消息
            MessageDO messageDO = createOfflineWarningMsg(warningDO);
            MessageUtil.sendIasMsg(messageDO, warningDO.getAddrIas(), warningDO.getAddrFarmland());
        }
    }

    private MessageDO createOfflineWarningMsg(WarningDO warningDO){
        MessageDO messageDO = new MessageDO();
        messageDO.setIasId(warningDO.getAddrIas());
        messageDO.setCategory(MessageConstant.MSG_CAT_OFFLINE);
        if (warningDO.getCleared()){
            messageDO.setType(MessageConstant.MSG_TYPE_DEVICE_ONLINE);
        }else{
            messageDO.setType(MessageConstant.MSG_TYPE_DEVICE_OFFLINE);
        }

        //生成Title
        String title;
        if (warningDO.getCleared()){
            title = "设备长时间离线告警解除";
        }else{
            title = "设备长时间离线";
        }
        messageDO.setTitle(title);

        //生成Summary
        String summary;
        summary = "告警位置:" + WarningUtil.getWarningLocation(warningDO);
        messageDO.setSummary(summary);

        WarningMessageExt ext = new WarningMessageExt();
        ext.setWarningId(warningDO.getId());
        String extension;
        try {
            extension = AppContext.objectMapper.writeValueAsString(ext);
        } catch (JsonProcessingException e) {
            return null;
        }
        messageDO.setExtension(extension);
        return messageDO;
    }
}
