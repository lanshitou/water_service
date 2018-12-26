package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.MessageSysRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-15
 * Time: 7:58
 */
@Component
@Mapper
public interface MessageSysRecordExtMapper extends MessageSysRecordMapper{
    @Select({
            "select",
            "message_sys.id, title, message_sys.type, content, url, create_time",
            "from message_sys, message_user",
            "where message_user.id = #{userId,jdbcType=INTEGER}",
            "and message_user.msg_id = message_sys.id",
            "and message_user.type = 1",
            "LIMIT #{offset,jdbcType=INTEGER},#{limit,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.TINYINT),
            @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
            @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<MessageSysRecord> selectByPage(@Param(value = "offset") int offset, @Param(value = "limit") int limit, @Param(value = "userId") int userId);
}
