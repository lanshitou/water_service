package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceOpRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface DeviceOpRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dev_op_log
     *
     * @mbg.generated
     */
    @Delete({
            "delete from dev_op_log",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dev_op_log
     *
     * @mbg.generated
     */
    @Insert({
            "insert into dev_op_log (id, dev_id, ",
            "user_id, source, ",
            "time, op_type, ",
            "auto_stop, duration, ",
            "param, result)",
            "values (#{id,jdbcType=BIGINT}, #{devId,jdbcType=BIGINT}, ",
            "#{userId,jdbcType=INTEGER}, #{source,jdbcType=INTEGER}, ",
            "#{time,jdbcType=TIMESTAMP}, #{opType,jdbcType=TINYINT}, ",
            "#{autoStop,jdbcType=BIT}, #{duration,jdbcType=INTEGER}, ",
            "#{param,jdbcType=INTEGER}, #{result,jdbcType=INTEGER})"
    })
    int insert(DeviceOpRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dev_op_log
     *
     * @mbg.generated
     */
    @InsertProvider(type = DeviceOpRecordSqlProvider.class, method = "insertSelective")
    int insertSelective(DeviceOpRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dev_op_log
     *
     * @mbg.generated
     */
    @Select({
            "select",
            "id, dev_id, user_id, source, time, op_type, auto_stop, duration, param, result",
            "from dev_op_log",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "dev_id", property = "devId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "source", property = "source", jdbcType = JdbcType.INTEGER),
            @Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "op_type", property = "opType", jdbcType = JdbcType.TINYINT),
            @Result(column = "auto_stop", property = "autoStop", jdbcType = JdbcType.BIT),
            @Result(column = "duration", property = "duration", jdbcType = JdbcType.INTEGER),
            @Result(column = "param", property = "param", jdbcType = JdbcType.INTEGER),
            @Result(column = "result", property = "result", jdbcType = JdbcType.INTEGER)
    })
    DeviceOpRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dev_op_log
     *
     * @mbg.generated
     */
    @UpdateProvider(type = DeviceOpRecordSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DeviceOpRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dev_op_log
     *
     * @mbg.generated
     */
    @Update({
            "update dev_op_log",
            "set dev_id = #{devId,jdbcType=BIGINT},",
            "user_id = #{userId,jdbcType=INTEGER},",
            "source = #{source,jdbcType=INTEGER},",
            "time = #{time,jdbcType=TIMESTAMP},",
            "op_type = #{opType,jdbcType=TINYINT},",
            "auto_stop = #{autoStop,jdbcType=BIT},",
            "duration = #{duration,jdbcType=INTEGER},",
            "param = #{param,jdbcType=INTEGER},",
            "result = #{result,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DeviceOpRecord record);
}