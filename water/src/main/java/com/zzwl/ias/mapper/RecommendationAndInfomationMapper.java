package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.RecommendationAndInfomation;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface RecommendationAndInfomationMapper {
    @Delete({
        "delete from recommendation_and_infomation",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into recommendation_and_infomation (id, article_id, ",
        "type, create_time)",
        "values (#{id,jdbcType=INTEGER}, #{articleId,jdbcType=INTEGER}, ",
        "#{type,jdbcType=INTEGER}, #{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(RecommendationAndInfomation record);

    @InsertProvider(type=RecommendationAndInfomationSqlProvider.class, method="insertSelective")
    int insertSelective(RecommendationAndInfomation record);

    @Select({
        "select",
        "id, article_id, type, create_time",
        "from recommendation_and_infomation",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="article_id", property="articleId", jdbcType=JdbcType.INTEGER),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    RecommendationAndInfomation selectByPrimaryKey(Integer id);

    @UpdateProvider(type=RecommendationAndInfomationSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(RecommendationAndInfomation record);

    @Update({
        "update recommendation_and_infomation",
        "set article_id = #{articleId,jdbcType=INTEGER},",
          "type = #{type,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(RecommendationAndInfomation record);
}