package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.CommonRegion;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface CommonRegionMapper {
    @Delete({
            "delete from common_region",
            "where id = #{id,jdbcType=SMALLINT}"
    })
    int deleteByPrimaryKey(Short id);

    @Insert({
            "insert into common_region (id, pid, ",
            "code, name)",
            "values (#{id,jdbcType=SMALLINT}, #{pid,jdbcType=SMALLINT}, ",
            "#{code,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR})"
    })
    int insert(CommonRegion record);

    @InsertProvider(type = CommonRegionSqlProvider.class, method = "insertSelective")
    int insertSelective(CommonRegion record);

    @Select({
            "select",
            "id, pid, code, name",
            "from common_region",
            "where id = #{id,jdbcType=SMALLINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.SMALLINT, id = true),
            @Result(column = "pid", property = "pid", jdbcType = JdbcType.SMALLINT),
            @Result(column = "code", property = "code", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR)
    })
    CommonRegion selectByPrimaryKey(Short id);

    @UpdateProvider(type = CommonRegionSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CommonRegion record);

    @Update({
            "update common_region",
            "set pid = #{pid,jdbcType=SMALLINT},",
            "code = #{code,jdbcType=INTEGER},",
            "name = #{name,jdbcType=VARCHAR}",
            "where id = #{id,jdbcType=SMALLINT}"
    })
    int updateByPrimaryKey(CommonRegion record);
}