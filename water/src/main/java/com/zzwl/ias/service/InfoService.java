package com.zzwl.ias.service;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.dto.info.InfoArticleAddDTO;
import com.zzwl.ias.dto.info.InfoArticleQueryDTO;
import com.zzwl.ias.dto.info.InfoArticleUpdateDTO;
import com.zzwl.ias.dto.info.InfoCatAddDTO;
import com.zzwl.ias.vo.InfoArticleDetailVo;
import com.zzwl.ias.vo.InfoArticleListVo;
import com.zzwl.ias.vo.InfoCatVo;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/28.
 */
public interface InfoService {

    List<InfoCatVo> selectCatTree(Integer pid);

    /**
     * 添加
     *
     * @param infoCatAddDTO
     * @return
     */
    int insertInfoCat(InfoCatAddDTO infoCatAddDTO);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    int deleteInfoCat(Integer id);

    /**
     * 文章列表
     *
     * @return
     */
    PageInfo<InfoArticleListVo> selectInfoArticleList(Integer id, InfoArticleQueryDTO infoArticleQueryDTO);

    /**
     * 添加文章
     *
     * @param infoArticleAddDTO
     */
    void insertArticle(InfoArticleAddDTO infoArticleAddDTO);

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    InfoArticleDetailVo selectInfoArticleById(Integer id);

    /**
     * 文章修改
     *
     * @param infoArticleUpdateDTO
     */
    void updateInfoArticle(InfoArticleUpdateDTO infoArticleUpdateDTO);

    /**
     * 文章删除
     *
     * @param ids
     */
    void deleteInfoArticle(List<Integer> ids);
}
