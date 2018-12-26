package com.zzwl.ias.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-11
 * Time: 15:08
 */
@Component
@Mapper
public interface MessageUserRecordExtMapper extends MessageUserRecordMapper {
    @Select({
            "select",
            "count(*) as unread",
            "from message_user",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and is_read = 0"
    })
    @Results({
            @Result(column = "unread", property = "unread", jdbcType = JdbcType.INTEGER),
    })
    Integer countUnread(Integer userId);

    @Update({
            "update message_user",
            "set is_read = 1,",
            "where msg_id = #{msgId,jdbcType=INTEGER}",
            "and user_id = #{userId,jdbcType=INTEGER}"
    })
    int setMessageRead(@Param(value = "userId") Integer userId, @Param(value = "msgId") Integer msgId);
}
