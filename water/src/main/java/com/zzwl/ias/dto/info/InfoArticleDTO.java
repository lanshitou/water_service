package com.zzwl.ias.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.InfoArticleDO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InfoArticleDTO {
    private int id;
    private String title;
    private String img;
    private String originImg;
    private int watchCount;
    private int commentCount;
    private Date publishTime;
    private String origin;
    private List<String> tag;
    private String htmlContent;

    public InfoArticleDTO(){}

    public InfoArticleDTO(int id, String title, String img, String originImg, int watchCount, int commentCount, Date publishTime, String origin, List<String> tag, String htmlContent) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.originImg = originImg;
        this.watchCount = watchCount;
        this.commentCount = commentCount;
        this.publishTime = publishTime;
        this.origin = origin;
        this.tag = tag;
        this.htmlContent = htmlContent;
    }

    public static InfoArticleDTO fromArticleDO(InfoArticleDO articleDo) {
        ArrayList<String> tagList = new ArrayList<>();
        if (!articleDo.getTag().isEmpty()) {
            tagList.addAll(Arrays.asList(articleDo.getTag().split(",")));
        }

        return new InfoArticleDTO(articleDo.getId(), articleDo.getTitle(), articleDo.getImg(), articleDo.getOriginImg(),
                articleDo.getWatchCount().intValue(), articleDo.getCommentCount().intValue(), articleDo.getPublishTime(),
                articleDo.getOrigin(), tagList, articleDo.getHtmlContent());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOriginImg() {
        return originImg;
    }

    public void setOriginImg(String originImg) {
        this.originImg = originImg;
    }

    public int getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(int watchCount) {
        this.watchCount = watchCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

}