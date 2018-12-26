package com.zzwl.ias.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.ErrorCodeException;
import com.zzwl.ias.domain.InfoArticleDO;
import com.zzwl.ias.domain.UserNotificationDO;
import com.zzwl.ias.dto.notification.NotificationAddDTO;
import com.zzwl.ias.dto.notification.NotificationQueryDTO;
import com.zzwl.ias.dto.notification.NotificationUpdateDTO;
import com.zzwl.ias.dto.notification.ReqSendNotification;
import com.zzwl.ias.iasystem.message.MessageUtil;
import com.zzwl.ias.mapper.InfoArticleExtDOMapper;
import com.zzwl.ias.mapper.NotificationExtMapper;
import com.zzwl.ias.mapper.UserMapper;
import com.zzwl.ias.mapper.UserNotificationDOExtMapper;
import com.zzwl.ias.service.NotificationService;
import com.zzwl.ias.vo.NotificationDetailVo;
import com.zzwl.ias.vo.NotificationListVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-15
 * Time: 11:31
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationExtMapper notificationExtMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 向用户发送通知
     *
     * @param request 发送请求
     */
    @Override
    public void SendNotification(ReqSendNotification request) {
        //获取文章
        InfoArticleDO articleDO = AppContext.getBean(InfoArticleExtDOMapper.class).selectByPrimaryKey(request.getArticleId());
        AssertEx.isTrue(articleDO != null, ErrorCode.ARTICLE_NOT_EXIST);
        //给每个用户生成通知
        UserNotificationDO userNotificationDO = new UserNotificationDO();
        for (Integer user : request.getUsers()) {
            userNotificationDO.setUserId(user);
            userNotificationDO.setVerify(false);
            userNotificationDO.setTitle(request.getTitle());
            userNotificationDO.setSummary(request.getSummary());
            userNotificationDO.setArticleId(request.getArticleId());
            userNotificationDO.setVerify(false);
            userNotificationDO.setExpirationTime(request.getExpirationTime());
            AppContext.getBean(UserNotificationDOExtMapper.class).insert(userNotificationDO);
        }
        //向用户发送通知消息
        MessageUtil.SendNotifyMsg(request.getUsers(), userNotificationDO);
    }

    @Override
    @Transactional
    public void insertNotification(NotificationAddDTO notificationAddDTO) throws ErrorCodeException {
        notificationExtMapper.insertNotification(notificationAddDTO);
        int notificationId = notificationExtMapper.selectLastId();
        List<Integer> userIds = new ArrayList<>();
        //按选择用户添加
        if (0 == notificationAddDTO.getType()) {
            userIds = notificationAddDTO.getUserIds();
            notificationExtMapper.insertNotificationUserList(notificationAddDTO.getUserIds(), notificationId);
        } else {
            //按地址添加
            userIds = userMapper.selectUserIdsByAddressId(notificationAddDTO.getAddressId());
            if (CollectionUtils.isEmpty(userIds)) {
                throw new ErrorCodeException(ErrorCode.NOT_USER);
            }
            notificationExtMapper.insertNotificationUserList(userIds, notificationId);
        }
        //发送通知
        try {
            ReqSendNotification reqSendNotification = new ReqSendNotification();
            reqSendNotification.setArticleId(notificationAddDTO.getArticleId());
            reqSendNotification.setSummary(notificationAddDTO.getSummary());
            reqSendNotification.setTitle(notificationAddDTO.getTitle());
            reqSendNotification.setUsers(new LinkedList<>(userIds));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            reqSendNotification.setExpirationTime(format.parse(notificationAddDTO.getEndTime()));
            this.SendNotification(reqSendNotification);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageInfo<NotificationListVo> selectNotificationList(NotificationQueryDTO notificationQueryDTO) {
        PageHelper.startPage(notificationQueryDTO.getPageNum(), notificationQueryDTO.getPageSize());
        List<NotificationListVo> notificationListVos = notificationExtMapper.selectNotificationList(notificationQueryDTO);
        return new PageInfo<>(notificationListVos);
    }

    @Override
    public PageInfo selectUserByNotificationId(Integer notificationId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(notificationExtMapper.selectUserByNotificationId(notificationId));
    }

    @Override
    public NotificationDetailVo selectNotificationDetail(Integer id) {
        return notificationExtMapper.selectNotificationDetail(id);
    }

    @Override
    public void updateNotification(NotificationUpdateDTO notificationUpdateDTO) {
        notificationExtMapper.updateNotification(notificationUpdateDTO);
    }
}
