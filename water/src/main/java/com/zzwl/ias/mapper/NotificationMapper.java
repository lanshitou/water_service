package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.Notification;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface NotificationMapper {
    @Delete({
        "delete from notification",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into notification (id, article_id, ",
        "title, start_time, ",
        "end_time, create_time, ",
        "type, summary)",
        "values (#{id,jdbcType=INTEGER}, #{articleId,jdbcType=INTEGER}, ",
        "#{title,jdbcType=VARCHAR}, #{startTime,jdbcType=TIMESTAMP}, ",
        "#{endTime,jdbcType=TIMESTAMP}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{type,jdbcType=INTEGER}, #{summary,jdbcType=LONGVARCHAR})"
    })
    int insert(Notification record);

    @InsertProvider(type=NotificationSqlProvider.class, method="insertSelective")
    int insertSelective(Notification record);

    @Select({
        "select",
        "id, article_id, title, start_time, end_time, create_time, type, summary",
        "from notification",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="article_id", property="articleId", jdbcType=JdbcType.INTEGER),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="summary", property="summary", jdbcType=JdbcType.LONGVARCHAR)
    })
    Notification selectByPrimaryKey(Integer id);

    @UpdateProvider(type=NotificationSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Notification record);

    @Update({
        "update notification",
        "set article_id = #{articleId,jdbcType=INTEGER},",
          "title = #{title,jdbcType=VARCHAR},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "type = #{type,jdbcType=INTEGER},",
          "summary = #{summary,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(Notification record);

    @Update({
        "update notification",
        "set article_id = #{articleId,jdbcType=INTEGER},",
          "title = #{title,jdbcType=VARCHAR},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "type = #{type,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Notification record);
}