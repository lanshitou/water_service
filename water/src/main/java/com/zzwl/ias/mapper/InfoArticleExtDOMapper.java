package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.InfoArticleDO;
import com.zzwl.ias.dto.info.InfoArticleAddDTO;
import com.zzwl.ias.dto.info.InfoArticleUpdateDTO;
import com.zzwl.ias.vo.InfoArticleDetailVo;
import com.zzwl.ias.vo.InfoArticleListVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface InfoArticleExtDOMapper extends InfoArticleDOMapper {
    /**
     * 查找关联文章
     */
    List<InfoArticleDO> findRelationArticleByPidAndRegionIdAndCropId(@Param("articleId") Integer articleId, @Param("pid") String pid, @Param("cropId") Integer cropId, @Param("regionId") Integer regionId);

    /**
     * 获取文章列表
     */
    List<InfoArticleDO> findArticleByIdList(@Param("ids") List<Integer> ids);

    /**
     * 查看分类下是否有文章
     *
     * @param cid
     * @return
     */
    @Select({
            "SELECT COUNT(*) countt FROM info_article ia WHERE ia.catId = #{cid}"
    })
    int selectCountByCatId(@Param("cid") Integer cid);

    /**
     * 文章列表
     *
     * @param id
     * @return
     */
    @Select({
            "<script>SELECT",
            "  ia.id,",
            "  ia.title,",
            "  ia.img,",
            "  ia.originImg,",
            "  ia.watchCount,",
            "  ia.commentCount,",
            "  DATE_FORMAT(",
            "    ia.publishTime,",
            "    '%Y-%m-%d %H:%i:%S'",
            "  ) publishTime,",
            "  ia.tag,",
            "  ia.htmlContent,",
            "  ia.origin,",
            "  ia.catId,",
            "  ( CASE",
            "  WHEN GROUP_CONCAT(rai.type) = '1'",
            "  THEN '1'",
            "  WHEN GROUP_CONCAT(rai.type) = '0'",
            "  THEN '0' WHEN GROUP_CONCAT(rai.type) IS NULL",
            "  THEN ''",
            "  ELSE '2'",
            "  END",
            "  ) type",
            "FROM",
            "  info_article ia",
            "  LEFT JOIN  recommendation_and_infomation rai",
            "  ON rai.article_id = ia.id",
            "<if test=\"id != 0\"> WHERE ia.catId = #{id}</if> ",
            "GROUP BY ia.id",
            "ORDER BY ia.publishTime desc</script>"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "img", property = "img", jdbcType = JdbcType.VARCHAR),
            @Result(column = "originImg", property = "originImg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "commentCount", property = "commentCount", jdbcType = JdbcType.INTEGER),
            @Result(column = "publishTime", property = "publishTime", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tag", property = "tag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "htmlContent", property = "htmlContent", jdbcType = JdbcType.VARCHAR),
            @Result(column = "origin", property = "origin", jdbcType = JdbcType.VARCHAR),
            @Result(column = "catId", property = "catId", jdbcType = JdbcType.INTEGER),
            @Result(column = "type", property = "type", jdbcType = JdbcType.VARCHAR)
    })
    List<InfoArticleListVo> selectInfoArticleList(@Param("id") Integer id);

    /**
     * 添加文章
     *
     * @param infoArticleAddDTO
     */
    @Insert({
            "insert into info_article (title, ",
            "img, originImg, tag, ",
            "origin, catId, htmlContent)",
            "values (#{title,jdbcType=VARCHAR},#{img,jdbcType=VARCHAR}, #{originImg,jdbcType=VARCHAR}, #{tag,jdbcType=VARCHAR}, ",
            "#{origin,jdbcType=VARCHAR}, #{catId,jdbcType=SMALLINT}, #{htmlContent,jdbcType=LONGVARCHAR})"
    })
    void insertInfoArticle(InfoArticleAddDTO infoArticleAddDTO);

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @Select({
            "SELECT ia.id, ia.title, ia.img, ia.originImg, ia.watchCount, ia.commentCount, DATE_FORMAT( ia.publishTime, '%Y-%m-%d %H:%i:%S' ) publishTime, ia.tag, ia.htmlContent, ia.origin, ia.catId,ic.name,(",
            "  CASE",
            "  WHEN GROUP_CONCAT(rai.type) = '1' ",
            "  THEN '1'",
            "  WHEN GROUP_CONCAT(rai.type) = '0' ",
            "  THEN '0'",
            "  WHEN GROUP_CONCAT(rai.type) IS NULL",
            "  THEN '' ",
            "  ELSE '2' ",
            "  END",
            "  ) TYPE FROM info_article ia LEFT JOIN info_cat ic ON ia.catId = ic.id LEFT JOIN recommendation_and_infomation rai ",
            " ON rai.article_id = ia.id WHERE ia.id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "img", property = "img", jdbcType = JdbcType.VARCHAR),
            @Result(column = "originImg", property = "originImg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "publishTime", property = "publishTime", jdbcType = JdbcType.VARCHAR),
            @Result(column = "tag", property = "tag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "htmlContent", property = "htmlContent", jdbcType = JdbcType.VARCHAR),
            @Result(column = "origin", property = "origin", jdbcType = JdbcType.VARCHAR),
            @Result(column = "catId", property = "catId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR)
    })
    InfoArticleDetailVo selectInfoArticleById(@Param("id") Integer id);

    /**
     * 文章修改
     *
     * @param infoArticleUpdateDTO
     */
    @Update({
            "UPDATE info_article ia  " +
                    "SET  ia.title = #{title}," +
                    "ia.img=#{img}," +
                    "ia.originImg=#{originImg}," +
                    "ia.publishTime=#{publishTime}," +
                    "ia.htmlContent=#{htmlContent}," +
                    "ia.tag=#{tag}," +
                    "ia.origin=#{origin}," +
                    "ia.catId=#{catId} " +
                    "WHERE ia.id = #{id}"
    })
    void updateInfoArticle(InfoArticleUpdateDTO infoArticleUpdateDTO);

    /**
     * 文章删除
     *
     * @param ids
     */
    @Delete({
            "<script>" +
                    "DELETE FROM info_article WHERE id in " +
                    "<foreach collection=\"ids\" item=\"id\" open=\"(\" separator=\",\" close=\")\">#{id}</foreach>" +
                    "</script>"
    })
    void deleteInfoArticle(@Param("ids") List<Integer> ids);
}
