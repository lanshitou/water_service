package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceOpRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

/**
 * Created by HuXin on 2017/12/21.
 */
@Component
@Mapper
public interface DeviceOpRecordExtMapper extends DeviceOpRecordMapper {
    @Select({
            "select id, dev_id, user_id, source, time, op_type, auto_stop, duration, param, result",
            "from dev_op_log",
            "where id = (",
            "select id from dev_op_log",
            "where dev_id = #{devId,jdbcType=BIGINT} and op_type = 1 and result = 0 ",
            "and time = (select max(time) from dev_op_log where dev_id = #{devId,jdbcType=BIGINT} and op_type = 1 and result = 0)",
            "limit 1)"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "dev_id", property = "devId", jdbcType = JdbcType.BIGINT),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "source", property = "source", jdbcType = JdbcType.INTEGER),
            @Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "op_type", property = "opType", jdbcType = JdbcType.TINYINT),
            @Result(column = "auto_stop", property = "autoStop", jdbcType = JdbcType.BIT),
            @Result(column = "duration", property = "duration", jdbcType = JdbcType.INTEGER),
            @Result(column = "param", property = "param", jdbcType = JdbcType.INTEGER),
            @Result(column = "result", property = "result", jdbcType = JdbcType.INTEGER)
    })
    DeviceOpRecord getLatestStartRecord(Long devId);
}
