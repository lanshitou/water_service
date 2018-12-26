package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.InfoArticleDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface InfoArticleDOMapper {

    @Delete({
            "delete from info_article",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
            "insert into info_article (id, title, ",
            "img, originImg, ",
            "watchCount, commentCount, ",
            "publishTime, tag, ",
            "origin, regionId, ",
            "cropId, catPid, ",
            "catId, htmlContent)",
            "values (#{id,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, ",
            "#{img,jdbcType=VARCHAR}, #{originImg,jdbcType=VARCHAR}, ",
            "#{watchCount,jdbcType=SMALLINT}, #{commentCount,jdbcType=SMALLINT}, ",
            "#{publishTime,jdbcType=TIMESTAMP}, #{tag,jdbcType=VARCHAR}, ",
            "#{origin,jdbcType=VARCHAR}, #{regionId,jdbcType=SMALLINT}, ",
            "#{cropId,jdbcType=INTEGER}, #{catPid,jdbcType=VARCHAR}, ",
            "#{catId,jdbcType=SMALLINT}, #{htmlContent,jdbcType=LONGVARCHAR})"
    })
    int insert(InfoArticleDO record);

    @InsertProvider(type = InfoArticleDOSqlProvider.class, method = "insertSelective")
    int insertSelective(InfoArticleDO record);

    @Select({
            "select",
            "id, title, img, originImg, watchCount, commentCount, publishTime, tag, origin, ",
            "regionId, cropId, catPid, catId, htmlContent",
            "from info_article",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "img", property = "img", jdbcType = JdbcType.VARCHAR),
            @Result(column = "originImg", property = "originImg", jdbcType = JdbcType.VARCHAR),
            @Result(column = "watchCount", property = "watchCount", jdbcType = JdbcType.SMALLINT),
            @Result(column = "commentCount", property = "commentCount", jdbcType = JdbcType.SMALLINT),
            @Result(column = "publishTime", property = "publishTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "tag", property = "tag", jdbcType = JdbcType.VARCHAR),
            @Result(column = "origin", property = "origin", jdbcType = JdbcType.VARCHAR),
            @Result(column = "regionId", property = "regionId", jdbcType = JdbcType.SMALLINT),
            @Result(column = "cropId", property = "cropId", jdbcType = JdbcType.INTEGER),
            @Result(column = "catPid", property = "catPid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "catId", property = "catId", jdbcType = JdbcType.SMALLINT),
            @Result(column = "htmlContent", property = "htmlContent", jdbcType = JdbcType.LONGVARCHAR)
    })
    InfoArticleDO selectByPrimaryKey(Integer id);

    @UpdateProvider(type = InfoArticleDOSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(InfoArticleDO record);

    @Update({
            "update info_article",
            "set title = #{title,jdbcType=VARCHAR},",
            "img = #{img,jdbcType=VARCHAR},",
            "originImg = #{originImg,jdbcType=VARCHAR},",
            "watchCount = #{watchCount,jdbcType=SMALLINT},",
            "commentCount = #{commentCount,jdbcType=SMALLINT},",
            "publishTime = #{publishTime,jdbcType=TIMESTAMP},",
            "tag = #{tag,jdbcType=VARCHAR},",
            "origin = #{origin,jdbcType=VARCHAR},",
            "regionId = #{regionId,jdbcType=SMALLINT},",
            "cropId = #{cropId,jdbcType=INTEGER},",
            "catPid = #{catPid,jdbcType=VARCHAR},",
            "catId = #{catId,jdbcType=SMALLINT},",
            "htmlContent = #{htmlContent,jdbcType=LONGVARCHAR}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKeyWithBLOBs(InfoArticleDO record);

    @Update({
            "update info_article",
            "set title = #{title,jdbcType=VARCHAR},",
            "img = #{img,jdbcType=VARCHAR},",
            "originImg = #{originImg,jdbcType=VARCHAR},",
            "watchCount = #{watchCount,jdbcType=SMALLINT},",
            "commentCount = #{commentCount,jdbcType=SMALLINT},",
            "publishTime = #{publishTime,jdbcType=TIMESTAMP},",
            "tag = #{tag,jdbcType=VARCHAR},",
            "origin = #{origin,jdbcType=VARCHAR},",
            "regionId = #{regionId,jdbcType=SMALLINT},",
            "cropId = #{cropId,jdbcType=INTEGER},",
            "catPid = #{catPid,jdbcType=VARCHAR},",
            "catId = #{catId,jdbcType=SMALLINT}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(InfoArticleDO record);
}