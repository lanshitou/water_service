package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IrrigationAreaRecord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HuXin on 2017/12/14.
 */
@Component
@Mapper
public interface IrrigationAreaRecordExtMapper extends IrrigationAreaRecordMapper {
    @Select({
            "select",
            "id, ias_id, fm_id, name, create_time, is_delete, delete_time, sort_order",
            "from irrigation_area"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "ias_id", property = "iasId", jdbcType = JdbcType.INTEGER),
            @Result(column = "fm_id", property = "fmId", jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "is_delete", property = "isDelete", jdbcType = JdbcType.BIT),
            @Result(column = "delete_time", property = "deleteTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "sort_order", property = "sortOrder", jdbcType = JdbcType.INTEGER)
    })
    List<IrrigationAreaRecord> selectAllIrrigationAreaRecord();


    //    @Insert({
//            "insert into fm_irr_area (id, fm_id, ",
//            "name, valve_id)",
//            "values (#{id,jdbcType=INTEGER}, #{fmId,jdbcType=INTEGER}, ",
//            "#{name,jdbcType=VARCHAR}, #{valveId,jdbcType=BIGINT})"
//    })
//    @Options(useGeneratedKeys = true)
//    int insert(IrrigationAreaRecord record);
//
    @Select({
            "select",
            "id, fm_id, name, valve_id",
            "from fm_irr_area",
            "where fm_id = #{farmlandId,jdbcType=INTEGER}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "fm_id", property = "fmId", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "valve_id", property = "valveId", jdbcType = JdbcType.BIGINT)
    })
    List<IrrigationAreaRecord> selectByFarmlandId(int farmlandId);
}
