package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.RecommendationAndInfomation;
import org.apache.ibatis.jdbc.SQL;

public class RecommendationAndInfomationSqlProvider {

    public String insertSelective(RecommendationAndInfomation record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("recommendation_and_infomation");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getArticleId() != null) {
            sql.VALUES("article_id", "#{articleId,jdbcType=INTEGER}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(RecommendationAndInfomation record) {
        SQL sql = new SQL();
        sql.UPDATE("recommendation_and_infomation");
        
        if (record.getArticleId() != null) {
            sql.SET("article_id = #{articleId,jdbcType=INTEGER}");
        }
        
        if (record.getType() != null) {
            sql.SET("type = #{type,jdbcType=INTEGER}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }
}