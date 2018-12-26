package com.zzwl.ias.controller.admin.iasystem;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.common.ErrorCodeException;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.notification.NotificationAddDTO;
import com.zzwl.ias.dto.notification.NotificationQueryDTO;
import com.zzwl.ias.dto.notification.NotificationUpdateDTO;
import com.zzwl.ias.service.NotificationService;
import com.zzwl.ias.vo.NotificationDetailVo;
import com.zzwl.ias.vo.NotificationListVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.zzwl.ias.common.ErrorCode.NOT_DATA;

/**
 * Created by Lvpin on 2018/12/3.
 */
@RestController
@RequestMapping("/api/admin/notification")
public class AdminNotificationController {
    @Autowired
    private NotificationService notificationService;

    /**
     * 添加通知
     *
     * @param notificationAddDTO
     * @return
     */
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Object insertNotification(NotificationAddDTO notificationAddDTO) {
        try {
            notificationService.insertNotification(notificationAddDTO);
        } catch (ErrorCodeException e) {
            return Result.error(e.getErrorCode());
        }
        return Result.ok();
    }

    /**
     * 通知列表
     *
     * @param
     * @return
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Object selectNotificationList(NotificationQueryDTO notificationQueryDTO) {
        PageInfo<NotificationListVo> notificationList = notificationService.selectNotificationList(notificationQueryDTO);
        if (CollectionUtils.isEmpty(notificationList.getList())) {
            return Result.error(NOT_DATA);
        }
        return Result.ok(notificationList);
    }

    /**
     * 通知用户列表
     *
     * @param notificationId
     * @return
     */
    @RequestMapping(path = "/user/{notificationId}", method = RequestMethod.GET)
    public Object selectUserByNotificationId(@PathVariable Integer notificationId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = notificationService.selectUserByNotificationId(notificationId, pageNum, pageSize);
        if (CollectionUtils.isEmpty(pageInfo.getList())) {
            return Result.error(NOT_DATA);
        }
        return Result.ok(pageInfo);
    }

    /**
     * 通知详情
     *
     * @param notificationId
     * @return
     */
    @RequestMapping(path = "/detail/{notificationId}", method = RequestMethod.GET)
    public Object selectNotificationDetail(@PathVariable Integer notificationId) {
        NotificationDetailVo notificationDetailVo = notificationService.selectNotificationDetail(notificationId);
        return Result.ok(notificationDetailVo);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Object updateNotification(NotificationUpdateDTO notificationUpdateDTO) {
        notificationService.updateNotification(notificationUpdateDTO);
        return Result.ok();
    }
}
