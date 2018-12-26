package com.zzwl.ias.service.impl.info;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzwl.ias.dto.info.InfoArticleAddDTO;
import com.zzwl.ias.dto.info.InfoArticleQueryDTO;
import com.zzwl.ias.dto.info.InfoArticleUpdateDTO;
import com.zzwl.ias.dto.info.InfoCatAddDTO;
import com.zzwl.ias.mapper.InfoArticleExtDOMapper;
import com.zzwl.ias.mapper.InfoCatExtMapper;
import com.zzwl.ias.service.InfoService;
import com.zzwl.ias.vo.InfoArticleDetailVo;
import com.zzwl.ias.vo.InfoArticleListVo;
import com.zzwl.ias.vo.InfoCatVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/28.
 */
@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoCatExtMapper infoCatExtMapper;
    @Autowired
    private InfoArticleExtDOMapper infoArticleExtDOMapper;

    @Override
    public List<InfoCatVo> selectCatTree(Integer pid) {
        return infoCatExtMapper.selectCatTree(pid);
    }

    /**
     * 添加
     *
     * @param infoCatAddDTO
     * @return
     */
    @Override
    public int insertInfoCat(InfoCatAddDTO infoCatAddDTO) {
        infoCatExtMapper.insertInfoCat(infoCatAddDTO);
        return infoCatExtMapper.selectLastId();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteInfoCat(Integer id) {
        int count = infoArticleExtDOMapper.selectCountByCatId(id);
        if (count == 0) {
            count = infoCatExtMapper.selectCountByPid(id);
            if (count == 0) {
                infoCatExtMapper.deleteInfoCat(id);
            }
        }
        return count;
    }

    @Override
    public PageInfo<InfoArticleListVo> selectInfoArticleList(Integer id, InfoArticleQueryDTO infoArticleQueryDTO) {
        PageHelper.startPage(infoArticleQueryDTO.getPageNum(), infoArticleQueryDTO.getPageSize());
        return new PageInfo<>(infoArticleExtDOMapper.selectInfoArticleList(id));
    }

    @Override
    @Transactional
    public void insertArticle(InfoArticleAddDTO infoArticleAddDTO) {
        infoArticleExtDOMapper.insertInfoArticle(infoArticleAddDTO);
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @Override
    public InfoArticleDetailVo selectInfoArticleById(Integer id) {
        return infoArticleExtDOMapper.selectInfoArticleById(id);
    }

    /**
     * 文章修改
     *
     * @param infoArticleUpdateDTO
     */
    @Override
    public void updateInfoArticle(InfoArticleUpdateDTO infoArticleUpdateDTO) {
        infoArticleExtDOMapper.updateInfoArticle(infoArticleUpdateDTO);
    }

    /**
     * 文章删除
     *
     * @param ids
     */
    @Override
    public void deleteInfoArticle(List<Integer> ids) {
        infoArticleExtDOMapper.deleteInfoArticle(ids);
    }

}
