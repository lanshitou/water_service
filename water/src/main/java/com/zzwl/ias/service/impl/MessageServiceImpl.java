package com.zzwl.ias.service.impl;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.domain.*;
import com.zzwl.ias.dto.iasystem.IaSystemBasicInfoDTO;
import com.zzwl.ias.dto.info.InfoPreviewDTO;
import com.zzwl.ias.dto.message.*;
import com.zzwl.ias.dto.warning.ThresholdAlarmDto;
import com.zzwl.ias.dto.warning.WarningDTO;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.iasystem.message.NotifyMessageExt;
import com.zzwl.ias.iasystem.message.SensorThresholdAlarmMessage;
import com.zzwl.ias.iasystem.message.WarningMessageExt;
import com.zzwl.ias.iasystem.permission.PermissionManager;
import com.zzwl.ias.iasystem.permission.UserIasPermissionSet;
import com.zzwl.ias.iasystem.warning.WarningUtil;
import com.zzwl.ias.mapper.*;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.service.MessageService;
import com.zzwl.ias.service.WarningService;
import com.zzwl.ias.service.info.ClientInfo;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


/**
 * Description: 对消息相关的数据库表进行操作
 * User: MaYong
 * Date: 2018-06-22
 */
@Service
public class MessageServiceImpl implements MessageService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public List<MessageDtoOld<NormalMessageDto>> getNormalMessages(int offset, int limit) {
        List<MessageDtoOld<NormalMessageDto>> messages = new LinkedList<>();

