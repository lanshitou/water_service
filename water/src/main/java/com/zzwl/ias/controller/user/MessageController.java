package com.zzwl.ias.controller.user;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.message.*;
import com.zzwl.ias.service.MessageService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 消息
 * User: MaYong
 * Date: 2018-06-22
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /** 获取消息预览信息
     * @return 消息预览信息
     */
    @RequiresAuthentication
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public Result<?> getMessagePreview(){
        MsgPreviewDTO msgPreviewDTO = messageService.getMsgPreview(CurrentUserUtil.getCurrentUserId());
        return Result.ok(msgPreviewDTO);
    }

    /** 获取消息列表
     * @param iasId 系统ID
     * @param type 消息类型
     * @param offset 起始位置
     * @param limit 数量
     * @return 消息摘要信息列表
     */
    @RequiresAuthentication
    @RequestMapping(value = "/categories/{type}", method = RequestMethod.GET)
    public Result<?> listMessageSummary(@RequestParam(required=false) Integer iasId, @PathVariable Integer type, @RequestParam Integer offset, @RequestParam Integer limit){
        ReqListMsgSummaryDTO reqListMsgSummaryDTO = new ReqListMsgSummaryDTO(iasId, type, offset, limit);
        reqListMsgSummaryDTO.check();
        List<MsgSummaryDTO> msgSummaryDTOS = messageService.listMsgSummary(reqListMsgSummaryDTO);
        AssertEx.isNotEmpty(msgSummaryDTOS);
        return Result.ok(msgSummaryDTOS);
    }

    /** 获取消息详情
     * @param msgId 消息ID
     * @return 消息详情
     */
    @RequiresAuthentication
    @RequestMapping(value = "/{msgId}", method = RequestMethod.GET)
    public Result<?> getMessageDetail(@PathVariable Long msgId){
        Object object = messageService.getMessageDetail(msgId);
        if (object == null)
        {
            return Result.error(ErrorCode.MESSAGE_NOT_EXIST);
        }
        return Result.ok(object);
    }


    /** 获取未读消息数量
     * @param type 消息类型
     * @return 未读消息数量
     */
    @RequiresAuthentication
    @RequestMapping(value = "/categories/{type}/unread", method = RequestMethod.GET)
    public Result<?> getMessageUnreadCount(@PathVariable Integer type){
        return Result.ok(messageService.getMessageUnreadCount(type, CurrentUserUtil.getCurrentUserId()));
    }

    /** 设置未读消息为已读
     * @param type 消息类型
     * @param iasId 智慧农业系统ID
     * @return 设置的结果
     */
    @RequiresAuthentication
    @RequestMapping(value = "/categories/{type}/markAllRead", method = RequestMethod.POST)
    public Result<?> setAllMessageRead(@PathVariable Integer type, @RequestParam(required=false) Integer iasId){
        messageService.setAllMessageRead(type, iasId, CurrentUserUtil.getCurrentUserId());
        return Result.ok();
    }
}
