package com.zzwl.ias.mapper;

import com.zzwl.ias.vo.DeviceTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/27.
 */
@Component
@Mapper
public interface DeviceTypeExtMapper extends DeviceTypeMapper {
    @Select({
            "SELECT dt.code, dt.name, dt.cron FROM device_type dt"
    })
    @Results({
            @Result(column = "code", property = "code", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "cron", property = "cron", jdbcType = JdbcType.VARCHAR)
    })
    List<DeviceTypeVo> selectAllDeviceType();
}
