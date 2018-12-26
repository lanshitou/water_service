package com.zzwl.ias.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.InfoArticleDO;
import com.zzwl.ias.domain.InfoSubjectDO;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InfoPreviewDTO {

    private int id;
    private InfoContentTypeEnum type;
    private String title;
    private String img;
    private String originImg;
    private int watchCount;
    private int commentCount;
    private Date publishTime;
    private String origin;

    public InfoPreviewDTO(){}

    public InfoPreviewDTO(int id, InfoContentTypeEnum type, String title, String img, String originImg, int watchCount, int commentCount, Date publishTime, String origin) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.img = img;
        this.originImg = originImg;
        this.watchCount = watchCount;
        this.commentCount = commentCount;
        this.publishTime = publishTime;
        this.origin = origin;
    }

    public static InfoPreviewDTO fromArticleDO(InfoArticleDO articleDo) {
        return new InfoPreviewDTO(articleDo.getId(), InfoContentTypeEnum.Article, articleDo.getTitle(), articleDo.getImg(), articleDo.getOriginImg(),
                articleDo.getWatchCount().intValue(), articleDo.getCommentCount().intValue(), articleDo.getPublishTime(), articleDo.getOrigin());
    }

    public static InfoPreviewDTO fromSubjectDO(InfoSubjectDO subjectDO) {
        return new InfoPreviewDTO(subjectDO.getId().intValue(), InfoContentTypeEnum.Subject, subjectDO.getTitle(), subjectDO.getImg(), subjectDO.getOriginImg(),
                subjectDO.getWatchCount().intValue(), subjectDO.getCommentCount().intValue(), subjectDO.getPublishTime(), subjectDO.getOrigin());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InfoContentTypeEnum getType() {
        return type;
    }

    public void setType(InfoContentTypeEnum type) {
        this.type = type;
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
}