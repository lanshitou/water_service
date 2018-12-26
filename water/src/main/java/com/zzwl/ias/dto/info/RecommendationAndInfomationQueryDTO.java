package com.zzwl.ias.dto.info;

/**
 * Created by Lvpin on 2018/12/6.
 */
public class RecommendationAndInfomationQueryDTO {
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
