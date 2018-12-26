package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.UserNotificationDO;
import com.zzwl.ias.domain.UserNotificationDOKey;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-13
 * Time: 15:33
 */
@Component
@Service
public interface UserNotificationDOExtMapper extends UserNotificationDOMapper{
    @Select({
            "select",
            "article_id, verify, title, summary, create_time",
            "from user_notification",
            "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="article_id", property="articleId", jdbcType=JdbcType.INTEGER),
            @Result(column="verify", property="verify", jdbcType=JdbcType.BIT),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
    })
    List<UserNotificationDO> listUserNotification(Integer userId);

    @Update({
            "update user_notification",
            "set verify = 1",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and article_id = #{articleId,jdbcType=INTEGER}",
            "and verify = 0"
    })
    int setNotificationRead(@Param(value = "userId") Integer userId, @Param(value = "articleId")Integer articleId);
}
