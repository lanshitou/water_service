package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.AlarmConfigRecord;
import com.zzwl.ias.domain.AlarmConfigRecordKey;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-15
 * Time: 14:30
 */
@Component
@Mapper
public interface AlarmConfigRecordExtMapper extends AlarmConfigRecordMapper {
    @Select({
            "select",
            "dev_id, alarm_type_id, id, user_id, forewarn_uplimit, forewarn_lowlimit, alarmwarn_uplimit, ",
            "alarmwarn_lowlimit",
            "from alarm_config",
            "where dev_id = #{devId,jdbcType=BIGINT}",
            "and alarm_type_id = #{alarmTypeId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="dev_id", property="devId", jdbcType= JdbcType.INTEGER, id=true),
            @Result(column="alarm_type_id", property="alarmTypeId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
            @Result(column="forewarn_uplimit", property="forewarnUplimit", jdbcType=JdbcType.INTEGER),
            @Result(column="forewarn_lowlimit", property="forewarnLowlimit", jdbcType=JdbcType.INTEGER),
            @Result(column="alarmwarn_uplimit", property="alarmwarnUplimit", jdbcType=JdbcType.INTEGER),
            @Result(column="alarmwarn_lowlimit", property="alarmwarnLowlimit", jdbcType=JdbcType.INTEGER)
    })
    AlarmConfigRecord selectByDevIdAndType(@Param(value = "devId") Integer devId, @Param(value = "alarmTypeId")Integer type);

    @Insert({
            "insert into alarm_config (dev_id, alarm_type_id, ",
            "id, user_id, forewarn_uplimit, ",
            "forewarn_lowlimit, alarmwarn_uplimit, ",
            "alarmwarn_lowlimit)",
            "values (#{devId,jdbcType=INTEGER}, #{alarmTypeId,jdbcType=INTEGER}, ",
            "#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{forewarnUplimit,jdbcType=INTEGER}, ",
            "#{forewarnLowlimit,jdbcType=INTEGER}, #{alarmwarnUplimit,jdbcType=INTEGER}, ",
            "#{alarmwarnLowlimit,jdbcType=INTEGER})",
            "on duplicate key update",
            "alarmwarn_uplimit=#{alarmwarnUplimit,jdbcType=INTEGER},",
            "alarmwarn_lowlimit=#{alarmwarnLowlimit,jdbcType=INTEGER}"
    })
    int upsert(AlarmConfigRecord record);
}
