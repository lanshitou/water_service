package com.zzwl.ias.service.impl.info;

import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.domain.InfoArticleDO;
import com.zzwl.ias.domain.InfoCommentDO;
import com.zzwl.ias.domain.InfoSubjectDO;
import com.zzwl.ias.dto.info.CommentDTO;
import com.zzwl.ias.mapper.InfoArticleExtDOMapper;
import com.zzwl.ias.mapper.InfoCommentExtDOMapper;
import com.zzwl.ias.mapper.InfoCommentLikeExtDOMapper;
import com.zzwl.ias.mapper.InfoSubjectExtDOMapper;
import com.zzwl.ias.service.info.ClientComment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientCommentImpl implements ClientComment {

    @Autowired
    InfoCommentExtDOMapper commentMapper;

    @Autowired
    InfoCommentLikeExtDOMapper commentLikeMapper;

    @Autowired
    InfoArticleExtDOMapper articleMapper;

    @Autowired
    InfoSubjectExtDOMapper subjectMapper;


    @Override
    public List<CommentDTO> getArticleComment(Integer id, Integer offset, Integer limit) {
        return commentMapper.findCommentListById(id, "articleId", offset, limit);
    }

    @Override
    public List<CommentDTO> getSubjectComment(Integer id, Integer offset, Integer limit) {
        return commentMapper.findCommentListById(id, "subjectId", offset, limit);
    }

    @Override
    @Transactional
    public CommentDTO postArticleComment(Integer id, String content) {
        //评论数加1
        InfoArticleDO selectByPrimaryKey = articleMapper.selectByPrimaryKey(id);
        selectByPrimaryKey.setCommentCount((short) (selectByPrimaryKey.getCommentCount() + 1));
        articleMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
        //插入评论
        InfoCommentDO infoCommentDO = new InfoCommentDO();
        infoCommentDO.setArticleId(id);
        infoCommentDO.setComment(content);
        infoCommentDO.setUserId(CurrentUserUtil.getCurrentUserId());
        commentMapper.saveAndReturnId(infoCommentDO);
        return commentMapper.findCommentById(infoCommentDO.getId());
    }

    @Override
    @Transactional
    public CommentDTO postSubjectComment(Integer id, String content) {
        //评论数加1
        InfoSubjectDO selectByPrimaryKey = subjectMapper.selectByPrimaryKey(id.shortValue());
        selectByPrimaryKey.setCommentCount((short) (selectByPrimaryKey.getCommentCount() + 1));
        subjectMapper.updateByPrimaryKeySelective(selectByPrimaryKey);
        //插入评论
        InfoCommentDO infoCommentDO = new InfoCommentDO();
        infoCommentDO.setSubjectId(id.shortValue());
        infoCommentDO.setComment(content);
        infoCommentDO.setUserId(CurrentUserUtil.getCurrentUserId());
        commentMapper.saveAndReturnId(infoCommentDO);
        return commentMapper.findCommentById(infoCommentDO.getId());
    }

    @Override
    @Transactional
    public void likeComment(Integer commentId) {
        //如果我已经点赞过了,那就不应该再点赞了
        if (commentLikeMapper.findCommentLikeCountByCommentIdAndUserId(commentId, CurrentUserUtil.getCurrentUserId())) return;
        commentMapper.updateCommentLikeCount(commentId);
        commentLikeMapper.saveCommentLike(commentId, CurrentUserUtil.getCurrentUserId());
    }
}
