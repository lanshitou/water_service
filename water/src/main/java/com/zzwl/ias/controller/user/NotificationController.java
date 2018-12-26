package com.zzwl.ias.controller.user;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.domain.InfoArticleDO;
import com.zzwl.ias.domain.UserNotificationDO;
import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;
import com.zzwl.ias.dto.message.MsgSummaryDTO;
import com.zzwl.ias.dto.message.ReqListMsgSummaryDTO;
import com.zzwl.ias.dto.notification.ReqSendNotification;
import com.zzwl.ias.mapper.UserNotificationDOExtMapper;
import com.zzwl.ias.service.NotificationService;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-13
 * Time: 15:34
 */

@RestController
@CrossOrigin
@RequestMapping("/v2/notifications")
public class NotificationController {


    /**
     * @return 用户通知列表
     */
    @RequiresAuthentication
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<?> listNotification(){
        List<UserNotificationDO> userNotificationDOS =
                AppContext.getBean(UserNotificationDOExtMapper.class).listUserNotification(CurrentUserUtil.getCurrentUserId());
        AssertEx.isNotEmpty(userNotificationDOS);
        return Result.ok(userNotificationDOS);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/{articleId}/read", method = RequestMethod.POST)
    public Result<?> setNotificationRead(@PathVariable Integer articleId){
        AppContext.getBean(UserNotificationDOExtMapper.class).setNotificationRead(CurrentUserUtil.getCurrentUserId(), articleId);
        return Result.ok();
    }

    /** 向用户发送通知
     * @param irrigationRequestVo 发送请求
     * @return 发送结果
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> sendNotification(@RequestBody ReqSendNotification irrigationRequestVo){
        AppContext.getBean(NotificationService.class).SendNotification(irrigationRequestVo);
        return Result.ok();
    }
}
