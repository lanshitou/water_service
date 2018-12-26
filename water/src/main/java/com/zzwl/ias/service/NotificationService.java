package com.zzwl.ias.service;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.common.ErrorCodeException;
import com.zzwl.ias.dto.notification.NotificationAddDTO;
import com.zzwl.ias.dto.notification.NotificationQueryDTO;
import com.zzwl.ias.dto.notification.NotificationUpdateDTO;
import com.zzwl.ias.dto.notification.ReqSendNotification;
import com.zzwl.ias.vo.NotificationDetailVo;
import com.zzwl.ias.vo.NotificationListVo;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-15
 * Time: 11:31
 */
public interface NotificationService {
    /**
     * 向用户发送通知
     *
     * @param request 发送请求
     */
    void SendNotification(ReqSendNotification request);

    /**
     * 添加通知
     *
     * @param notificationAddDTO
     */
    void insertNotification(NotificationAddDTO notificationAddDTO) throws ErrorCodeException;

    /**
     * 通知列表
     *
     * @return
     */
    PageInfo<NotificationListVo> selectNotificationList(NotificationQueryDTO notificationQueryDTO);

    /**
     * 通知的用户列表
     *
     * @param notificationId
     * @return
     */
    PageInfo selectUserByNotificationId(Integer notificationId, Integer pageNum, Integer pageSize);

    /**
     * 通知详情
     *
     * @param id
     * @return
     */
    NotificationDetailVo selectNotificationDetail(Integer id);

    /**
     * 通知修改
     *
     * @param notificationUpdateDTO
     */
    void updateNotification(NotificationUpdateDTO notificationUpdateDTO);
}
