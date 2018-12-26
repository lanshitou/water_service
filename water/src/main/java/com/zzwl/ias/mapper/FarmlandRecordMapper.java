package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.domain.FarmlandRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FarmlandRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FarmlandRecord record);

    int insertSelective(FarmlandRecord record);

    FarmlandRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FarmlandRecord record);

    int updateByPrimaryKey(FarmlandRecord record);

    List<FarmlandRecord> selectAllFarmlandRecord();

    List<FarmlandRecord> selectByIaSystemId(Integer iasystemId);

    List<FarmlandRecord> getFarmlands();

    List<FarmlandRecord> selectByUserId(int userId);

    List<DeviceRecord> getDataCollectionSensorByFarmland(int farmlandId);
}