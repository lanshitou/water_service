package com.zzwl.ias.dto.info;

/**
 * Created by Lvpin on 2018/12/6.
 */
public class RecommendationAndInfomationAddDTO {
    private Integer articleId;
    //0:每日推荐 1:农业资讯
    private Integer type;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
