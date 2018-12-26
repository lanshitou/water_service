package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.AlarmConfigRecord;
import com.zzwl.ias.domain.AlarmConfigRecordKey;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface AlarmConfigRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_config
     *
     * @mbg.generated
     */
    @Delete({
        "delete from alarm_config",
        "where dev_id = #{devId,jdbcType=INTEGER}",
          "and alarm_type_id = #{alarmTypeId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(AlarmConfigRecordKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_config
     *
     * @mbg.generated
     */
    @Insert({
        "insert into alarm_config (dev_id, alarm_type_id, ",
        "id, user_id, forewarn_uplimit, ",
        "forewarn_lowlimit, alarmwarn_uplimit, ",
        "alarmwarn_lowlimit)",
        "values (#{devId,jdbcType=INTEGER}, #{alarmTypeId,jdbcType=INTEGER}, ",
        "#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, #{forewarnUplimit,jdbcType=INTEGER}, ",
        "#{forewarnLowlimit,jdbcType=INTEGER}, #{alarmwarnUplimit,jdbcType=INTEGER}, ",
        "#{alarmwarnLowlimit,jdbcType=INTEGER})"
    })
    int insert(AlarmConfigRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_config
     *
     * @mbg.generated
     */
    @InsertProvider(type=AlarmConfigRecordSqlProvider.class, method="insertSelective")
    int insertSelective(AlarmConfigRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_config
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "dev_id, alarm_type_id, id, user_id, forewarn_uplimit, forewarn_lowlimit, alarmwarn_uplimit, ",
        "alarmwarn_lowlimit",
        "from alarm_config",
        "where dev_id = #{devId,jdbcType=INTEGER}",
          "and alarm_type_id = #{alarmTypeId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="dev_id", property="devId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="alarm_type_id", property="alarmTypeId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="forewarn_uplimit", property="forewarnUplimit", jdbcType=JdbcType.INTEGER),
        @Result(column="forewarn_lowlimit", property="forewarnLowlimit", jdbcType=JdbcType.INTEGER),
        @Result(column="alarmwarn_uplimit", property="alarmwarnUplimit", jdbcType=JdbcType.INTEGER),
        @Result(column="alarmwarn_lowlimit", property="alarmwarnLowlimit", jdbcType=JdbcType.INTEGER)
    })
    AlarmConfigRecord selectByPrimaryKey(AlarmConfigRecordKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_config
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AlarmConfigRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AlarmConfigRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_config
     *
     * @mbg.generated
     */
    @Update({
        "update alarm_config",
        "set id = #{id,jdbcType=INTEGER},",
          "user_id = #{userId,jdbcType=INTEGER},",
          "forewarn_uplimit = #{forewarnUplimit,jdbcType=INTEGER},",
          "forewarn_lowlimit = #{forewarnLowlimit,jdbcType=INTEGER},",
          "alarmwarn_uplimit = #{alarmwarnUplimit,jdbcType=INTEGER},",
          "alarmwarn_lowlimit = #{alarmwarnLowlimit,jdbcType=INTEGER}",
        "where dev_id = #{devId,jdbcType=INTEGER}",
          "and alarm_type_id = #{alarmTypeId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AlarmConfigRecord record);
}