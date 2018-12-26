package com.zzwl.ias.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.domain.InfoSubjectDO;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InfoSubjectDTO {
    private int id;
    private String title;
    private String img;
    private String originImg;
    private int watchCount;
    private int commentCount;
    private Date publishTime;
    private String origin;
    private String summary;
    private List<InfoPreviewWarpDTO> content;

    public InfoSubjectDTO(){}

    public InfoSubjectDTO(int id, String title, String img, String originImg, int watchCount, int commentCount, Date publishTime, String origin, String summary, List<InfoPreviewWarpDTO> content) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.originImg = originImg;
        this.watchCount = watchCount;
        this.commentCount = commentCount;
        this.publishTime = publishTime;
        this.origin = origin;
        this.summary = summary;
        this.content = content;
    }

    public static InfoSubjectDTO fromSubjectDO(InfoSubjectDO subjectDO, List<InfoPreviewWarpDTO> content) {
        return new InfoSubjectDTO(subjectDO.getId().intValue(), subjectDO.getTitle(), subjectDO.getImg(), subjectDO.getOriginImg(),
                subjectDO.getWatchCount().intValue(), subjectDO.getCommentCount().intValue(), subjectDO.getPublishTime(), subjectDO.getOrigin(), subjectDO.getSummary(), content);
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<InfoPreviewWarpDTO> getContent() {
        return content;
    }

    public void setContent(List<InfoPreviewWarpDTO> content) {
        this.content = content;
    }
}