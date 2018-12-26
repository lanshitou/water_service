package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.InfoCommentDO;
import com.zzwl.ias.dto.info.CommentDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface InfoCommentExtDOMapper extends InfoCommentDOMapper {
    /**
     * 获取评论列表
     */
    @Select({
            "select a.id, a.comment, a.publishTime, a.likeCount, a.userId, count(b.id) as isLike from info_comment a",
            "left join info_comment_like b on a.id = b.commentId and a.userId = b.userId",
            "where a.${column} = #{id} group by a.id order by a.publishTime, a.likeCount",
            "limit #{offset, jdbcType=INTEGER},#{limit, jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "comment", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "publishTime", property = "publishTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "likeCount", property = "likeCount", jdbcType = JdbcType.SMALLINT),
            @Result(column = "isLike", property = "isLike", jdbcType = JdbcType.INTEGER),
            @Result(column = "userId", property = "user", one = @One(select = "com.zzwl.ias.mapper.UserBasicMapper.findUserById"))
    })
    List<CommentDTO> findCommentListById(@Param("id") Integer id, @Param("column") String column, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取一条评论
     */
    @Select({"select a.id, a.comment, a.publishTime, a.likeCount, a.userId, count(b.id) as isLike from info_comment a",
            "left join info_comment_like b on a.id = b.commentId and a.userId = b.userId",
            "where a.id = #{id} group by a.id"})
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "comment", property = "content", jdbcType = JdbcType.VARCHAR),
            @Result(column = "publishTime", property = "publishTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "likeCount", property = "likeCount", jdbcType = JdbcType.SMALLINT),
            @Result(column = "isLike", property = "isLike", jdbcType = JdbcType.INTEGER),
            @Result(column = "userId", property = "user", one = @One(select = "com.zzwl.ias.mapper.UserBasicMapper.findUserById"))
    })
    CommentDTO findCommentById(@Param("id") Integer id);

    /**
     * 插入一条数据并返回主键id
     */
    @Insert({
            "insert into info_comment (id, articleId, userId, subjectId, comment)",
            "values (#{id,jdbcType=INTEGER}, #{articleId,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER},",
            "#{subjectId,jdbcType=SMALLINT}, #{comment,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void saveAndReturnId(InfoCommentDO record);

    /**
     * 评论点赞数+1
     */
    @Update({
            "update info_comment set likeCount = likeCount + 1 where id = #{id}"
    })
    void updateCommentLikeCount(Integer id);
}