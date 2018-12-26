package com.zzwl.ias.mapper;

import com.zzwl.ias.vo.RegionVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Lvpin on 2018/11/9.
 */
@Component
@Mapper
public interface CommonRegionExtMapper extends CommonRegionMapper {
    @Select({
            "SELECT * FROM common_region cr WHERE cr.pid = #{pid} "
    })
    @Results({
            @Result(column = "id", jdbcType = JdbcType.INTEGER, property = "id", id = true),
            @Result(column = "pid", property = "pid", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR)
    })
    /**
     * 获取region结构
     */
    List<RegionVo> selectRegionTree(@Param("pid") Integer pid);

    @Select({
            "SELECT COUNT(*) FROM common_region WHERE common_region.name = #{name}"
    })
    /**
     * name是否存在
     */
    int selectRegionCountByname(@Param("name") String name);

    @Insert({
            "INSERT INTO common_region (pid,name) VALUES(#{pid},#{name})"
    })
    /**
     * 添加新地址
     */
    void insertRegion(@Param("pid") Integer pid, @Param("name") String name);

    @Select({
            "SELECT cr.name FROM common_region cr WHERE cr.id = #{id}"
    })
    /**
     * 根据id查找name
     */
    String selectNameById(@Param("id") Integer id);

    /**
     * @param id
     * @return
     */
    @Select({
            "SELECT cr.name,cr.pid FROM common_region cr WHERE cr.id = #{id}"
    })
    Map<String, Object> selectPidById(@Param("id") Integer id);

    @Select({
            "SELECT @@IDENTITY id"
    })
    int selectLastId();
}
