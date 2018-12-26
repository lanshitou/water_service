package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.Image;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface ImageMapper {
    @Delete({
        "delete from image",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into image (id, name, ",
        "url, info, create_time, ",
        "update_time)",
        "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
        "#{url,jdbcType=VARCHAR}, #{info,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Image record);

    @InsertProvider(type=ImageSqlProvider.class, method="insertSelective")
    int insertSelective(Image record);

    @Select({
        "select",
        "id, name, url, info, create_time, update_time",
        "from image",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="url", property="url", jdbcType=JdbcType.VARCHAR),
        @Result(column="info", property="info", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Image selectByPrimaryKey(Integer id);

    @UpdateProvider(type=ImageSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Image record);

    @Update({
        "update image",
        "set name = #{name,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "info = #{info,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Image record);
}