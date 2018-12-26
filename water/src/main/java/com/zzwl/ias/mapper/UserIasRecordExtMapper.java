package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.UserIasRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-11
 * Time: 11:28
 */
@Component
@Mapper
public interface UserIasRecordExtMapper extends UserIasRecordMapper {
    @Select({
            "select",
            "id, user_id, ias_id, role_id",
            "from user_ias",
            "where ias_id = #{iasId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER)
    })
    List<UserIasRecord> selectByIasId(Integer iasId);

    @Select({
            "select",
            "id, user_id, ias_id, role_id",
            "from user_ias",
            "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER)
    })
    List<UserIasRecord> selectByUserId(Integer userId);

    @Select({
            "select",
            "id, user_id, ias_id, role_id",
            "from user_ias",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and ias_id = #{iasId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER)
    })
    UserIasRecord selectByUserAndIas(@Param(value = "userId") Integer userId, @Param(value = "iasId") Integer iasId);
}
