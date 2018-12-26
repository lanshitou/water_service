package com.zzwl.ias.service;

import com.zzwl.ias.dto.message.*;
import com.zzwl.ias.dto.warning.ThresholdAlarmDto;
import com.zzwl.ias.dto.warning.WarningDTO;
import com.zzwl.ias.dto.warning.WarningSummaryDTO;

import java.util.LinkedList;
import java.util.List;

/**
 * Description: 对消息相关的数据库表进行操作
 * User: MaYong
 * Date: 2018-06-22
 * Time: 13:40
 */
public interface MessageService {
    List<ThresholdAlarmDto> getThresholdAlarm(int type, int offset, int limit);

    List<MessageDtoOld<NormalMessageDto>> getNormalMessages(int offset, int limit);

    List<MessageDtoOld<ThresholdAlarmDto>> getThresholdAlarmMessage(int offset, int limit);

    Integer countUnread();

    int countWarning();

    void markRead(int type, int messageId);

    List<MessageDTO> listWarningMessage(int offset, int limit);

    List<MessageDTO> listNormalMessage(int offset, int limit);



    List<WarningDTO> listWarning(int status, int offset, int limit);


    /** 获取用户消息摘要信息列表
     * @param reqListMsgSummaryDTO  获取消息摘要信息列表的请求
     * @return 消息摘要信息列表
     */
    List<MsgSummaryDTO> listMsgSummary(ReqListMsgSummaryDTO reqListMsgSummaryDTO);


    /** 获取用户消息预览信息
     * @param userId 用户ID
     * @return 用户消息预览信息
     */
    MsgPreviewDTO getMsgPreview(Integer userId);

    /** 获取消息详细信息
     * @param msgId 消息的ID
     * @return 消息详细信息
     */
    Object getMessageDetail(Long msgId);

    /** 获取用户未读消息数量
     * @param type 消息类型
     * @param userId 用户ID
     * @return 用户未读消息数量
     */
    LinkedList<MessageCountDTO> getMessageUnreadCount(Integer type, Integer userId);

    /** 设置未读消息为已读
     * @param type 消息类型
     * @param iasId 智慧农业系统ID
     * @param userId 用户ID
     */
    void setAllMessageRead(Integer type, Integer iasId, Integer userId);

}

