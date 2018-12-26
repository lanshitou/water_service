package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.domain.FarmlandRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by HuXin on 2017/12/14.
 */
@Component
@Mapper
public interface FarmlandRecordExtMapper extends FarmlandRecordMapper {

    @Select({
            "select",
            "id, iasystem_id, name, create_time, is_delete, delete_time, sort_order",
            "from farmland"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "iasystem_id", property = "iasystemId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "is_delete", property = "isDelete", jdbcType = JdbcType.SMALLINT),
            @Result(column = "delete_time", property = "deleteTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER)
    })
    List<FarmlandRecord> selectAllFarmlandRecord();

    @Insert({
            "insert into farmland (id, iasystem_id, ",
            "name )",
            "values (#{id,jdbcType=INTEGER}, #{iasystemId,jdbcType=INTEGER}, ",
            "#{name,jdbcType=VARCHAR} )"
    })
    @Options(useGeneratedKeys = true)
    int insert(FarmlandRecord record);

    @Select({
            "select",
            "id, iasystem_id, name, create_time, delete_time, sort_order",
            "from farmland",
            "where iasystem_id = #{iasystemId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "iasystem_id", property = "iasystemId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "delete_time", property = "deleteTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER),
            @Result(column = "is_delete", property = "isDelete", jdbcType = JdbcType.INTEGER)
    })
    List<FarmlandRecord> selectByIaSystemId(Integer iasystemId);

    @Select({
            "select",
            "id, name, user_id, create_time, delete_time sort_order",
            "from farmland"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "delete_time", property = "deleteTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER)
    })
    List<FarmlandRecord> getFarmlands();

    @Select({
            "select",
            "id, name, user_id, create_time, delete_time, order",
            "from farmland",
            "where user_id = #{userId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "delete_time", property = "deleteTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER)
    })
    List<FarmlandRecord> selectByUserId(int userId);

    @Select({
            "select",
            "d.id as id, d.user_id as user_id, d.name as name, d.config as config",
            "from fm_irr_area, device as d",
            "where fm_irr_area.fm_id = #{farmlandId,jdbcType=INTEGER} and fm_irr_area.valve_id = d.id"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "config", property = "config", jdbcType = JdbcType.VARBINARY)
    })
    List<DeviceRecord> getIrrigationValveByFarmland(int farmlandId);

    @Select({
            "select",
            "d.id as id, d.user_id as user_id, d.name as name, d.config as config",
            "from fm_dc_point, device as d",
            "where fm_dc_point.fm_id = #{farmlandId,jdbcType=INTEGER} and fm_dc_point.sensor_id = d.id"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "config", property = "config", jdbcType = JdbcType.VARBINARY)
    })
    List<DeviceRecord> getDataCollectionSensorByFarmland(int farmlandId);

    @Select({
            "select",
            "d.id as id, d.user_id as user_id, d.name as name, d.config as config",
            "from fm_device_normal, device as d",
            "where fm_device_normal.fm_id = #{farmlandId,jdbcType=INTEGER} and fm_device_normal.dev_id = d.id"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "config", property = "config", jdbcType = JdbcType.VARBINARY)
    })
    List<DeviceRecord> getNormalDeviceByFarmland(int farmlandId);

    @Select({
            "select",
            "d.id as id, d.user_id as user_id, d.name as name, d.config as config",
            "from fm_irr_area, device as d",
            "where fm_irr_area.fm_id = #{farmlandId,jdbcType=INTEGER} and fm_irr_area.valve_id = d.id",

            "union all",

            "select",
            "d.id as id, d.user_id as user_id, d.name as name, d.config as config",
            "from fm_device_normal, device as d",
            "where fm_device_normal.fm_id = #{farmlandId,jdbcType=INTEGER} and fm_device_normal.dev_id = d.id",

            "union all",

            "select",
            "d.id as id, d.user_id as user_id, d.name as name, d.config as config",
            "from fm_dc_point, device as d",
            "where fm_dc_point.fm_id = #{farmlandId,jdbcType=INTEGER} and fm_dc_point.sensor_id = d.id"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "user_id", property = "userId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "config", property = "config", jdbcType = JdbcType.VARBINARY)
    })
    List<DeviceRecord> getDeviceByFarmland(int farmlandId);

    /**
     * 系统下的农田
     *
     * @param iasId
     * @return
     */
    @Select({
            "SELECT f.id,f.name FROM farmland f  WHERE f.iasystem_id = #{iasId}"
    })
    List<Map> selectFarmlandByIasId(@Param("iasId") Integer iasId);
}
