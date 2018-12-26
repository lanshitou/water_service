package com.zzwl.ias.dto.message;

import com.zzwl.ias.domain.MessageSysRecord;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-14
 * Time: 8:25
 */
public class NormalMessageDto {
    public static MessageDtoOld<NormalMessageDto> getMessageFromRecord(MessageSysRecord record) {
        MessageDtoOld<NormalMessageDto> msg = new MessageDtoOld<NormalMessageDto>();
        msg.setId(record.getId().longValue());
        msg.setType(record.getType().intValue());
        msg.setTitle(record.getTitle());
        msg.setContent(record.getContent());
        msg.setRead(false);
        msg.setCreateTime(record.getCreateTime());
        msg.setExtension(null);
        return msg;
    }
}
