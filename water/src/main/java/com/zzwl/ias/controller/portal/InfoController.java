package com.zzwl.ias.controller.portal;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.info.CommentDTO;
import com.zzwl.ias.service.info.ClientComment;
import com.zzwl.ias.service.info.ClientInfo;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@CrossOrigin
@RequestMapping(path = "/v2/info")
public class InfoController {
    @Autowired
    ClientInfo infoService;

    @Autowired
    ClientComment commentService;

    //-----------------------------------资讯相关
    /**
     * 资讯列表
     */
    @GetMapping("")
    @RequiresAuthentication
    public  Result<?> getInfoList()  {
        return Result.ok(infoService.getInfoList());
    }

    /**
     * 专题内容
     */
    @GetMapping("/subject/{subjectId}")
    @RequiresAuthentication
    public Result<?> getSubject(@PathVariable(required = true) int subjectId)  {
        return Result.ok(infoService.getSubjectContent(subjectId));
    }

    /**
     * 文章内容
     */
    @GetMapping("/article/{articleId}")
    @RequiresAuthentication
    public   Result<?>  getArticle(@PathVariable(required = true) int articleId) {
        return Result.ok(infoService.getArticleContent(articleId));
    }

    /**
     * 举报文章
     */
    @PostMapping("/article/{articleId}/report")
    @RequiresAuthentication
    public  Result<?> reportArticle(@PathVariable(required = true) int articleId, @RequestParam String reason)  {
        infoService.reportArticle(articleId, reason);
        return Result.ok();
    }
    //-----------------------------------评论相关
    /**
     * 文章评论列表
     */
    @GetMapping("/comment/article/{articleId}")
    @RequiresAuthentication
    public Result<?>  getArticleCommentList(@PathVariable int articleId, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit) {
        List<CommentDTO> articleComment = commentService.getArticleComment(articleId, offset, limit);
        AssertEx.isNotEmpty(articleComment);
        return Result.ok(articleComment);
    }

    /**
     * 专题评论列表
     */
    @GetMapping("/comment/subject/{subjectId}")
    @RequiresAuthentication
    public Result<?> getSubjectCommentList(@PathVariable int subjectId, @RequestParam(defaultValue = "0") int offset , @RequestParam(defaultValue = "20") int limit)  {
        List<CommentDTO> subjectComment = commentService.getSubjectComment(subjectId, offset, limit);
        AssertEx.isNotEmpty(subjectComment);
        return Result.ok(subjectComment);
    }

    /**
     * 给评论点赞
     */
    @PostMapping("/comment/{commentId}/like")
    @RequiresAuthentication
    public  Result<?> likeComment(@PathVariable int commentId)  {
        commentService.likeComment(commentId);
        return Result.ok();
    }

    /**
     * 专题下发布评论
     */
    @PostMapping("/comment/subject/{subjectId}")
    @RequiresAuthentication
    public  Result<?> postSubjectComment(@PathVariable int subjectId, @RequestParam(required = true) String comment)  {
        return Result.ok(commentService.postSubjectComment(subjectId, comment));
    }

    /**
     * 文章下发布评论
     */
    @PostMapping("/comment/article/{articleId}")
    @RequiresAuthentication
    public  Result<?>  postArticleComment(@PathVariable int articleId, @RequestParam(required = true) String comment) {
        return Result.ok(commentService.postArticleComment(articleId, comment));
    }
}