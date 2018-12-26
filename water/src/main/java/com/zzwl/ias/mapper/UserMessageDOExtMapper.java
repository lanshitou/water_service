package com.zzwl.ias.mapper;

import com.zzwl.ias.dto.message.MessageDTO;
import com.zzwl.ias.dto.message.MsgSummaryDTO;
import com.zzwl.ias.dto.message.ReqListMsgSummaryDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-17
 * Time: 8:46
 */
@Component
@Mapper
public interface UserMessageDOExtMapper extends UserMessageDOMapper {
    @Select({
            "select",
            "count(*) as unread",
            "from user_message",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and verified = 0"
    })
    @Results({
            @Result(column = "unread", property = "unread", jdbcType = JdbcType.INTEGER),
    })
    Integer countUnread(Integer userId);

    @Update({
            "update user_message",
            "set verified = 1,",
            "read_time = #{readTime,jdbcType=TIMESTAMP}",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and msg_id = #{msgId,jdbcType=BIGINT}",
            "and verified = 0"
    })
    int setRead(@Param(value = "userId") Integer userId, @Param(value = "msgId")Long msgId, @Param(value = "readTime")Date readTime);

    @Select({
            "select",
            "msg_id, type, title, summary, createTime, verified",
            "from user_message, message",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and message.id = user_message.msg_id",
            "and (type = 1 or type = 2)",
            "LIMIT #{offset,jdbcType=INTEGER},#{limit,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="msg_id", property="id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
            @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="verified", property="read", jdbcType=JdbcType.TINYINT)
    })
    List<MessageDTO> listWarningMessage(@Param(value = "userId") Integer userId, @Param(value = "offset")Integer offset, @Param(value = "limit")Integer limit);

    @Select({
            "select",
            "msg_id, type, title, summary, createTime, verified",
            "from user_message, message",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and message.id = user_message.msg_id",
            "and type != 1 and type != 2",
            "LIMIT #{offset,jdbcType=INTEGER},#{limit,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="msg_id", property="id", jdbcType=JdbcType.INTEGER),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
            @Result(column="createTime", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="verified", property="read", jdbcType=JdbcType.TINYINT)
    })
    List<MessageDTO> listNormalMessage(@Param(value = "userId") Integer userId, @Param(value = "offset")Integer offset, @Param(value = "limit")Integer limit);


    @Select({
            "<script>",
            "select",
            "msg_id, type, title, summary, create_time, verified",
            "from user_message, message",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and user_message.msg_id = message.id",
            "and message.category = #{category,jdbcType=INTEGER}",
            "<if test='iasId != null'>",
            "and message.ias_id = #{iasId,jdbcType=INTEGER}",
            "</if>",
            "ORDER BY create_time DESC,msg_id DESC",
            "LIMIT #{offset,jdbcType=INTEGER},#{limit,jdbcType=INTEGER}",
            "</script>"
    })
    @Results({
            @Result(column="msg_id", property="id", jdbcType=JdbcType.BIGINT),
            @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
            @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
            @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createDate", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="verified", property="read", jdbcType=JdbcType.BIT)
    })
    List<MsgSummaryDTO> listMsgSummary(ReqListMsgSummaryDTO request);

    @Select({
            "<script>",
            "select",
            "count(msg_id) as count",
            "from user_message, message",
            "where user_message.verified = 0",
            "and user_id = #{userId,jdbcType=INTEGER}",
            "and user_message.msg_id = message.id",
            "<if test='category != null'>",
            "and message.category = #{category,jdbcType=INTEGER}",
            "</if>",
            "<if test='iasId != null'>",
            "and message.ias_id = #{iasId,jdbcType=INTEGER}",
            "</if>",
            "</script>"
    })
    Integer getUnreadMsgNum(@Param(value = "userId")Integer userId, @Param(value = "category")Integer category, @Param(value = "iasId")Integer iasId);

    @Update({
            "<script>",
            "update user_message",
            "inner join (",
            "select",
            "msg_id",
            "from user_message, message",
            "where user_message.verified = 0",
            "and user_id = #{userId,jdbcType=INTEGER}",
            "and user_message.msg_id = message.id",
            "<if test='category != null'>",
            "and message.category = #{category,jdbcType=INTEGER}",
            "</if>",
            "<if test='iasId != null'>",
            "and message.ias_id = #{iasId,jdbcType=INTEGER}",
            "</if>",
            ") R",
            "on user_message.user_id = #{userId,jdbcType=INTEGER}",
            "and user_message.msg_id = R.msg_id",
            "and user_message.verified = 0",
            "set user_message.verified = 1,",
            "read_time = #{readTime,jdbcType=TIMESTAMP}",
            "</script>"
    })
    void setAllMessageRead(@Param(value = "category")Integer category, @Param(value = "iasId")Integer iasId, @Param(value = "userId")Integer userId, @Param(value = "readTime")Date readTime);
}