        List<MessageSysRecord> messageSysRecords =
                AppContext.getBean(MessageSysRecordExtMapper.class).selectByPage(offset, limit, CurrentUserUtil.getCurrentUserId());
        for (MessageSysRecord record : messageSysRecords) {
            messages.add(NormalMessageDto.getMessageFromRecord(record));
        }
        return messages;
    }

    @Override
    public List<MessageDtoOld<ThresholdAlarmDto>> getThresholdAlarmMessage(int offset, int limit) {
        LinkedList<MessageDtoOld<ThresholdAlarmDto>> messages = new LinkedList<>();

        List<MessageAlarmRecord> messageAlarmRecords =
                AppContext.getBean(MessageAlarmRecordExtMapper.class).selectByUser(offset, limit, CurrentUserUtil.getCurrentUserId());
        for (MessageAlarmRecord record : messageAlarmRecords) {
            messages.add(ThresholdAlarmDto.getMessageFromRecord(record));
        }
        return messages;
    }

    @Override
    public List<ThresholdAlarmDto> getThresholdAlarm(int type, int offset, int limit) {
        LinkedList<ThresholdAlarmDto> alarms = new LinkedList<>();
        List<Integer> sensorIds = AppContext.getBean(PermissionManager.class).listUserSensor(CurrentUserUtil.getCurrentUserId());
        if (sensorIds.size() == 0) {
            return alarms;
        }

        List<AlarmThresholdRecord> alarmThresholdRecords =
                AppContext.getBean(AlarmThresholdRecordExtMapper.class).selectByStatus(null, type, offset, limit);
        for (AlarmThresholdRecord alarmThresholdRecord : alarmThresholdRecords) {
            ThresholdAlarmDto alarm = AppContext.getBean(SensorThresholdAlarmMessage.class).createThresholdAlarmDtoByAlarm(alarmThresholdRecord);
            alarms.add(alarm);
        }
        return alarms;
    }

    @Override
    public Integer countUnread() {
        return AppContext.getBean(UserMessageDOExtMapper.class)
                .countUnread(CurrentUserUtil.getCurrentUserId());
    }

    @Override
    public int countWarning() {
        UserIasPermissionSet userIasPermissionSet = AppContext.getBean(PermissionManager.class)
                .getUserIasPermissionSet(CurrentUserUtil.getCurrentUserId());
        if (userIasPermissionSet == null){
            return 0;
        }
        return AppContext.getBean(WarningDOExtMapper.class)
                .countThresholdWarning(userIasPermissionSet.getAdminPermissionIas(),
                        userIasPermissionSet.getRetrievePermissionIas(), userIasPermissionSet.getFarmlands());
    }

    @Override
    public void markRead(int type, int messageId) {
        AppContext.getBean(MessageUserRecordExtMapper.class).setMessageRead(CurrentUserUtil.getCurrentUserId(), messageId);
    }


    @Override
    public List<MessageDTO> listWarningMessage(int offset, int limit) {
        return AppContext.getBean(UserMessageDOExtMapper.class)
                .listWarningMessage(CurrentUserUtil.getCurrentUserId(), offset, limit);
    }

    @Override
    public List<MessageDTO> listNormalMessage(int offset, int limit) {
        return AppContext.getBean(UserMessageDOExtMapper.class)
                .listNormalMessage(CurrentUserUtil.getCurrentUserId(), offset, limit);
    }

    @Override
    public List<WarningDTO> listWarning(int status, int offset, int limit) {
        UserIasPermissionSet userIasPermissionSet = AppContext.getBean(PermissionManager.class)
                .getUserIasPermissionSet(CurrentUserUtil.getCurrentUserId());
        if (userIasPermissionSet == null){
            return null;
        }

        List<WarningDO> warningDOS = AppContext.getBean(WarningDOExtMapper.class)
                .listThresholdWarning(userIasPermissionSet.getAdminPermissionIas(),
                        userIasPermissionSet.getRetrievePermissionIas(),
                        userIasPermissionSet.getFarmlands(),
                        status != 1 ? 1 : 0, offset, limit);

        LinkedList<WarningDTO> warningDTOS = new LinkedList<>();
        for (WarningDO warningDO : warningDOS){
            warningDTOS.add(WarningUtil.getWarningDTO(warningDO));
        }

        return warningDTOS;
    }

    /** 获取用户消息摘要信息列表
     * @param reqListMsgSummaryDTO  获取消息摘要信息列表的请求
     * @return 消息摘要信息列表
     */
    @Override
    public List<MsgSummaryDTO> listMsgSummary(ReqListMsgSummaryDTO reqListMsgSummaryDTO) {
        return AppContext.getBean(UserMessageDOExtMapper.class)
                .listMsgSummary(reqListMsgSummaryDTO);
    }

    /**
     * 获取用户消息预览信息
     *
     * @param userId 用户ID
     * @return 用户消息预览信息
     */
    @Override
    public MsgPreviewDTO getMsgPreview(Integer userId) {
        MsgPreviewDTO msgPreviewDTO = new MsgPreviewDTO();
        UserMessageDOExtMapper mapper = AppContext.getBean(UserMessageDOExtMapper.class);
        ReqListMsgSummaryDTO req = new ReqListMsgSummaryDTO(null, MessageConstant.MSG_CAT_NOTIFICATION, 0, 1);

        SingleMsgPreviewDTO preview;
        List<MsgSummaryDTO> msgSummary;
        //通知类消息
        preview = new SingleMsgPreviewDTO();
        preview.setUnreadCount(mapper.getUnreadMsgNum(userId, MessageConstant.MSG_CAT_NOTIFICATION, null));
        msgSummary = mapper.listMsgSummary(req);
        if (msgSummary.size() == 0){
            preview.setTitle(null);
        }else{
            preview.setTitle(msgSummary.get(0).getTitle());
        }
        msgPreviewDTO.setNotify(preview);
        //灌溉类消息
        req.setCategory(MessageConstant.MSG_CAT_IRRIGATION);
        preview = new SingleMsgPreviewDTO();
        preview.setUnreadCount(mapper.getUnreadMsgNum(userId, MessageConstant.MSG_CAT_IRRIGATION, null));
        msgSummary = mapper.listMsgSummary(req);
        if (msgSummary.size() == 0){
            preview.setTitle(null);
        }else{
            preview.setTitle(msgSummary.get(0).getTitle());
        }
        msgPreviewDTO.setIrrigate(preview);
        //离线类消息
        req.setCategory(MessageConstant.MSG_CAT_OFFLINE);
        preview = new SingleMsgPreviewDTO();
        preview.setUnreadCount(mapper.getUnreadMsgNum(userId, MessageConstant.MSG_CAT_OFFLINE, null));
        msgSummary = mapper.listMsgSummary(req);
        if (msgSummary.size() == 0){
            preview.setTitle(null);
        }else{
            preview.setTitle(msgSummary.get(0).getTitle());
        }
        msgPreviewDTO.setOffline(preview);
        //告警类消息
        req.setCategory(MessageConstant.MSG_CAT_WARNING);
        preview = new SingleMsgPreviewDTO();
        preview.setUnreadCount(mapper.getUnreadMsgNum(userId, MessageConstant.MSG_CAT_WARNING, null));
        msgSummary = mapper.listMsgSummary(req);
        if (msgSummary.size() == 0){
            preview.setTitle(null);
        }else{
            preview.setTitle(msgSummary.get(0).getTitle());
        }
        msgPreviewDTO.setAlarm(preview);

        return msgPreviewDTO;
    }

    /** 获取消息详细信息
     * @param msgId 消息的ID
     * @return 消息详细信息
     */
    @Override
    public Object getMessageDetail(Long msgId) {
        //标记消息为已读
        AppContext.getBean(UserMessageDOExtMapper.class)
                .setRead(CurrentUserUtil.getCurrentUserId(), msgId, Calendar.getInstance().getTime());

        //获取用户消息
        MessageDO messageDO = AppContext.getBean(MessageDOExtMapper.class).getUserMsg(CurrentUserUtil.getCurrentUserId(), msgId);
        if (messageDO == null){
            return null;
        }
        //获取消息扩展信息
        switch (messageDO.getType()){
            case MessageConstant.MSG_TYPE_NOTIFY_WEB:{
                //网页推送消息详情
                NotifyMessageExt ext = NotifyMessageExt.getExtFromMessage(messageDO);
                if (ext == null){
                    return null;
                }
                return AppContext.getBean(ClientInfo.class).getArticlePreview(ext.getArticleId());
            }
            case MessageConstant.MSG_TYPE_IRRIGATION:{
                //灌溉作业消息
                IrrigationTaskStateVo ext = IrrigationTaskStateVo.getFromJson((String)messageDO.getExtension());
                if (ext == null){
                    return null;
                }
                ext.insertUserInfo();
                return ext;
            }
            case MessageConstant.MSG_TYPE_IRRIGATION_FAIL:
            case MessageConstant.MSG_TYPE_DEVICE_OFFLINE:
            case MessageConstant.MSG_TYPE_DEVICE_ONLINE:
            case MessageConstant.MSG_TYPE_THRESHOLD_WARNING_PRODUCED:
            case MessageConstant.MSG_TYPE_THRESHOLD_WARNING_CLEARED:{
                //阈值告警消息详情
                WarningMessageExt ext = WarningMessageExt.getExtFromMessage(messageDO);
                if (ext == null){
                    return null;
                }
                return AppContext.getBean(WarningService.class).getWarningDetail(ext.getWarningId());
            }
            default:{
                //do nothing
            }
        }
        return null;
    }

    /**
     * 获取用户未读消息数量
     *
     * @param type   消息类型
     * @param userId 用户ID
     * @return
     */
    @Override
    public LinkedList<MessageCountDTO> getMessageUnreadCount(Integer type, Integer userId) {
        UserMessageDOExtMapper mapper = AppContext.getBean(UserMessageDOExtMapper.class);
        LinkedList<MessageCountDTO> messageCountDTOS = new LinkedList<>();

        //获取用户智慧农业系统列表
        LinkedList<IaSystemBasicInfoDTO> iaSystemBasicInfoDTOS =
                AppContext.getBean(IaSystemService.class).getIaSystemsByUser(userId);

        MessageCountDTO messageCountDto;
        if (type == MessageConstant.MSG_CAT_NOTIFICATION){
            messageCountDto = new MessageCountDTO();
            messageCountDto.setCount(mapper.getUnreadMsgNum(userId, type, null));
            messageCountDTOS.add(messageCountDto);
        }else {
            for (IaSystemBasicInfoDTO iaSystemBasicInfoDTO : iaSystemBasicInfoDTOS){
                messageCountDto = new MessageCountDTO();
                messageCountDto.setId(iaSystemBasicInfoDTO.getId());
                messageCountDto.setName(iaSystemBasicInfoDTO.getName());
                messageCountDto.setCount(mapper.getUnreadMsgNum(userId, type, iaSystemBasicInfoDTO.getId()));
                messageCountDTOS.add(messageCountDto);
            }
        }

        return messageCountDTOS;
    }

    /**
     * 设置未读消息为已读
     *
     * @param type   消息类型
     * @param iasId  智慧农业系统ID
     * @param userId 用户ID
     */
    @Override
    public void setAllMessageRead(Integer type, Integer iasId, Integer userId) {
        if (type == MessageConstant.MSG_CAT_NOTIFICATION){
            iasId = null;
        }
        AppContext.getBean(UserMessageDOExtMapper.class).setAllMessageRead(type, iasId, userId, Calendar.getInstance().getTime());
    }
}
