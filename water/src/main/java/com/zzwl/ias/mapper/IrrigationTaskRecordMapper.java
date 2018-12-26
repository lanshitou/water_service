package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IrrigationTaskRecord;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface IrrigationTaskRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table irrigation_task
     *
     * @mbg.generated
     */
    @Delete({
        "delete from irrigation_task",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table irrigation_task
     *
     * @mbg.generated
     */
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
    int insert(IrrigationTaskRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table irrigation_task
     *
     * @mbg.generated
     */
    @InsertProvider(type=IrrigationTaskRecordSqlProvider.class, method="insertSelective")
    int insertSelective(IrrigationTaskRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table irrigation_task
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, ias_id, farmland_id, irri_area_id, create_time, start_time, finish_time, ",
        "create_user, delete_user, status, result, exp_duration, irri_duration",
        "from irrigation_task",
        "where id = #{id,jdbcType=INTEGER}"
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
    IrrigationTaskRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table irrigation_task
     *
     * @mbg.generated
     */
    @UpdateProvider(type=IrrigationTaskRecordSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IrrigationTaskRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table irrigation_task
     *
     * @mbg.generated
     */
    @Update({
        "update irrigation_task",
        "set ias_id = #{iasId,jdbcType=INTEGER},",
          "farmland_id = #{farmlandId,jdbcType=INTEGER},",
          "irri_area_id = #{irriAreaId,jdbcType=INTEGER},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "finish_time = #{finishTime,jdbcType=TIMESTAMP},",
          "create_user = #{createUser,jdbcType=INTEGER},",
          "delete_user = #{deleteUser,jdbcType=INTEGER},",
          "status = #{status,jdbcType=INTEGER},",
          "result = #{result,jdbcType=INTEGER},",
          "exp_duration = #{expDuration,jdbcType=INTEGER},",
          "irri_duration = #{irriDuration,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(IrrigationTaskRecord record);
}