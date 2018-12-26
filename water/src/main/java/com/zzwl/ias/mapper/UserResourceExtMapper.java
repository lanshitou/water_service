package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.UserResource;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HuXin on 2018/1/10.
 */
@Component
@Mapper
public interface UserResourceExtMapper {
    @Select({
            "select",
            "id, user_id, type, resource_id, role_id",
            "from user_resources",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and type = #{type,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "resource_id", property = "resourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER)
    })
    List<UserResource> getByUser(@Param("userId") int userId, @Param("type") int type);


    @Select({
            "select",
            "user_resources.id as id, user_id, type, resource_id, role_id",
            "from user_resources, farmland",
            "where user_id = #{userId,jdbcType=INTEGER}",
            "and type = 2",
            "and farmland.iasystem_id = #{iasId,jdbcType=INTEGER}",
            "and resource_id = farmland.id"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "type", property = "type", jdbcType = JdbcType.INTEGER),
            @Result(column = "resource_id", property = "resourceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "role_id", property = "roleId", jdbcType = JdbcType.INTEGER)
    })
    List<UserResource> getUserFarmlands(@Param("userId") int userId, @Param("iasId") int iasId);
}
