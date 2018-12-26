package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created by Lvpin on 2018/12/11.
 */
@Component
@Mapper
public interface ImageExtMapper extends ImageMapper {
    @Insert({
            "insert into image (id, name, ",
            "url, info )",
            "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{url,jdbcType=VARCHAR}, #{info,jdbcType=VARCHAR})"
    })
    int insertImage(Image record);

    @Select({
            "select id, name, url from image where name = #{name}"
    })
    Image selectByName(@Param("name") String name);
}
