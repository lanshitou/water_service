package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceOpRecord;
import org.apache.ibatis.jdbc.SQL;

public class DeviceOpRecordSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dev_op_log
     *
     * @mbg.generated
     */
    public String insertSelective(DeviceOpRecord record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("dev_op_log");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getDevId() != null) {
            sql.VALUES("dev_id", "#{devId,jdbcType=BIGINT}");
        }

        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=INTEGER}");
        }

        if (record.getSource() != null) {
            sql.VALUES("source", "#{source,jdbcType=INTEGER}");
        }

        if (record.getTime() != null) {
            sql.VALUES("time", "#{time,jdbcType=TIMESTAMP}");
        }

        if (record.getOpType() != null) {
            sql.VALUES("op_type", "#{opType,jdbcType=TINYINT}");
        }

        if (record.getAutoStop() != null) {
            sql.VALUES("auto_stop", "#{autoStop,jdbcType=BIT}");
        }

        if (record.getDuration() != null) {
            sql.VALUES("duration", "#{duration,jdbcType=INTEGER}");
        }

        if (record.getParam() != null) {
            sql.VALUES("param", "#{param,jdbcType=INTEGER}");
        }

        if (record.getResult() != null) {
            sql.VALUES("result", "#{result,jdbcType=INTEGER}");
        }

        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dev_op_log
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(DeviceOpRecord record) {
        SQL sql = new SQL();
        sql.UPDATE("dev_op_log");

        if (record.getDevId() != null) {
            sql.SET("dev_id = #{devId,jdbcType=BIGINT}");
        }

        if (record.getUserId() != null) {
            sql.SET("user_id = #{userId,jdbcType=INTEGER}");
        }

        if (record.getSource() != null) {
            sql.SET("source = #{source,jdbcType=INTEGER}");
        }

        if (record.getTime() != null) {
            sql.SET("time = #{time,jdbcType=TIMESTAMP}");
        }

        if (record.getOpType() != null) {
            sql.SET("op_type = #{opType,jdbcType=TINYINT}");
        }

        if (record.getAutoStop() != null) {
            sql.SET("auto_stop = #{autoStop,jdbcType=BIT}");
        }

        if (record.getDuration() != null) {
            sql.SET("duration = #{duration,jdbcType=INTEGER}");
        }

        if (record.getParam() != null) {
            sql.SET("param = #{param,jdbcType=INTEGER}");
        }

        if (record.getResult() != null) {
            sql.SET("result = #{result,jdbcType=INTEGER}");
        }

        sql.WHERE("id = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }
}