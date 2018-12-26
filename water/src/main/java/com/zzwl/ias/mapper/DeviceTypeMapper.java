package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceType;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface DeviceTypeMapper {
    @Delete({
        "delete from device_type",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into device_type (id, code, ",
        "name, cron)",
        "values (#{id,jdbcType=INTEGER}, #{code,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{cron,jdbcType=VARCHAR})"
    })
    int insert(DeviceType record);

    @InsertProvider(type=DeviceTypeSqlProvider.class, method="insertSelective")
    int insertSelective(DeviceType record);

    @Select({
        "select",
        "id, code, name, cron",
        "from device_type",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="cron", property="cron", jdbcType=JdbcType.VARCHAR)
    })
    DeviceType selectByPrimaryKey(Integer id);

    @UpdateProvider(type=DeviceTypeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DeviceType record);

    @Update({
        "update device_type",
        "set code = #{code,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "cron = #{cron,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(DeviceType record);
}