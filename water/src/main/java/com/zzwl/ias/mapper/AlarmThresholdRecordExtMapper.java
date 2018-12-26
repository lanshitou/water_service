package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.AlarmThresholdRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-13
 * Time: 14:45
 */
@Component
@Mapper
public interface AlarmThresholdRecordExtMapper extends AlarmThresholdRecordMapper {
    @Select({
            "select",
            "id, sensor_id, data_type, alarm_type, alarm_level, threshold, status, occur_time, ",
            "end_reason, end_time, user_id",
            "from alarm_threshold",
            "where sensor_id = #{sensorId,jdbcType=BIGINT}",
            "and data_type = #{dataType,jdbcType=INTEGER}",
            "and status = 1"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "sensor_id", property = "sensorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "data_type", property = "dataType", jdbcType = JdbcType.INTEGER),
            @Result(column = "alarm_type", property = "alarmType", jdbcType = JdbcType.INTEGER),
            @Result(column = "alarm_level", property = "alarmLevel", jdbcType = JdbcType.TINYINT),
            @Result(column = "threshold", property = "threshold", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.TINYINT),
            @Result(column = "occur_time", property = "occurTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "end_reason", property = "endReason", jdbcType = JdbcType.INTEGER),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER)
    })
    AlarmThresholdRecord selectCurrentAlarmBySensorIdAndType(@Param(value = "sensorId") Long sensorId, @Param(value = "dataType") Integer dataType);

    @Select({
            "<script>",
            "select",
            "id, sensor_id, data_type, alarm_type, alarm_level, threshold, status, occur_time, ",
            "end_reason, end_time, user_id",
            "from alarm_threshold",
            "where status = #{status,jdbcType=INTEGER}",
            "and sensor_id in",
            "<foreach collection='sensorIds' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "order by occur_time DESC",
            "<if test='limit > 0'>",
            "LIMIT #{offset},#{limit}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "sensor_id", property = "sensorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "data_type", property = "dataType", jdbcType = JdbcType.INTEGER),
            @Result(column = "alarm_type", property = "alarmType", jdbcType = JdbcType.INTEGER),
            @Result(column = "alarm_level", property = "alarmLevel", jdbcType = JdbcType.TINYINT),
            @Result(column = "threshold", property = "threshold", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.TINYINT),
            @Result(column = "occur_time", property = "occurTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "end_reason", property = "endReason", jdbcType = JdbcType.INTEGER),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER)
    })
    List<AlarmThresholdRecord> selectByStatus(
            @Param(value = "sensorIds") List<Long> sensorIds,
            @Param(value = "status") Integer status,
            @Param(value = "offset") Integer offset,
            @Param(value = "limit") Integer limit);

    @Select({
            "<script>",
            "select",
            "count(*)",
            "from alarm_threshold",
            "where status = #{status,jdbcType=INTEGER}",
            "and sensor_id in",
            "<foreach collection='sensorIds' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "sensor_id", property = "sensorId", jdbcType = JdbcType.BIGINT),
            @Result(column = "data_type", property = "dataType", jdbcType = JdbcType.INTEGER),
            @Result(column = "alarm_type", property = "alarmType", jdbcType = JdbcType.INTEGER),
            @Result(column = "alarm_level", property = "alarmLevel", jdbcType = JdbcType.TINYINT),
            @Result(column = "threshold", property = "threshold", jdbcType = JdbcType.INTEGER),
            @Result(column = "status", property = "status", jdbcType = JdbcType.TINYINT),
            @Result(column = "occur_time", property = "occurTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "end_reason", property = "endReason", jdbcType = JdbcType.INTEGER),
            @Result(column = "end_time", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER)
    })
    Integer countWarning(@Param(value = "sensorIds") List<Long> sensorIds, @Param(value = "status") Integer status);
}
