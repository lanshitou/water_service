package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.CommonRegionDO;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CommonRegionDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table common_region
     *
     * @mbg.generated
     */
    @Delete({
        "delete from common_region",
        "where id = #{id,jdbcType=SMALLINT}"
    })
    int deleteByPrimaryKey(Short id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table common_region
     *
     * @mbg.generated
     */
    @Insert({
        "insert into common_region (id, pid, ",
        "code, name)",
        "values (#{id,jdbcType=SMALLINT}, #{pid,jdbcType=SMALLINT}, ",
        "#{code,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR})"
    })
    int insert(CommonRegionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table common_region
     *
     * @mbg.generated
     */
    @InsertProvider(type=CommonRegionDOSqlProvider.class, method="insertSelective")
    int insertSelective(CommonRegionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table common_region
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, pid, code, name",
        "from common_region",
        "where id = #{id,jdbcType=SMALLINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.SMALLINT, id=true),
        @Result(column="pid", property="pid", jdbcType=JdbcType.SMALLINT),
        @Result(column="code", property="code", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    CommonRegionDO selectByPrimaryKey(Short id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table common_region
     *
     * @mbg.generated
     */
    @UpdateProvider(type=CommonRegionDOSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CommonRegionDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table common_region
     *
     * @mbg.generated
     */
    @Update({
        "update common_region",
        "set pid = #{pid,jdbcType=SMALLINT},",
          "code = #{code,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=SMALLINT}"
    })
    int updateByPrimaryKey(CommonRegionDO record);
}