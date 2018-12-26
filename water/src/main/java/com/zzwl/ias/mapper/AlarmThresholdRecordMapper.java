package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.AlarmThresholdRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface AlarmThresholdRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_threshold
     *
     * @mbg.generated
     */
    @Delete({
            "delete from alarm_threshold",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_threshold
     *
     * @mbg.generated
     */
    @Insert({
            "insert into alarm_threshold (id, sensor_id, ",
            "data_type, alarm_type, ",
            "alarm_level, threshold, ",
            "status, occur_time, ",
            "end_reason, end_time, ",
            "user_id)",
            "values (#{id,jdbcType=INTEGER}, #{sensorId,jdbcType=BIGINT}, ",
            "#{dataType,jdbcType=INTEGER}, #{alarmType,jdbcType=INTEGER}, ",
            "#{alarmLevel,jdbcType=TINYINT}, #{threshold,jdbcType=INTEGER}, ",
            "#{status,jdbcType=TINYINT}, #{occurTime,jdbcType=TIMESTAMP}, ",
            "#{endReason,jdbcType=INTEGER}, #{endTime,jdbcType=TIMESTAMP}, ",
            "#{userId,jdbcType=INTEGER})"
    })
    int insert(AlarmThresholdRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_threshold
     *
     * @mbg.generated
     */
    @InsertProvider(type = AlarmThresholdRecordSqlProvider.class, method = "insertSelective")
    int insertSelective(AlarmThresholdRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_threshold
     *
     * @mbg.generated
     */
    @Select({
            "select",
            "id, sensor_id, data_type, alarm_type, alarm_level, threshold, status, occur_time, ",
            "end_reason, end_time, user_id",
            "from alarm_threshold",
            "where id = #{id,jdbcType=INTEGER}"
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
    AlarmThresholdRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_threshold
     *
     * @mbg.generated
     */
    @UpdateProvider(type = AlarmThresholdRecordSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AlarmThresholdRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_threshold
     *
     * @mbg.generated
     */
    @Update({
            "update alarm_threshold",
            "set sensor_id = #{sensorId,jdbcType=BIGINT},",
            "data_type = #{dataType,jdbcType=INTEGER},",
            "alarm_type = #{alarmType,jdbcType=INTEGER},",
            "alarm_level = #{alarmLevel,jdbcType=TINYINT},",
            "threshold = #{threshold,jdbcType=INTEGER},",
            "status = #{status,jdbcType=TINYINT},",
            "occur_time = #{occurTime,jdbcType=TIMESTAMP},",
            "end_reason = #{endReason,jdbcType=INTEGER},",
            "end_time = #{endTime,jdbcType=TIMESTAMP},",
            "user_id = #{userId,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AlarmThresholdRecord record);
}