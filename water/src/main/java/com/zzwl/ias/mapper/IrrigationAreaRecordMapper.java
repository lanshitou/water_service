package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IrrigationAreaRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IrrigationAreaRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IrrigationAreaRecord record);

    int insertSelective(IrrigationAreaRecord record);

    IrrigationAreaRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IrrigationAreaRecord record);

    int updateByPrimaryKey(IrrigationAreaRecord record);

    List<IrrigationAreaRecord> selectAllIrrigationAreaRecord();

    List<IrrigationAreaRecord> selectByFarmlandId(int farmlandId);
}