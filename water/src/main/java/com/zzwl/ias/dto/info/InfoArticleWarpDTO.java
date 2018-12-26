package com.zzwl.ias.dto.info;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class InfoArticleWarpDTO {
    private InfoArticleDTO article;

    private InfoPreviewWarpDTO relateArticleList;

    public InfoArticleWarpDTO(){}

    public InfoArticleWarpDTO(InfoArticleDTO article, InfoPreviewWarpDTO relateArticleList) {
        this.article = article;
        this.relateArticleList = relateArticleList;
    }


    public InfoArticleDTO getArticle() {
        return article;
    }

    public void setArticle(InfoArticleDTO article) {
        this.article = article;
    }

    public InfoPreviewWarpDTO getRelateArticleList() {
        return relateArticleList;
    }

    public void setRelateArticleList(InfoPreviewWarpDTO relateArticleList) {
        this.relateArticleList = relateArticleList;
    }
}
