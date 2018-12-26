package com.zzwl.ias.controller.admin.iasystem;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.info.InfoArticleAddDTO;
import com.zzwl.ias.dto.info.InfoArticleQueryDTO;
import com.zzwl.ias.dto.info.InfoArticleUpdateDTO;
import com.zzwl.ias.dto.info.InfoCatAddDTO;
import com.zzwl.ias.service.InfoService;
import com.zzwl.ias.vo.InfoArticleDetailVo;
import com.zzwl.ias.vo.InfoArticleListVo;
import com.zzwl.ias.vo.InfoCatVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zzwl.ias.common.ErrorCode.EXIST_ARTICLE_CAT;
import static com.zzwl.ias.common.ErrorCode.NO_SUBGROUPS;

/**
 * Created by Lvpin on 2018/11/28.
 */
@RestController
@RequestMapping("/api/admin/info")
public class AdminInfoController {
    @Autowired
    private InfoService infoService;

    @RequestMapping(path = "/cat/{pid}", method = RequestMethod.GET)
    public Object selectInfoCat(@PathVariable Integer pid) {
        List<InfoCatVo> infoCatVos = infoService.selectCatTree(pid);
        if (CollectionUtils.isEmpty(infoCatVos)) {
            return Result.error(NO_SUBGROUPS);
        }
        return Result.ok(infoCatVos);
    }

    /**
     * 添加分类
     *
     * @param infoCatAddDTO
     * @return
     */
    @RequestMapping(path = "/cat", method = RequestMethod.PUT)
    public Object selectInfoCat(InfoCatAddDTO infoCatAddDTO) {
        int id = infoService.insertInfoCat(infoCatAddDTO);
        return Result.ok(id);
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */

    @RequestMapping(path = "/cat/{id}", method = RequestMethod.DELETE)
    public Object deleteInfoCat(@PathVariable Integer id) {
        int count = infoService.deleteInfoCat(id);
        if (count != 0) {
            return Result.error(EXIST_ARTICLE_CAT);
        }
        return Result.ok();
    }

    /**
     * 文章列表
     *
     * @param id
     * @param infoArticleQueryDTO
     * @return
     */
    @RequestMapping(path = "/article/{id}", method = RequestMethod.GET)
    public Object selectInfoArticleList(@PathVariable Integer id, InfoArticleQueryDTO infoArticleQueryDTO) {
        PageInfo<InfoArticleListVo> infoArticleListVoPageInfo = infoService.selectInfoArticleList(id, infoArticleQueryDTO);
        return Result.ok(infoArticleListVoPageInfo);
    }

    /**
     * 添加文章
     *
     * @param infoArticleAddDTO
     * @return
     */
    @RequestMapping(path = "/article", method = RequestMethod.PUT)
    public Object insertArticle(InfoArticleAddDTO infoArticleAddDTO) {
        infoService.insertArticle(infoArticleAddDTO);
        return Result.ok();
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/article/detail/{id}", method = RequestMethod.GET)
    public Object selectInfoArticleById(@PathVariable Integer id) {
        InfoArticleDetailVo infoArticleDetailVo = infoService.selectInfoArticleById(id);
        return Result.ok(infoArticleDetailVo);
    }

    /**
     * 文章修改
     *
     * @param infoArticleUpdateDTO
     * @return
     */
    @RequestMapping(path = "/article", method = RequestMethod.POST)
    public Object updateInfoArticle(InfoArticleUpdateDTO infoArticleUpdateDTO) {
        infoService.updateInfoArticle(infoArticleUpdateDTO);
        return Result.ok();
    }

    @RequestMapping(path = "/article/delete", method = RequestMethod.POST)
    public Object deleteInfoArticle(@RequestParam("ids") List<Integer> ids) {
        infoService.deleteInfoArticle(ids);
        return Result.ok();
    }

}
