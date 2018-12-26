package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.InfoRecommendDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface InfoRecommendExtDOMapper extends InfoRecommendDOMapper {
    /**
     * 获取用户的推荐文章/专题
     */
    List<InfoRecommendDO> findRecommendByUserId(Integer id);
}
