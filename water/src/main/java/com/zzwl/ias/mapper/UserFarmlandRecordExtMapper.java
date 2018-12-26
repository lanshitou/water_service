package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.UserFarmlandRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HuXin on 2018/2/24.
 */
@Component
@Mapper
public interface UserFarmlandRecordExtMapper extends UserFarmlandRecordMapper {
    @Select({
            "select",
            "id, user_id, ias_id, fm_id, role_id",
            "from user_farmland",
            "where fm_id = #{farmlandId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "fm_id", property = "fmId", jdbcType = JdbcType.INTEGER),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER)
    })
    List<UserFarmlandRecord> selectByFarmlandId(Integer farmlandId);

    @Select({
            "select",
            "id, user_id, ias_id, fm_id, role_id",
            "from user_farmland",
            "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "fm_id", property = "fmId", jdbcType = JdbcType.INTEGER),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER)
    })
    List<UserFarmlandRecord> selectByUserId(Integer userId);

    @Select({
            "select",
            "id, user_id, ias_id, fm_id, role_id",
            "from user_farmland",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and ias_id = #{iasId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "fm_id", property = "fmId", jdbcType = JdbcType.INTEGER),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER)
    })
    List<UserFarmlandRecord> selectByIasIdAndUserId(@Param(value = "iasId") Integer iasId, @Param(value = "userId") Integer userId);
}
