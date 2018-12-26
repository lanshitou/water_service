package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceType;
import org.apache.ibatis.jdbc.SQL;

public class DeviceTypeSqlProvider {

    public String insertSelective(DeviceType record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("device_type");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getCron() != null) {
            sql.VALUES("cron", "#{cron,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(DeviceType record) {
        SQL sql = new SQL();
        sql.UPDATE("device_type");
        
        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getCron() != null) {
            sql.SET("cron = #{cron,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}