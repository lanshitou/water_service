package com.zzwl.ias.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.vo.UserBasicInfoVo;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentDTO {
    private UserBasicInfoVo user;  //用户信息
    private String content; //评论内容
    private int id; //评论ID
    private Date publishTime; //发布时间
    private int likeCount; //点赞人数
    private Boolean isLike;  //我是否点赞了该评论

    public CommentDTO(){}

    public CommentDTO(UserBasicInfoVo user, String content, int id, Date publishTime, int likeCount, Boolean isLike) {
        this.user = user;
        this.content = content;
        this.id = id;
        this.publishTime = publishTime;
        this.likeCount = likeCount;
        this.isLike = isLike;
    }

    public UserBasicInfoVo getUser() {
        return user;
    }

    public void setUser(UserBasicInfoVo user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }
}