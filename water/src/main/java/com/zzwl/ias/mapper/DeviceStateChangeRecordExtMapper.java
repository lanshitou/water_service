package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceStateChangeRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HuXin on 2017/12/26.
 */
@Component
@Mapper
public interface DeviceStateChangeRecordExtMapper {
    @Select({
            "select",
            "id, dev_id, time, online, state",
            "from dev_state_log",
            "where dev_id = #{deviceId,jdbcType=BIGINT}",
            "order by time desc",
            "limit #{offset,jdbcType=INTEGER}, #{size,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "dev_id", property = "devId", jdbcType = JdbcType.BIGINT),
            @Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "online", property = "online", jdbcType = JdbcType.BIT),
            @Result(column = "state", property = "state", jdbcType = JdbcType.TINYINT)
    })
    List<DeviceStateChangeRecord> getDeviceStateChangeLog(@Param("deviceId") long deviceId, @Param("offset") int offset, @Param("size") int size);

}
