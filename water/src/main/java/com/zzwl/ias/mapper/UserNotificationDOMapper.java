package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.UserNotificationDO;
import com.zzwl.ias.domain.UserNotificationDOKey;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface UserNotificationDOMapper {
    
    @Delete({
        "delete from user_notification",
        "where user_id = #{userId,jdbcType=INTEGER}",
          "and article_id = #{articleId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(UserNotificationDOKey key);

    
    @Insert({
        "insert into user_notification (user_id, article_id, ",
        "verify, title, summary, ",
        "create_time, expiration_time)",
        "values (#{userId,jdbcType=INTEGER}, #{articleId,jdbcType=INTEGER}, ",
        "#{verify,jdbcType=BIT}, #{title,jdbcType=VARCHAR}, #{summary,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{expirationTime,jdbcType=TIMESTAMP})"
    })
    int insert(UserNotificationDO record);

    
    @InsertProvider(type=UserNotificationDOSqlProvider.class, method="insertSelective")
    int insertSelective(UserNotificationDO record);

    
    @Select({
        "select",
        "user_id, article_id, verify, title, summary, create_time, expiration_time",
        "from user_notification",
        "where user_id = #{userId,jdbcType=INTEGER}",
          "and article_id = #{articleId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="article_id", property="articleId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="verify", property="verify", jdbcType=JdbcType.BIT),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="summary", property="summary", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="expiration_time", property="expirationTime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserNotificationDO selectByPrimaryKey(UserNotificationDOKey key);

    
    @UpdateProvider(type=UserNotificationDOSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserNotificationDO record);

    
    @Update({
        "update user_notification",
        "set verify = #{verify,jdbcType=BIT},",
          "title = #{title,jdbcType=VARCHAR},",
          "summary = #{summary,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "expiration_time = #{expirationTime,jdbcType=TIMESTAMP}",
        "where user_id = #{userId,jdbcType=INTEGER}",
          "and article_id = #{articleId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserNotificationDO record);
}