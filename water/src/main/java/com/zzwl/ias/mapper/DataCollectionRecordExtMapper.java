package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DataCollectionRecord;
import com.zzwl.ias.dto.device.ReqSensorHistoryDataDTO;
import com.zzwl.ias.dto.device.SensorDataDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-25
 * Time: 12:31
 */
@Component
@Mapper
public interface DataCollectionRecordExtMapper extends DataCollectionRecordMapper{
    @Select({
            "select",
            "value, time",
            "from data_collections",
            "where sensor_id = #{devId,jdbcType=BIGINT}",
            "and type = #{dataType,jdbcType=BIGINT}",
            "and collection_type = #{collectionType,jdbcType=BIGINT}",
            "and time > #{startDate,jdbcType=TIMESTAMP}",
            "and time <= #{endDate,jdbcType=TIMESTAMP}",
            "order by time"
    })
    @Results({
            @Result(column="value", property="value", jdbcType=JdbcType.INTEGER),
            @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP),
    })
    LinkedList<SensorDataDTO> listSensorHistoryData(ReqSensorHistoryDataDTO request);
}
