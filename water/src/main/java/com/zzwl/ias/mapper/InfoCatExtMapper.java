package com.zzwl.ias.mapper;

import com.zzwl.ias.dto.info.InfoCatAddDTO;
import com.zzwl.ias.vo.InfoCatVo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/28.
 */
@Mapper
@Component
public interface InfoCatExtMapper extends InfoCatDOMapper {
    /**
     * 查找
     *
     * @param pid
     * @return
     */
    @Select({
            "SELECT ic.id,ic.pid,ic.name FROM info_cat ic WHERE ic.pid = #{pid} "
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "pid", property = "pid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id", property = "children", many = @Many(
                    select = "com.zzwl.ias.mapper.InfoCatExtMapper.selectCatTree"
            ))
    })
    List<InfoCatVo> selectCatTree(@Param("pid") Integer pid);

    /**
     * 添加
     *
     * @param infoCatAddDTO
     * @return
     */
    @Insert({
            "insert into info_cat (pid,name)",
            "values (#{pid,jdbcType=VARCHAR}, ",
            "#{name,jdbcType=VARCHAR})"
    })
    int insertInfoCat(InfoCatAddDTO infoCatAddDTO);

    /**
     * 查看分类下是否有子分类
     *
     * @param pid
     * @return
     */
    @Select({
            "SELECT COUNT(*) countt FROM info_cat ic WHERE ic.pid = #{pid}"
    })
    int selectCountByPid(@Param("pid") Integer pid);

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @Delete({
            "delete from info_cat",
            "where id = #{id}"
    })
    int deleteInfoCat(@Param("id") Integer id);

    @Select({
            "SELECT @@IDENTITY id"
    })
    int selectLastId();
}
