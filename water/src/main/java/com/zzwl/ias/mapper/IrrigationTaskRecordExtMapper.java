package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IrrigationTaskRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-29
 * Time: 14:41
 */
@Component
@Mapper
public interface IrrigationTaskRecordExtMapper extends IrrigationTaskRecordMapper {
    @Insert({
            "insert into irrigation_task (id, ias_id, ",
            "farmland_id, irri_area_id, ",
            "create_time, start_time, ",
            "finish_time, create_user, ",
            "delete_user, status, ",
            "result, exp_duration, ",
            "irri_duration)",
            "values (#{id,jdbcType=INTEGER}, #{iasId,jdbcType=INTEGER}, ",
            "#{farmlandId,jdbcType=INTEGER}, #{irriAreaId,jdbcType=INTEGER}, ",
            "#{createTime,jdbcType=TIMESTAMP}, #{startTime,jdbcType=TIMESTAMP}, ",
            "#{finishTime,jdbcType=TIMESTAMP}, #{createUser,jdbcType=INTEGER}, ",
            "#{deleteUser,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, ",
            "#{result,jdbcType=INTEGER}, #{expDuration,jdbcType=INTEGER}, ",
            "#{irriDuration,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true)
    int insert(IrrigationTaskRecord record);

    @Select({
            "select",
            "id, ias_id, farmland_id, irri_area_id, create_time, start_time, finish_time, ",
            "create_user, delete_user, status, result, exp_duration, irri_duration",
            "from irrigation_task",
            "where irri_area_id = #{irriAreaId,jdbcType=INTEGER}",
            "order by finish_time desc limit 0,1"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="ias_id", property="iasId", jdbcType=JdbcType.INTEGER),
            @Result(column="farmland_id", property="farmlandId", jdbcType=JdbcType.INTEGER),
            @Result(column="irri_area_id", property="irriAreaId", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="finish_time", property="finishTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="create_user", property="createUser", jdbcType=JdbcType.INTEGER),
            @Result(column="delete_user", property="deleteUser", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="result", property="result", jdbcType=JdbcType.INTEGER),
            @Result(column="exp_duration", property="expDuration", jdbcType=JdbcType.INTEGER),
            @Result(column="irri_duration", property="irriDuration", jdbcType=JdbcType.INTEGER)
    })
    IrrigationTaskRecord selectLatestRecord(Integer irriAreaId);

    @Select({
            "select",
            "id, ias_id, farmland_id, irri_area_id, create_time, start_time, finish_time, ",
            "create_user, delete_user, status, result, exp_duration, irri_duration",
            "from irrigation_task",
            "where farmland_id = #{farmlandId,jdbcType=INTEGER}",
            "order by finish_time desc"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="ias_id", property="iasId", jdbcType=JdbcType.INTEGER),
            @Result(column="farmland_id", property="farmlandId", jdbcType=JdbcType.INTEGER),
            @Result(column="irri_area_id", property="irriAreaId", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="finish_time", property="finishTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="create_user", property="createUser", jdbcType=JdbcType.INTEGER),
            @Result(column="delete_user", property="deleteUser", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="result", property="result", jdbcType=JdbcType.INTEGER),
            @Result(column="exp_duration", property="expDuration", jdbcType=JdbcType.INTEGER),
            @Result(column="irri_duration", property="irriDuration", jdbcType=JdbcType.INTEGER)
    })
    LinkedList<IrrigationTaskRecord> selectByFmId(Integer farmlandId);

    @Select({
            "select",
            "id, ias_id, farmland_id, irri_area_id, create_time, start_time, finish_time, ",
            "create_user, delete_user, status, result, exp_duration, irri_duration",
            "from irrigation_task",
            "where irri_area_id = #{irriAreaId,jdbcType=INTEGER}",
            "order by finish_time desc",
            "limit #{offset,jdbcType=INTEGER}, #{limit,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="ias_id", property="iasId", jdbcType=JdbcType.INTEGER),
            @Result(column="farmland_id", property="farmlandId", jdbcType=JdbcType.INTEGER),
            @Result(column="irri_area_id", property="irriAreaId", jdbcType=JdbcType.INTEGER),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="finish_time", property="finishTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="create_user", property="createUser", jdbcType=JdbcType.INTEGER),
            @Result(column="delete_user", property="deleteUser", jdbcType=JdbcType.INTEGER),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER),
            @Result(column="result", property="result", jdbcType=JdbcType.INTEGER),
            @Result(column="exp_duration", property="expDuration", jdbcType=JdbcType.INTEGER),
            @Result(column="irri_duration", property="irriDuration", jdbcType=JdbcType.INTEGER)
    })
    LinkedList<IrrigationTaskRecord> selectByIrriAreaId(@Param(value = "irriAreaId") Integer irriAreaId,
                                                        @Param(value = "offset") Integer offset,
                                                        @Param(value = "limit") Integer limit);
}
