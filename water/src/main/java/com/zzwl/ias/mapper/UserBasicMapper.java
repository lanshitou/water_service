package com.zzwl.ias.mapper;

import com.zzwl.ias.vo.UserBasicInfoVo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Component
@Mapper
interface UserBasicMapper {
    @Select({
            "select id,username,image from users where id = #{userId}"
    })
    @Results({
            @Result(id = true, column = "id", property = "uid", jdbcType = JdbcType.INTEGER),
            @Result(column = "username", property = "username", jdbcType = JdbcType.VARCHAR),
            @Result(column = "image", property = "headImage", jdbcType = JdbcType.VARCHAR)
    })
    UserBasicInfoVo findUserById(Integer userId);
}