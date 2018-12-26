package com.zzwl.ias.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzwl.ias.dto.info.RecommendationAndInfomationAddDTO;
import com.zzwl.ias.dto.info.RecommendationAndInfomationQueryDTO;
import com.zzwl.ias.mapper.RecommendationAndInfomationExtMapper;
import com.zzwl.ias.service.RecommendationAndInfomationService;
import com.zzwl.ias.vo.RecommendationAndInfomationListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Lvpin on 2018/12/6.
 */
@Service
public class RecommendationAndInfomationImpl implements RecommendationAndInfomationService {
    @Autowired
    RecommendationAndInfomationExtMapper recommendationAndInfomationExtMapper;

    @Override
    public int InsertRecommendationAndInfomation(RecommendationAndInfomationAddDTO dto) {
        int count = recommendationAndInfomationExtMapper.selectByArticleIdAndType(dto.getArticleId(), dto.getType());
        if (count == 0) {
            recommendationAndInfomationExtMapper.insertRecommendationAndInfomation(dto);
        }
        return count;
    }

    @Override
    public PageInfo<RecommendationAndInfomationListVo> selectRecommendationAndInfomationList(RecommendationAndInfomationQueryDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(recommendationAndInfomationExtMapper.selectRecommendationAndInfomationList(dto));
    }

    @Override
    public void deleteById(Integer id) {
        recommendationAndInfomationExtMapper.deleteById(id);
    }
}
