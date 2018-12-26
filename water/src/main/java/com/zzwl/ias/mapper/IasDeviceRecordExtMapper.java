package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IasDeviceRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HuXin on 2018/4/10.
 */
@Component
@Mapper
public interface IasDeviceRecordExtMapper extends IasDeviceRecordMapper {
    @Select({
            "select",
            "id, dev_id, ias_id, farmland_id, irri_area_id, ias_dev_id, irri_fer_id, name, ",
            "usage_type, user_id, sort_order",
            "from ias_device"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "dev_id", property = "devId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "farmland_id", property = "farmlandId", jdbcType = JdbcType.INTEGER),
            @Result(column = "irri_area_id", property = "irriAreaId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_dev_id", property = "iasDevId", jdbcType = JdbcType.BIGINT),
            @Result(column = "irri_fer_id", property = "irriFerId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "usage_type", property = "usageType", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER)
    })
    List<IasDeviceRecord> selectAllIasDeviceRecord();

    @Select({
            "select",
            "id, dev_id, ias_id, farmland_id, irri_area_id, ias_dev_id, irri_fer_id, name, ",
            "usage_type, user_id, sort_order",
            "from ias_device",
            "where dev_id = #{devId,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "dev_id", property = "devId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "farmland_id", property = "farmlandId", jdbcType = JdbcType.INTEGER),
            @Result(column = "irri_area_id", property = "irriAreaId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_dev_id", property = "iasDevId", jdbcType = JdbcType.BIGINT),
            @Result(column = "irri_fer_id", property = "irriFerId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "usage_type", property = "usageType", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER)
    })
    IasDeviceRecord selectByDevId(Long devId);

    @Select({
            "select",
            "id, dev_id, ias_id, farmland_id, irri_area_id, ias_dev_id, irri_fer_id, name, ",
            "usage_type, user_id, sort_order",
            "from ias_device",
            "where ias_id = #{iasId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "dev_id", property = "devId", jdbcType = JdbcType.BIGINT),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "farmland_id", property = "farmlandId", jdbcType = JdbcType.INTEGER),
            @Result(column = "irri_area_id", property = "irriAreaId", jdbcType = JdbcType.INTEGER),
            @Result(column = "ias_dev_id", property = "iasDevId", jdbcType = JdbcType.BIGINT),
            @Result(column = "irri_fer_id", property = "irriFerId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "usage_type", property = "usageType", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER)
    })
    List<IasDeviceRecord> selectByIasId(Integer iasId);

    @Select({
            "select",
            "usage_type",
            "from ias_device where dev_id=#{devId}"
    })
    Integer selectUsageTypeByDevId(Long devId);

    @Select({
            "select",
            "count(*)",
            "from ias_device where ias_dev_id = #{devId}"
    })
    Integer countIasDevId(Long devId);

    @Delete({
            "delete from ias_device where ",
            "dev_id = #{devId,jdbcType=INTEGER}"
    })
    int deleteByDevId(Long devId);

}
