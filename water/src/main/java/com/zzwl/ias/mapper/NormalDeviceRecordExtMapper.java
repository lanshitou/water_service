package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.NormalDeviceRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HuXin on 2017/12/14.
 */
@Component
@Mapper
public interface NormalDeviceRecordExtMapper {
    @Insert({
            "insert into fm_device_normal (id, fm_id, ",
            "name, dev_id)",
            "values (#{id,jdbcType=INTEGER}, #{fmId,jdbcType=INTEGER}, ",
            "#{name,jdbcType=VARCHAR}, #{devId,jdbcType=BIGINT})"
    })
    @Options(useGeneratedKeys = true)
    int insert(NormalDeviceRecord record);

    @Select({
            "select",
            "id, fm_id, name, dev_id",
            "from fm_device_normal",
            "where fm_id = #{farmlandId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "fm_id", property = "fmId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "dev_id", property = "devId", jdbcType = JdbcType.BIGINT)
    })
    List<NormalDeviceRecord> selectByFarmlandId(Integer farmlandId);
}
