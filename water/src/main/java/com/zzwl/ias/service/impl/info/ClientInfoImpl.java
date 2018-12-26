package com.zzwl.ias.service.impl.info;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.InfoArticleDO;
import com.zzwl.ias.domain.InfoArticleReportDO;
import com.zzwl.ias.domain.InfoRecommendDO;
import com.zzwl.ias.domain.InfoSubjectCatArticleDO;
import com.zzwl.ias.domain.InfoSubjectCatDO;
import com.zzwl.ias.domain.InfoSubjectDO;
import com.zzwl.ias.dto.info.InfoArticleDTO;
import com.zzwl.ias.dto.info.InfoArticleWarpDTO;
import com.zzwl.ias.dto.info.InfoPreviewDTO;
import com.zzwl.ias.dto.info.InfoPreviewWarpDTO;
import com.zzwl.ias.dto.info.InfoSubjectDTO;
import com.zzwl.ias.dto.info.InfoWarpTypeEnum;
import com.zzwl.ias.mapper.InfoArticleExtDOMapper;
import com.zzwl.ias.mapper.InfoArticleReportDOMapper;
import com.zzwl.ias.mapper.InfoRecommendExtDOMapper;
import com.zzwl.ias.mapper.InfoSubjectExtDOMapper;
import com.zzwl.ias.service.info.ClientInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ClientInfoImpl implements ClientInfo {
    @Autowired
    InfoArticleExtDOMapper articleMapper;

    @Autowired
    InfoSubjectExtDOMapper subjectMapper;

    @Autowired
    InfoRecommendExtDOMapper recommendMapper;

    @Autowired
    InfoArticleReportDOMapper articleReportMapper;

    /**
     * 默认推荐14个
     */
    @Override
    public List<InfoPreviewWarpDTO> getInfoList() {
        List<InfoRecommendDO> recommendList = recommendMapper.findRecommendByUserId(CurrentUserUtil.getCurrentUserId());
        //至少有14条数据
        AssertEx.isTrue(recommendList.size() >= 14, ErrorCode.EMPTY);

        ArrayList<Integer> articleIds = new ArrayList<>();
        ArrayList<Integer> subjectIds = new ArrayList<>();

        for (InfoRecommendDO infoRecommendDO : recommendList) {
            if (infoRecommendDO.getSubjectId() == null || infoRecommendDO.getSubjectId() != 0) {
                articleIds.add(infoRecommendDO.getArticleId());
            } else {
                subjectIds.add(infoRecommendDO.getSubjectId().intValue());
            }
        }

        //获取文章和分类内容
        List<InfoArticleDO> articleList = articleMapper.findArticleByIdList(articleIds);
        List<InfoSubjectDO> subjectList = subjectMapper.findSubjectByIdList(subjectIds);

        //生成预览列表
        ArrayList<InfoPreviewDTO> infoList = new ArrayList<>();

        for (InfoArticleDO a : articleList) {
            infoList.add(InfoPreviewDTO.fromArticleDO(a));
        }

        for (InfoSubjectDO s : subjectList) {
            infoList.add(InfoPreviewDTO.fromSubjectDO(s));
        }

        ArrayList<InfoPreviewWarpDTO> warpList = new ArrayList<>();
        warpList.add(new InfoPreviewWarpDTO("每日推荐", InfoWarpTypeEnum.TwoInOneLine, infoList.subList(0, 6)));
        warpList.add(new InfoPreviewWarpDTO("农业资讯", InfoWarpTypeEnum.OneLineBig, infoList.subList(6, 7)));
        warpList.add(new InfoPreviewWarpDTO("", InfoWarpTypeEnum.OneLineSmall, infoList.subList(7, 8)));
        warpList.add(new InfoPreviewWarpDTO("", InfoWarpTypeEnum.OneLineSmall, infoList.subList(8, 9)));
        warpList.add(new InfoPreviewWarpDTO("", InfoWarpTypeEnum.OneLineSmall, infoList.subList(9, 10)));
        warpList.add(new InfoPreviewWarpDTO("", InfoWarpTypeEnum.OneLineSmall, infoList.subList(10, 11)));
        warpList.add(new InfoPreviewWarpDTO("其他推荐", InfoWarpTypeEnum.OneLineGallery, infoList.subList(11, 14)));

        //包装成列表
        return warpList;
    }

    /**
     * 文章内容
     * 相似推荐
     */
    @Override
    public InfoArticleWarpDTO getArticleContent(Integer id) {
        InfoArticleDO article = articleMapper.selectByPrimaryKey(id);
        AssertEx.isTrue(article != null, ErrorCode.EMPTY);
        //阅读数+1
        article.setWatchCount((short) (article.getWatchCount() + 1));
        articleMapper.updateByPrimaryKeySelective(article);

        int cropId = -1;
        int regionId = -1;

        if(article.getCropId() != null) cropId = article.getCropId();
        if(article.getRegionId() != null) regionId = article.getRegionId();

        //相似推荐relation
        List<InfoArticleDO> relationArticle = articleMapper.findRelationArticleByPidAndRegionIdAndCropId(id, article.getCatPid(), cropId, regionId);
        //如果小于3个 那么找父级的
        if (relationArticle.size() <= 2) {
            int lastIndexOf = article.getCatPid().lastIndexOf(',');
            //如果有父级
            if (lastIndexOf != -1) {
                relationArticle.addAll(
                        articleMapper.findRelationArticleByPidAndRegionIdAndCropId(id, article.getCatPid().substring(0, lastIndexOf - 1),
                                cropId, regionId)
                );
                //去重
                LinkedHashMap<Integer, InfoArticleDO> map = new LinkedHashMap<>();
                for (InfoArticleDO a : relationArticle) {
                    map.put(a.getId(), a);
                }
                relationArticle.clear();
                relationArticle.addAll(map.values());
            }
        }

        if (relationArticle.size() > 4) relationArticle = relationArticle.subList(0, 4);

        ArrayList<InfoPreviewDTO> relationArticlePreviewList = new ArrayList<>();
        for (InfoArticleDO a : relationArticle) {
            relationArticlePreviewList.add(InfoPreviewDTO.fromArticleDO(a));
        }

        //封装成返回样式
        return new InfoArticleWarpDTO(InfoArticleDTO.fromArticleDO(article),
                new InfoPreviewWarpDTO("相关推荐", InfoWarpTypeEnum.OneLineSmall, relationArticlePreviewList));
    }

    @Override
    public InfoSubjectDTO getSubjectContent(Integer id) {
        //获取专题
        InfoSubjectDO subject = subjectMapper.selectByPrimaryKey(id.shortValue());
        AssertEx.isTrue(subject != null, ErrorCode.EMPTY);
        //阅读数+1
        subject.setWatchCount((short) (subject.getWatchCount() + 1));
        subjectMapper.updateByPrimaryKeySelective(subject);
        //获取分类
        List<InfoSubjectCatDO> subjectCat = subjectMapper.findSubjectCatBySubjectId(id);
        //获取分类文章关联列表
        ArrayList<InfoPreviewWarpDTO> articleWarpList = new ArrayList<>();

        for (InfoSubjectCatDO cat: subjectCat){
            //分类下的文章id列表
            List<InfoSubjectCatArticleDO> catArticleList = subjectMapper.findInfoSubjectCatArticleDOByCatId(cat.getId().intValue(), cat.getSubjectId().intValue());
            //获取文章列表
            ArrayList<Integer> articleIds = new ArrayList<>();
            for (InfoSubjectCatArticleDO c: catArticleList){
                articleIds.add(c.getArticleId());
            }
            List<InfoArticleDO> articleList = articleMapper.findArticleByIdList(articleIds);
            ArrayList<InfoPreviewDTO> articlePreviewList = new ArrayList<>();
            //封装成返回类型
            for (InfoArticleDO a : articleList){
                articlePreviewList.add(InfoPreviewDTO.fromArticleDO(a));
            }
            articleWarpList.add(new   InfoPreviewWarpDTO(cat.getName(), InfoWarpTypeEnum.OneLineSmall,articlePreviewList));
        }

        return InfoSubjectDTO.fromSubjectDO(subject, articleWarpList);
    }

    @Override
    public void reportArticle(Integer articleId, String content) {
        InfoArticleReportDO infoArticleReportDO = new InfoArticleReportDO();
        infoArticleReportDO.setArticleId(articleId);
        infoArticleReportDO.setUserId(CurrentUserUtil.getCurrentUserId());
        infoArticleReportDO.setReason(content);
        articleReportMapper.insert(infoArticleReportDO);
    }

    @Override
    public InfoPreviewDTO getArticlePreview(Integer id) {
        InfoArticleDO infoArticleDO = articleMapper.selectByPrimaryKey(id);
        if (infoArticleDO == null){
            return null;
        }
        return InfoPreviewDTO.fromArticleDO(articleMapper.selectByPrimaryKey(id));
    }
}
