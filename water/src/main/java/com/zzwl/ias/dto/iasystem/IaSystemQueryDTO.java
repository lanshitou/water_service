package com.zzwl.ias.dto.iasystem;

/**
 * Created by Lvpin on 2018/11/22.
 */
public class IaSystemQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private String like;
    private Integer regionId;

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

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }
}
