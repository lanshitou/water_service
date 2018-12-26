package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.WarningDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface WarningDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning
     *
     * @mbg.generated
     */
    @Delete({
        "delete from warning",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning
     *
     * @mbg.generated
     */
    @Insert({
        "insert into warning (id, type, ",
        "sub_type, level, ",
        "cleared, produce_time, ",
        "clear_time, clear_reason, ",
        "addr_type, addr_ias, ",
        "addr_irri_fer, addr_farmland, ",
        "addr_area, addr_parent_dev, ",
        "addr_dev, extension)",
        "values (#{id,jdbcType=BIGINT}, #{type,jdbcType=INTEGER}, ",
        "#{subType,jdbcType=INTEGER}, #{level,jdbcType=INTEGER}, ",
        "#{cleared,jdbcType=BIT}, #{produceTime,jdbcType=TIMESTAMP}, ",
        "#{clearTime,jdbcType=TIMESTAMP}, #{clearReason,jdbcType=INTEGER}, ",
        "#{addrType,jdbcType=INTEGER}, #{addrIas,jdbcType=INTEGER}, ",
        "#{addrIrriFer,jdbcType=INTEGER}, #{addrFarmland,jdbcType=INTEGER}, ",
        "#{addrArea,jdbcType=INTEGER}, #{addrParentDev,jdbcType=INTEGER}, ",
        "#{addrDev,jdbcType=INTEGER}, #{extension,jdbcType=OTHER})"
    })
    int insert(WarningDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning
     *
     * @mbg.generated
     */
    @InsertProvider(type=WarningDOSqlProvider.class, method="insertSelective")
    int insertSelective(WarningDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, type, sub_type, level, cleared, produce_time, clear_time, clear_reason, ",
        "addr_type, addr_ias, addr_irri_fer, addr_farmland, addr_area, addr_parent_dev, ",
        "addr_dev, extension",
        "from warning",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="type", property="type", jdbcType=JdbcType.INTEGER),
        @Result(column="sub_type", property="subType", jdbcType=JdbcType.INTEGER),
        @Result(column="level", property="level", jdbcType=JdbcType.INTEGER),
        @Result(column="cleared", property="cleared", jdbcType=JdbcType.BIT),
        @Result(column="produce_time", property="produceTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="clear_time", property="clearTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="clear_reason", property="clearReason", jdbcType=JdbcType.INTEGER),
        @Result(column="addr_type", property="addrType", jdbcType=JdbcType.INTEGER),
        @Result(column="addr_ias", property="addrIas", jdbcType=JdbcType.INTEGER),
        @Result(column="addr_irri_fer", property="addrIrriFer", jdbcType=JdbcType.INTEGER),
        @Result(column="addr_farmland", property="addrFarmland", jdbcType=JdbcType.INTEGER),
        @Result(column="addr_area", property="addrArea", jdbcType=JdbcType.INTEGER),
        @Result(column="addr_parent_dev", property="addrParentDev", jdbcType=JdbcType.INTEGER),
        @Result(column="addr_dev", property="addrDev", jdbcType=JdbcType.INTEGER),
        @Result(column="extension", property="extension", jdbcType=JdbcType.OTHER)
    })
    WarningDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning
     *
     * @mbg.generated
     */
    @UpdateProvider(type=WarningDOSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(WarningDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning
     *
     * @mbg.generated
     */
    @Update({
        "update warning",
        "set type = #{type,jdbcType=INTEGER},",
          "sub_type = #{subType,jdbcType=INTEGER},",
          "level = #{level,jdbcType=INTEGER},",
          "cleared = #{cleared,jdbcType=BIT},",
          "produce_time = #{produceTime,jdbcType=TIMESTAMP},",
          "clear_time = #{clearTime,jdbcType=TIMESTAMP},",
          "clear_reason = #{clearReason,jdbcType=INTEGER},",
          "addr_type = #{addrType,jdbcType=INTEGER},",
          "addr_ias = #{addrIas,jdbcType=INTEGER},",
          "addr_irri_fer = #{addrIrriFer,jdbcType=INTEGER},",
          "addr_farmland = #{addrFarmland,jdbcType=INTEGER},",
          "addr_area = #{addrArea,jdbcType=INTEGER},",
          "addr_parent_dev = #{addrParentDev,jdbcType=INTEGER},",
          "addr_dev = #{addrDev,jdbcType=INTEGER},",
          "extension = #{extension,jdbcType=OTHER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(WarningDO record);
}