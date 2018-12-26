package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.LatestIrrRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HuXin on 2018/3/6.
 */
@Component
@Mapper
public interface LatestIrrRecordExtMapper {
    @Insert({
            "insert into latest_irr_log (irr_area_id, iasystem_id, ",
            "fm_id, duration, add_time, ",
            "start_time, finish_time, ",
            "result)",
            "values (#{irrAreaId,jdbcType=INTEGER}, #{iasystemId,jdbcType=INTEGER}, ",
            "#{fmId,jdbcType=INTEGER}, #{duration,jdbcType=INTEGER}, #{addTime,jdbcType=TIMESTAMP}, ",
            "#{startTime,jdbcType=TIMESTAMP}, #{finishTime,jdbcType=TIMESTAMP}, ",
            "#{result,jdbcType=INTEGER})",
            "on duplicate key update",
            "irr_area_id = #{irrAreaId,jdbcType=INTEGER},",
            "iasystem_id = #{iasystemId,jdbcType=INTEGER},",
            "fm_id = #{fmId,jdbcType=INTEGER},",
            "duration = #{duration,jdbcType=INTEGER},",
            "add_time = #{addTime,jdbcType=TIMESTAMP},",
            "start_time = #{startTime,jdbcType=TIMESTAMP},",
            "finish_time = #{finishTime,jdbcType=TIMESTAMP},",
            "result = #{result,jdbcType=INTEGER}"
    })
    int upsert(LatestIrrRecord record);

    @Select({
            "select",
            "irr_area_id, iasystem_id, fm_id, duration, add_time, start_time, finish_time, result",
            "from latest_irr_log",
            "where iasystem_id = #{iasystemId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "irr_area_id", property = "irrAreaId", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "iasystem_id", property = "iasystemId", jdbcType = JdbcType.INTEGER),
            @Result(column = "fm_id", property = "fmId", jdbcType = JdbcType.INTEGER),
            @Result(column = "duration", property = "duration", jdbcType = JdbcType.INTEGER),
            @Result(column = "add_time", property = "addTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "start_time", property = "startTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "finish_time", property = "finishTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "result", property = "result", jdbcType = JdbcType.INTEGER)
    })
    List<LatestIrrRecord> selectByIaSystem(Integer iasystemId);
}
