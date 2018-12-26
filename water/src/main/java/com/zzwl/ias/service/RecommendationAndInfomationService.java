package com.zzwl.ias.service;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.dto.info.RecommendationAndInfomationAddDTO;
import com.zzwl.ias.dto.info.RecommendationAndInfomationQueryDTO;
import com.zzwl.ias.vo.RecommendationAndInfomationListVo;

/**
 * Created by Lvpin on 2018/12/6.
 */
public interface RecommendationAndInfomationService {
    /**
     * 添加资讯推荐
     *
     * @param recommendationAndInfomationAddDTO
     */
    int InsertRecommendationAndInfomation(RecommendationAndInfomationAddDTO recommendationAndInfomationAddDTO);

    /**
     * 资讯列表
     *
     * @return
     */
    PageInfo<RecommendationAndInfomationListVo> selectRecommendationAndInfomationList(RecommendationAndInfomationQueryDTO dto);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Integer id);
}
