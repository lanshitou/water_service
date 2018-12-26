package com.zzwl.ias.service.info;

import com.zzwl.ias.domain.InfoArticleDO;
import com.zzwl.ias.dto.info.InfoArticleWarpDTO;
import com.zzwl.ias.dto.info.InfoPreviewDTO;
import com.zzwl.ias.dto.info.InfoPreviewWarpDTO;
import com.zzwl.ias.dto.info.InfoSubjectDTO;

import java.util.List;

public interface ClientInfo {
    //获取推荐列表
    List<InfoPreviewWarpDTO> getInfoList();

    //获取文章内容
    InfoArticleWarpDTO getArticleContent(Integer id);

    //获取文章
    InfoPreviewDTO getArticlePreview(Integer id);

    //获取专题内容
    InfoSubjectDTO getSubjectContent(Integer id);

    //举报文章
    void reportArticle(Integer articleId, String content);
}
