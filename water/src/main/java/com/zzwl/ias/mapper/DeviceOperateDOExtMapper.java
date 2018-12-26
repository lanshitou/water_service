package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceOperateDO;
import com.zzwl.ias.dto.device.DeviceOperateDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-30
 * Time: 16:52
 */
@Component
@Mapper
public interface DeviceOperateDOExtMapper extends DeviceOperateDOMapper {
    @Select({
            "<script>",
            "select",
            "id, ias_dev_id, time, op_type, auto_stop, duration, param, result, source, user_id",
            "from device_operate",
            "where ias_dev_id = #{devId,jdbcType=INTEGER}",
            "<if test='type != null'>",
            "and source = #{type,jdbcType=INTEGER}",
            "</if>",
            "order by time desc",
            "limit #{offset,jdbcType=INTEGER}, #{limit,jdbcType=INTEGER}",
            "</script>"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="ias_dev_id", property="iasDevId", jdbcType=JdbcType.INTEGER),
            @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="op_type", property="opType", jdbcType=JdbcType.INTEGER),
            @Result(column="auto_stop", property="autoStop", jdbcType=JdbcType.BIT),
            @Result(column="duration", property="duration", jdbcType=JdbcType.INTEGER),
            @Result(column="param", property="param", jdbcType=JdbcType.INTEGER),
            @Result(column="result", property="result", jdbcType=JdbcType.INTEGER),
            @Result(column="source", property="source", jdbcType=JdbcType.INTEGER),
            @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER)
    })
    List<DeviceOperateDTO> listHistoryOperation(@Param(value = "devId") Integer devId, @Param(value = "type")Integer type,
                                                @Param(value = "offset") Integer offset, @Param(value = "limit")Integer limit);
}
