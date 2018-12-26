package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.InfoCommentLikeDO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface InfoCommentLikeDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_comment_like
     *
     * @mbg.generated
     */
    @Delete({
        "delete from info_comment_like",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_comment_like
     *
     * @mbg.generated
     */
    @Insert({
        "insert into info_comment_like (id, commentId, ",
        "userId)",
        "values (#{id,jdbcType=INTEGER}, #{commentId,jdbcType=INTEGER}, ",
        "#{userId,jdbcType=INTEGER})"
    })
    int insert(InfoCommentLikeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_comment_like
     *
     * @mbg.generated
     */
    @InsertProvider(type=InfoCommentLikeDOSqlProvider.class, method="insertSelective")
    int insertSelective(InfoCommentLikeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_comment_like
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, commentId, userId",
        "from info_comment_like",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="commentId", property="commentId", jdbcType=JdbcType.INTEGER),
        @Result(column="userId", property="userId", jdbcType=JdbcType.INTEGER)
    })
    InfoCommentLikeDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_comment_like
     *
     * @mbg.generated
     */
    @UpdateProvider(type=InfoCommentLikeDOSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(InfoCommentLikeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table info_comment_like
     *
     * @mbg.generated
     */
    @Update({
        "update info_comment_like",
        "set commentId = #{commentId,jdbcType=INTEGER},",
          "userId = #{userId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(InfoCommentLikeDO record);
}