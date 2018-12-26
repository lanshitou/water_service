package com.zzwl.ias.service.info;

import com.zzwl.ias.dto.info.CommentDTO;

import java.util.List;

public interface ClientComment {
    //获取评论
    List<CommentDTO> getArticleComment(Integer id, Integer offset, Integer limit);

    List<CommentDTO> getSubjectComment(Integer id, Integer offset, Integer limit);

    //发布评论
    CommentDTO postArticleComment(Integer id, String content);

    CommentDTO postSubjectComment(Integer id, String content);

    //点赞评论
    void likeComment(Integer commentId);
}
