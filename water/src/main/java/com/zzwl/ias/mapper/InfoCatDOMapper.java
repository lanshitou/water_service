package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.InfoCatDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface InfoCatDOMapper {

    @Delete({
            "delete from info_cat",
            "where id = #{id,jdbcType=SMALLINT}"
    })
    int deleteByPrimaryKey(Short id);

    @Insert({
            "insert into info_cat (id, pid, ",
            "name)",
            "values (#{id,jdbcType=SMALLINT}, #{pid,jdbcType=VARCHAR}, ",
            "#{name,jdbcType=VARCHAR})"
    })
    int insert(InfoCatDO record);

    @InsertProvider(type = InfoCatDOSqlProvider.class, method = "insertSelective")
    int insertSelective(InfoCatDO record);

    @Select({
            "select",
            "id, pid, name",
            "from info_cat",
            "where id = #{id,jdbcType=SMALLINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.SMALLINT, id = true),
            @Result(column = "pid", property = "pid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR)
    })
    InfoCatDO selectByPrimaryKey(Short id);

    @UpdateProvider(type = InfoCatDOSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(InfoCatDO record);

    @Update({
            "update info_cat",
            "set pid = #{pid,jdbcType=VARCHAR},",
            "name = #{name,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=SMALLINT}"
    })
    int updateByPrimaryKey(InfoCatDO record);
}