package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.InfoRecommendDO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface InfoRecommendDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_recommend
     *
     * @mbg.generated
     */
    @Delete({
        "delete from info_recommend",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_recommend
     *
     * @mbg.generated
     */
    @Insert({
        "insert into info_recommend (id, userId, ",
        "articleId, subjectId)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{articleId,jdbcType=INTEGER}, #{subjectId,jdbcType=SMALLINT})"
    })
    int insert(InfoRecommendDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_recommend
     *
     * @mbg.generated
     */
    @InsertProvider(type=InfoRecommendDOSqlProvider.class, method="insertSelective")
    int insertSelective(InfoRecommendDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_recommend
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, userId, articleId, subjectId",
        "from info_recommend",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="userId", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="articleId", property="articleId", jdbcType=JdbcType.INTEGER),
        @Result(column="subjectId", property="subjectId", jdbcType=JdbcType.SMALLINT)
    })
    InfoRecommendDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_recommend
     *
     * @mbg.generated
     */
    @UpdateProvider(type=InfoRecommendDOSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(InfoRecommendDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_recommend
     *
     * @mbg.generated
     */
    @Update({
        "update info_recommend",
        "set userId = #{userId,jdbcType=INTEGER},",
          "articleId = #{articleId,jdbcType=INTEGER},",
          "subjectId = #{subjectId,jdbcType=SMALLINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(InfoRecommendDO record);
}