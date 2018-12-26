package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IasPumpRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

/**
 * Created by HuXin on 2018/1/10.
 */
@Component
@Mapper
public interface IasPumpRecordExtMapper {
    @Insert({
            "insert into ias_pump (id, iasystem_id, ",
            "pump_id, name)",
            "values (#{id,jdbcType=INTEGER}, #{iasystemId,jdbcType=INTEGER}, ",
            "#{pumpId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys = true)
    int insert(IasPumpRecord record);

    @Select({
            "select",
            "id, iasystem_id, pump_id, name",
            "from ias_pump",
            "where iasystem_id = #{iasystemId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "iasystem_id", property = "iasystemId", jdbcType = JdbcType.INTEGER),
            @Result(column = "pump_id", property = "pumpId", jdbcType = JdbcType.BIGINT),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR)
    })
    IasPumpRecord selectByIaSystem(Integer iasystemId);
}
