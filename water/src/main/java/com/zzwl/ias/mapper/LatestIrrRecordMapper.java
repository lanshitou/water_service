package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.LatestIrrRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface LatestIrrRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table latest_irri_log
     *
     * @mbg.generated
     */
    int insert(LatestIrrRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table latest_irri_log
     *
     * @mbg.generated
     */
    int insertSelective(LatestIrrRecord record);

    LatestIrrRecord selectByIrrAreaId(int irrAreaId);
}