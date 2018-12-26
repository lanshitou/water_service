package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DataCollectionRecord;
import org.apache.ibatis.jdbc.SQL;

public class DataCollectionRecordSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collections
     *
     * @mbg.generated
     */
    public String insertSelective(DataCollectionRecord record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("data_collections");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getSensorId() != null) {
            sql.VALUES("sensor_id", "#{sensorId,jdbcType=BIGINT}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }
        
        if (record.getValue() != null) {
            sql.VALUES("value", "#{value,jdbcType=INTEGER}");
        }
        
        if (record.getTime() != null) {
            sql.VALUES("time", "#{time,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCollectionType() != null) {
            sql.VALUES("collection_type", "#{collectionType,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table data_collections
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(DataCollectionRecord record) {
        SQL sql = new SQL();
        sql.UPDATE("data_collections");
        
        if (record.getSensorId() != null) {
            sql.SET("sensor_id = #{sensorId,jdbcType=BIGINT}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }
        
        if (record.getValue() != null) {
            sql.SET("value = #{value,jdbcType=INTEGER}");
        }
        
        if (record.getTime() != null) {
            sql.SET("time = #{time,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCollectionType() != null) {
            sql.SET("collection_type = #{collectionType,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}