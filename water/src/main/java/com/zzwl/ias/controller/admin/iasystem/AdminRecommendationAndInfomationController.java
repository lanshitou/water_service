package com.zzwl.ias.controller.admin.iasystem;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.info.RecommendationAndInfomationAddDTO;
import com.zzwl.ias.dto.info.RecommendationAndInfomationQueryDTO;
import com.zzwl.ias.service.RecommendationAndInfomationService;
import com.zzwl.ias.vo.RecommendationAndInfomationListVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.zzwl.ias.common.ErrorCode.ARTICLE_IS_ADD;
import static com.zzwl.ias.common.ErrorCode.NOT_DATA;

/**
 * Created by Lvpin on 2018/12/6.
 */
@RestController
@RequestMapping("/api/admin/recommendation_and_infomation")
public class AdminRecommendationAndInfomationController {
    @Autowired
    private RecommendationAndInfomationService recommendationAndInfomationService;

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Object insertNotification(RecommendationAndInfomationAddDTO recommendationAndInfomationAddDTO) {
        int count = recommendationAndInfomationService.InsertRecommendationAndInfomation(recommendationAndInfomationAddDTO);
        if (count != 0) {
            return Result.error(ARTICLE_IS_ADD);
        }
        return Result.ok();
    }

    /**
     * 资讯推荐列表
     *
     * @return
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Object selectRecommendationAndInfomationList(RecommendationAndInfomationQueryDTO dto) {
        PageInfo<RecommendationAndInfomationListVo> recommendationAndInfomationList = recommendationAndInfomationService.selectRecommendationAndInfomationList(dto);
        if (CollectionUtils.isEmpty(recommendationAndInfomationList.getList())) {
            return Result.error(NOT_DATA);
        }
        return Result.ok(recommendationAndInfomationList);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public Object deleteById(@PathVariable Integer id) {
        recommendationAndInfomationService.deleteById(id);
        return Result.ok();
    }
}
