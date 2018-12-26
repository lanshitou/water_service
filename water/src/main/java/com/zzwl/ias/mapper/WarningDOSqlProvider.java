package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.WarningDO;
import org.apache.ibatis.jdbc.SQL;

public class WarningDOSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning
     *
     * @mbg.generated
     */
    public String insertSelective(WarningDO record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("warning");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }
        
        if (record.getSubType() != null) {
            sql.VALUES("sub_type", "#{subType,jdbcType=INTEGER}");
        }
        
        if (record.getLevel() != null) {
            sql.VALUES("level", "#{level,jdbcType=INTEGER}");
        }
        
        if (record.getCleared() != null) {
            sql.VALUES("cleared", "#{cleared,jdbcType=BIT}");
        }
        
        if (record.getProduceTime() != null) {
            sql.VALUES("produce_time", "#{produceTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getClearTime() != null) {
            sql.VALUES("clear_time", "#{clearTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getClearReason() != null) {
            sql.VALUES("clear_reason", "#{clearReason,jdbcType=INTEGER}");
        }
        
        if (record.getAddrType() != null) {
            sql.VALUES("addr_type", "#{addrType,jdbcType=INTEGER}");
        }
        
        if (record.getAddrIas() != null) {
            sql.VALUES("addr_ias", "#{addrIas,jdbcType=INTEGER}");
        }
        
        if (record.getAddrIrriFer() != null) {
            sql.VALUES("addr_irri_fer", "#{addrIrriFer,jdbcType=INTEGER}");
        }
        
        if (record.getAddrFarmland() != null) {
            sql.VALUES("addr_farmland", "#{addrFarmland,jdbcType=INTEGER}");
        }
        
        if (record.getAddrArea() != null) {
            sql.VALUES("addr_area", "#{addrArea,jdbcType=INTEGER}");
        }
        
        if (record.getAddrParentDev() != null) {
            sql.VALUES("addr_parent_dev", "#{addrParentDev,jdbcType=INTEGER}");
        }
        
        if (record.getAddrDev() != null) {
            sql.VALUES("addr_dev", "#{addrDev,jdbcType=INTEGER}");
        }
        
        if (record.getExtension() != null) {
            sql.VALUES("extension", "#{extension,jdbcType=OTHER}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(WarningDO record) {
        SQL sql = new SQL();
        sql.UPDATE("warning");
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }
        
        if (record.getSubType() != null) {
            sql.SET("sub_type = #{subType,jdbcType=INTEGER}");
        }
        
        if (record.getLevel() != null) {
            sql.SET("level = #{level,jdbcType=INTEGER}");
        }
        
        if (record.getCleared() != null) {
            sql.SET("cleared = #{cleared,jdbcType=BIT}");
        }
        
        if (record.getProduceTime() != null) {
            sql.SET("produce_time = #{produceTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getClearTime() != null) {
            sql.SET("clear_time = #{clearTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getClearReason() != null) {
            sql.SET("clear_reason = #{clearReason,jdbcType=INTEGER}");
        }
        
        if (record.getAddrType() != null) {
            sql.SET("addr_type = #{addrType,jdbcType=INTEGER}");
        }
        
        if (record.getAddrIas() != null) {
            sql.SET("addr_ias = #{addrIas,jdbcType=INTEGER}");
        }
        
        if (record.getAddrIrriFer() != null) {
            sql.SET("addr_irri_fer = #{addrIrriFer,jdbcType=INTEGER}");
        }
        
        if (record.getAddrFarmland() != null) {
            sql.SET("addr_farmland = #{addrFarmland,jdbcType=INTEGER}");
        }
        
        if (record.getAddrArea() != null) {
            sql.SET("addr_area = #{addrArea,jdbcType=INTEGER}");
        }
        
        if (record.getAddrParentDev() != null) {
            sql.SET("addr_parent_dev = #{addrParentDev,jdbcType=INTEGER}");
        }
        
        if (record.getAddrDev() != null) {
            sql.SET("addr_dev = #{addrDev,jdbcType=INTEGER}");
        }
        
        if (record.getExtension() != null) {
            sql.SET("extension = #{extension,jdbcType=OTHER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}