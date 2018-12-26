package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.MessageAlarmRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-11
 * Time: 14:03
 */
@Component
@Mapper
public interface MessageAlarmRecordExtMapper extends MessageAlarmRecordMapper {
    @Select({
            "select",
            "m.id, m.sersor_id, m.title, m.content, m.is_read, m.alarm_threshold_id, m.data_type, m.alarm_type, ",
            "m.level, m.status, m.threshold, m.occur_time, m.end_time, m.end_reason",
            "from message_alarm m, message_user u",
            "where m.id = u.msg_id",
            "and u.user_id = #{userId, jdbcType=INTEGER}",
            "and u.type = 3",
            "order by occur_time DESC, end_time DESC",
            "limit #{offset, jdbcType=INTEGER},#{limit, jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "sersor_id", property = "sersorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "content", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "is_read", property = "isRead", jdbcType = JdbcType.TINYINT),
            @Result(column = "alarm_threshold_id", property = "alarmThresholdId", jdbcType = JdbcType.INTEGER),
            @Result(column = "data_type", property = "dataType", jdbcType = JdbcType.INTEGER),
            @Result(column = "alarm_type", property = "alarmType", jdbcType = JdbcType.TINYINT),
            @Result(column = "level", property = "level", jdbcType = JdbcType.TINYINT),
            @Result(column = "status", property = "status", jdbcType = JdbcType.INTEGER),
            @Result(column = "threshold", property = "threshold", jdbcType = JdbcType.INTEGER),
            @Result(column = "occur_time", property = "occurTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "end_reason", property = "endReason", jdbcType = JdbcType.INTEGER)
    })
    List<MessageAlarmRecord> selectByUser(@Param(value = "offset") Integer offset,
                                          @Param(value = "limit") Integer limit,
                                          @Param(value = "userId") Integer userId);
}
