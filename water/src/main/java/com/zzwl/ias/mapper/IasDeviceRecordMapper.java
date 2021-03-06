package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IasDeviceRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface IasDeviceRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ias_device
     *
     * @mbg.generated
     */
    @Delete({
            "delete from ias_device",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ias_device
     *
     * @mbg.generated
     */
    @Insert({
            "insert into ias_device (id, dev_id, ",
            "ias_id, farmland_id, ",
            "irri_area_id, ias_dev_id, ",
            "irri_fer_id, name, ",
            "usage_type, user_id, ",
            "sort_order)",
            "values (#{id,jdbcType=INTEGER}, #{devId,jdbcType=BIGINT}, ",
            "#{iasId,jdbcType=INTEGER}, #{farmlandId,jdbcType=INTEGER}, ",
            "#{irriAreaId,jdbcType=INTEGER}, #{iasDevId,jdbcType=BIGINT}, ",
            "#{irriFerId,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{usageType,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
            "#{sortOrder,jdbcType=INTEGER})"
    })
    int insert(IasDeviceRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ias_device
     *
     * @mbg.generated
     */
    @InsertProvider(type = IasDeviceRecordSqlProvider.class, method = "insertSelective")
    int insertSelective(IasDeviceRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ias_device
     *
     * @mbg.generated
     */
    @Select({
            "select",
            "id, dev_id, ias_id, farmland_id, irri_area_id, ias_dev_id, irri_fer_id, name, ",
            "usage_type, user_id, sort_order",
            "from ias_device",
            "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "dev_id", property = "devId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "farmland_id", property = "farmlandId", jdbcType = JdbcType.INTEGER),
            @Result(column = "irri_area_id", property = "irriAreaId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_dev_id", property = "iasDevId", jdbcType = JdbcType.BIGINT),
            @Result(column = "irri_fer_id", property = "irriFerId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "usage_type", property = "usageType", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER)
    })
    IasDeviceRecord selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ias_device
     *
     * @mbg.generated
     */
    @UpdateProvider(type = IasDeviceRecordSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(IasDeviceRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ias_device
     *
     * @mbg.generated
     */
    @Update({
            "update ias_device",
            "set dev_id = #{devId,jdbcType=BIGINT},",
            "ias_id = #{iasId,jdbcType=INTEGER},",
            "farmland_id = #{farmlandId,jdbcType=INTEGER},",
            "irri_area_id = #{irriAreaId,jdbcType=INTEGER},",
            "ias_dev_id = #{iasDevId,jdbcType=BIGINT},",
            "irri_fer_id = #{irriFerId,jdbcType=INTEGER},",
            "name = #{name,jdbcType=VARCHAR},",
            "usage_type = #{usageType,jdbcType=INTEGER},",
            "user_id = #{userId,jdbcType=INTEGER},",
            "sort_order = #{sortOrder,jdbcType=INTEGER}",
            "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(IasDeviceRecord record);
}