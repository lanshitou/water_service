package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.SysConfigDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-26
 * Time: 15:37
 */
@Component
@Mapper
public interface SysConfigDoExtMapper extends SysConfigDOMapper {
    @Select({
            "select",
            "name, value, description",
            "from system_config"
    })
    @Results({
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR, id = true),
            @Result(column = "value", property = "value", jdbcType = JdbcType.OTHER),
            @Result(column = "description", property = "description", jdbcType = JdbcType.VARCHAR)
    })
    List<SysConfigDO> getAllConfig();
}
