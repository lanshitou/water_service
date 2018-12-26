package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.CommonRegion;
import org.apache.ibatis.jdbc.SQL;

public class CommonRegionSqlProvider {

    public String insertSelective(CommonRegion record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("common_region");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=SMALLINT}");
        }
        
        if (record.getPid() != null) {
            sql.VALUES("pid", "#{pid,jdbcType=SMALLINT}");
        }
        
        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(CommonRegion record) {
        SQL sql = new SQL();
        sql.UPDATE("common_region");
        
        if (record.getPid() != null) {
            sql.SET("pid = #{pid,jdbcType=SMALLINT}");
        }
        
        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=INTEGER}");
        }
        
        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        sql.WHERE("id = #{id,jdbcType=SMALLINT}");
        
        return sql.toString();
    }
}