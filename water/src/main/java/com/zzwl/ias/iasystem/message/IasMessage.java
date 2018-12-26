package com.zzwl.ias.iasystem.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.*;
import com.zzwl.ias.iasystem.constant.MessageConstant;
import com.zzwl.ias.mapper.MessageUserRecordExtMapper;
import com.zzwl.ias.mapper.UserMessageDOExtMapper;
import com.zzwl.ias.mapper.UserMessageDOMapper;
import com.zzwl.ias.service.MessagePushService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-17
 * Time: 8:42
 */
@Component
public class IasMessage {
    public void createAndSendMsgToUser(MessageDO msg, Object msgExt, List<Integer> users) {
        for (Integer userId : users) {
            UserMessageDO userMsg = new UserMessageDO();
            userMsg.setMsgId(msg.getId());
            userMsg.setUserId(userId);
            userMsg.setVerified(false);
            AppContext.getBean(UserMessageDOExtMapper.class).insert(userMsg);
        }
        AppContext.getBean(MessagePushService.class).
                push(msg.getTitle(), msg.getSummary(), msgExt, users);
    }
}
