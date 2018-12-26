package com.zzwl.ias.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface InfoCommentLikeExtDOMapper {
    /**
     * 评论点赞
     */
    @Insert({
            "insert into info_comment_like (commentId, userId) values (#{commentId}, #{userId})"
    })
    void saveCommentLike(@Param("commentId") Integer commentId, @Param("userId") Integer userId);


    /**
     * 用户是否评论了 > 0 就是评论了
     */
    @Select({
            "select count(*) as rowNumber from info_comment_like where commentId = #{commentId} and userId = #{userId}"
    })
    boolean findCommentLikeCountByCommentIdAndUserId(@Param("commentId") Integer commentId, @Param("userId") Integer userId);
}