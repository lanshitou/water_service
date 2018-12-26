package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.CameraDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-26
 * Time: 8:16
 */
@Component
@Mapper
public interface CameraDOExtMapper extends CameraDOMapper {
    @Insert({
            "insert into camera (id, name, ",
            "sn, code, url_hls, ",
            "url_hls_hd, rtmp, ",
            "rtmp_hd, ws_addr, ",
            "ias_id, farmland_id, ",
            "area_id, location, ",
            "capability)",
            "values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, ",
            "#{sn,jdbcType=VARCHAR}, #{code,jdbcType=VARCHAR}, #{urlHls,jdbcType=VARCHAR}, ",
            "#{urlHlsHd,jdbcType=VARCHAR}, #{rtmp,jdbcType=VARCHAR}, ",
            "#{rtmpHd,jdbcType=VARCHAR}, #{wsAddr,jdbcType=VARCHAR}, ",
            "#{iasId,jdbcType=INTEGER}, #{farmlandId,jdbcType=INTEGER}, ",
            "#{areaId,jdbcType=INTEGER}, #{location,jdbcType=INTEGER}, ",
            "#{capability,jdbcType=OTHER})"
    })
    @Options(useGeneratedKeys = true)
    int insert(CameraDO record);

    @Select({
            "select",
            "id, name, sn, code, url_hls, url_hls_hd, rtmp, rtmp_hd, ws_addr, ias_id, farmland_id, ",
            "area_id, location, capability",
            "from camera"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sn", property = "sn", jdbcType = JdbcType.VARCHAR),
            @Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url_hls", property = "urlHls", jdbcType = JdbcType.VARCHAR),
            @Result(column = "url_hls_hd", property = "urlHlsHd", jdbcType = JdbcType.VARCHAR),
            @Result(column = "rtmp", property = "rtmp", jdbcType = JdbcType.VARCHAR),
            @Result(column = "rtmp_hd", property = "rtmpHd", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ws_addr", property = "wsAddr", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "farmland_id", property = "farmlandId", jdbcType = JdbcType.INTEGER),
            @Result(column = "area_id", property = "areaId", jdbcType = JdbcType.INTEGER),
            @Result(column = "location", property = "location", jdbcType = JdbcType.INTEGER),
            @Result(column = "capability", property = "capability", jdbcType = JdbcType.OTHER)
    })
    List<CameraDO> getAllCameras();
}
