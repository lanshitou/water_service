package com.zzwl.ias.iasystem.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.domain.UserNotificationDO;
import com.zzwl.ias.iasystem.IasObjectAddr;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.iasystem.permission.PermissionManager;
import com.zzwl.ias.mapper.MessageDOExtMapper;

import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-30
 * Time: 10:14
 */
public class MessageUtil {
    static private MessageDOExtMapper messageDOExtMapper = AppContext.getBean(MessageDOExtMapper.class);
    static private PermissionManager permissionManager = AppContext.getBean(PermissionManager.class);
    static private IasMessage iasMessage = AppContext.getBean(IasMessage.class);

    /** 给用户发送智慧农业系统相关消息
     * @param messageDO 消息
     * @param iasId 智慧农业系统ID
     * @param farmlandId 农田ID
     */
    public static void sendIasMsg(MessageDO messageDO, Integer iasId, Integer farmlandId) {
        //插入消息
        messageDO.setIasId(iasId);
        messageDOExtMapper.insert(messageDO);

        //发送消息给用户
        List<Integer> users;
        if (farmlandId == null) {
            users = permissionManager.listIasUser(iasId);
        } else {
            users = permissionManager.listFarmlandUser(iasId, farmlandId);
        }
        IasMessageExt ext = new IasMessageExt();
        ext.setId(messageDO.getId());
        ext.setType(messageDO.getType());
        iasMessage.createAndSendMsgToUser(messageDO, ext, users);
    }


    /** 向用户发送通知消息
     * @param users 用户列表
     * @param userNotificationDO 通知消息
     */
    public static void SendNotifyMsg(LinkedList<Integer> users, UserNotificationDO userNotificationDO){
        //生成消息
        MessageDO messageDO = new MessageDO();
        messageDO.setCategory(MessageConstant.MSG_CAT_NOTIFICATION);
        messageDO.setType(MessageConstant.MSG_TYPE_NOTIFY_WEB);
        messageDO.setTitle(userNotificationDO.getTitle());
        messageDO.setSummary(userNotificationDO.getSummary());
        NotifyMessageExt ext = new NotifyMessageExt();
        ext.setArticleId(userNotificationDO.getArticleId());
        String extension;
        try {
            extension = AppContext.objectMapper.writeValueAsString(ext);
        } catch (JsonProcessingException e) {
            return;
        }
        messageDO.setExtension(extension);
        messageDOExtMapper.insert(messageDO);

        //向用户发送消息
        IasMessageExt msgExt = new IasMessageExt();
        msgExt.setId(messageDO.getId());
        msgExt.setType(messageDO.getType());
        iasMessage.createAndSendMsgToUser(messageDO, msgExt, users);
    }


    public static String getIasMsgLocation(Integer iasId, Integer irriFerId, Integer farmlandId, Integer areaId, Integer parentDevId){
        String location;
        location = IasObjectAddr.getIasName(iasId);
        if (farmlandId != null){
            location += "->" + IasObjectAddr.getFarmlandName(farmlandId);
            if (areaId != null){
                location += "->" + IasObjectAddr.getIrriAreaName(areaId);
                if (parentDevId != null){
                    location += "->" + IasObjectAddr.getParentDevName(parentDevId);
                }
            }
        }else if(irriFerId != null){
            location += "->" + IasObjectAddr.getIrriFerName(irriFerId);
            if (parentDevId != null){
                location += "->" + IasObjectAddr.getParentDevName(parentDevId);
            }
        }else{
            location += "->环境信息";
        }
        return location;
    }
}
