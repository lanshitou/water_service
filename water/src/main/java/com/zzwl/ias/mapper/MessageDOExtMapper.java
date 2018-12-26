package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.MessageDO;
import com.zzwl.ias.mapper.MessageDOMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-17
 * Time: 8:32
 */
@Component
@Mapper
public interface MessageDOExtMapper extends MessageDOMapper {
    @Select({
            "select",
            "message.id as id, ias_id, category, type, title, summary, create_time, extension",
            "from user_message, message",
            "where user_message.user_id = #{userId,jdbcType=INTEGER}",
            "and user_message.msg_id = #{id,jdbcType=BIGINT}",
            "and message.id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.BIGINT, id=true),
            @Result(column="ias_id", property="iasId", jdbcType=JdbcType.INTEGER),
            @Result(column="category", property="category", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="extension", property="extension", jdbcType=JdbcType.OTHER)
    })
    MessageDO getUserMsg(@Param(value = "userId") Integer userId, @Param(value = "id")Long id);

    @Insert({
            "insert into message (id, ias_id, ",
            "category, type, ",
            "title, summary, ",
            "create_time, extension)",
            "values (#{id,jdbcType=BIGINT}, #{iasId,jdbcType=INTEGER}, ",
            "#{category,jdbcType=INTEGER}, #{type,jdbcType=INTEGER}, ",
            "#{title,jdbcType=VARCHAR}, #{summary,jdbcType=VARCHAR}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{extension,jdbcType=OTHER})"
    })
    @Options(useGeneratedKeys = true)
    int insert(MessageDO record);
}
