package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DcPointRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HuXin on 2018/1/9.
 */
@Component
@Mapper
public interface DcPointRecordExtMapper {
    @Insert({
            "insert into dc_point (id, name, ",
            "owner_type, owner_id, ",
            "sensor_id)",
            "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{ownerType,jdbcType=INTEGER}, #{ownerId,jdbcType=INTEGER}, ",
            "#{sensorId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys = true)
    int insert(DcPointRecord record);

    @Select({
            "select",
            "id, name, owner_type, owner_id, sensor_id",
            "from dc_point",
            "where owner_id = #{ownerId,jdbcType=INTEGER}",
            "and owner_type =  #{ownerType,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "owner_type", property = "ownerType", jdbcType = JdbcType.INTEGER),
            @Result(column = "owner_id", property = "ownerId", jdbcType = JdbcType.INTEGER),
            @Result(column = "sensor_id", property = "sensorId", jdbcType = JdbcType.BIGINT)
    })
    List<DcPointRecord> getDcPointRecordByOwner(@Param("ownerId") Integer ownerId, @Param("ownerType") Integer ownerType);
}
