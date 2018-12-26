package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IrriFerRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface IrriFerRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IrriFerRecord record);

    int insertSelective(IrriFerRecord record);

    IrriFerRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IrriFerRecord record);

    int updateByPrimaryKey(IrriFerRecord record);

    List<IrriFerRecord> selectAllIrriFerRecords();

    int countIrriFer(int iasId);

    Map<String, Object> selectIrriFerSyStemByIasId(@Param("iasId") Integer iasId);

    int insertIrriFer(IrriFerRecord record);
}