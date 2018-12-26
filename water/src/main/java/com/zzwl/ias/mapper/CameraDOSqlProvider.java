package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.CameraDO;
import org.apache.ibatis.jdbc.SQL;

public class CameraDOSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table camera
     *
     * @mbg.generated
     */
    public String insertSelective(CameraDO record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("camera");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }

        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }

        if (record.getSn() != null) {
            sql.VALUES("sn", "#{sn,jdbcType=VARCHAR}");
        }

        if (record.getCode() != null) {
            sql.VALUES("code", "#{code,jdbcType=VARCHAR}");
        }

        if (record.getUrlHls() != null) {
            sql.VALUES("url_hls", "#{urlHls,jdbcType=VARCHAR}");
        }

        if (record.getUrlHlsHd() != null) {
            sql.VALUES("url_hls_hd", "#{urlHlsHd,jdbcType=VARCHAR}");
        }

        if (record.getRtmp() != null) {
            sql.VALUES("rtmp", "#{rtmp,jdbcType=VARCHAR}");
        }

        if (record.getRtmpHd() != null) {
            sql.VALUES("rtmp_hd", "#{rtmpHd,jdbcType=VARCHAR}");
        }

        if (record.getWsAddr() != null) {
            sql.VALUES("ws_addr", "#{wsAddr,jdbcType=VARCHAR}");
        }

        if (record.getIasId() != null) {
            sql.VALUES("ias_id", "#{iasId,jdbcType=INTEGER}");
        }

        if (record.getFarmlandId() != null) {
            sql.VALUES("farmland_id", "#{farmlandId,jdbcType=INTEGER}");
        }

        if (record.getAreaId() != null) {
            sql.VALUES("area_id", "#{areaId,jdbcType=INTEGER}");
        }

        if (record.getLocation() != null) {
            sql.VALUES("location", "#{location,jdbcType=INTEGER}");
        }

        if (record.getCapability() != null) {
            sql.VALUES("capability", "#{capability,jdbcType=OTHER}");
        }

        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table camera
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(CameraDO record) {
        SQL sql = new SQL();
        sql.UPDATE("camera");

        if (record.getName() != null) {
            sql.SET("name = #{name,jdbcType=VARCHAR}");
        }

        if (record.getSn() != null) {
            sql.SET("sn = #{sn,jdbcType=VARCHAR}");
        }

        if (record.getCode() != null) {
            sql.SET("code = #{code,jdbcType=VARCHAR}");
        }

        if (record.getUrlHls() != null) {
            sql.SET("url_hls = #{urlHls,jdbcType=VARCHAR}");
        }

        if (record.getUrlHlsHd() != null) {
            sql.SET("url_hls_hd = #{urlHlsHd,jdbcType=VARCHAR}");
        }

        if (record.getRtmp() != null) {
            sql.SET("rtmp = #{rtmp,jdbcType=VARCHAR}");
        }

        if (record.getRtmpHd() != null) {
            sql.SET("rtmp_hd = #{rtmpHd,jdbcType=VARCHAR}");
        }

        if (record.getWsAddr() != null) {
            sql.SET("ws_addr = #{wsAddr,jdbcType=VARCHAR}");
        }

        if (record.getIasId() != null) {
            sql.SET("ias_id = #{iasId,jdbcType=INTEGER}");
        }

        if (record.getFarmlandId() != null) {
            sql.SET("farmland_id = #{farmlandId,jdbcType=INTEGER}");
        }

        if (record.getAreaId() != null) {
            sql.SET("area_id = #{areaId,jdbcType=INTEGER}");
        }

        if (record.getLocation() != null) {
            sql.SET("location = #{location,jdbcType=INTEGER}");
        }

        if (record.getCapability() != null) {
            sql.SET("capability = #{capability,jdbcType=OTHER}");
        }

        sql.WHERE("id = #{id,jdbcType=INTEGER}");

        return sql.toString();
    }
}