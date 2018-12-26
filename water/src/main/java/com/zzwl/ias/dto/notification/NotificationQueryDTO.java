package com.zzwl.ias.dto.notification;

/**
 * Created by Lvpin on 2018/12/5.
 */
public class NotificationQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String like;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
