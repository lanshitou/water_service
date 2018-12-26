package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.InfoSubjectCatArticleDO;
import com.zzwl.ias.domain.InfoSubjectCatDO;
import com.zzwl.ias.domain.InfoSubjectDO;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface InfoSubjectExtDOMapper extends InfoSubjectDOMapper {
    /**
     * 查询专题下的分类
     */
    List<InfoSubjectCatDO> findSubjectCatBySubjectId(Integer id);

    /**
     * 查询分类下关联的文章id
     */
    List<InfoSubjectCatArticleDO> findInfoSubjectCatArticleDOByCatId(@Param("catId") Integer catId, @Param("subjectId") Integer subjectId);

    /**
     * 根据id获取专题列表
     */
    List<InfoSubjectDO> findSubjectByIdList(@Param("ids") List<Integer> ids);
}
