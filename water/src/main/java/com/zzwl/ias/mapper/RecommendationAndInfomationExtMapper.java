package com.zzwl.ias.mapper;

import com.zzwl.ias.dto.info.RecommendationAndInfomationAddDTO;
import com.zzwl.ias.dto.info.RecommendationAndInfomationQueryDTO;
import com.zzwl.ias.vo.RecommendationAndInfomationListVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Lvpin on 2018/12/6.
 */
@Component
@Mapper
public interface RecommendationAndInfomationExtMapper extends RecommendationAndInfomationMapper {
    /**
     * 添加
     *
     * @param recommendationAndInfomationAddDTO
     */
    @Insert({
            "insert into recommendation_and_infomation (article_id, type)",
            "values (#{articleId}, #{type})"
    })
    void insertRecommendationAndInfomation(RecommendationAndInfomationAddDTO recommendationAndInfomationAddDTO);

    /**
     * 是否已添加
     *
     * @param articleId
     * @param type
     * @return
     */
    @Select({
            "SELECT COUNT(*) FROM recommendation_and_infomation rai WHERE rai.article_id = #{articleId} AND rai.type = #{type}"
    })
    int selectByArticleIdAndType(@Param("articleId") Integer articleId, @Param("type") Integer type);

    /**
     * 列表
     *
     * @param dto
     * @return
     */
    @Select({
            "<script>SELECT rai.id rid,rai.type,ia.id,ia.title,DATE_FORMAT(create_time, '%Y-%m-%d %H:%i:%S') create_time ",
            "FROM recommendation_and_infomation rai ",
            "LEFT JOIN info_article ia ON ia.id = rai.article_id" +
                    "<where><if test=\"type != null\">rai.type=#{type}</if></where>order by create_time desc </script>"
    })
    @Results({
            @Result(column = "rid", property = "rid", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.VARCHAR)
    })
    List<RecommendationAndInfomationListVo> selectRecommendationAndInfomationList(RecommendationAndInfomationQueryDTO dto);

    /**
     * 删除
     *
     * @param id
     */
    @Delete({
            "delete from recommendation_and_infomation",
            "where id = #{id}"
    })
    void deleteById(Integer id);
}
