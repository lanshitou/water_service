package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.domain.IasDeviceRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface DeviceRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceRecord record);

    int insertSelective(DeviceRecord record);

    DeviceRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceRecord record);

    int updateByPrimaryKeyWithBLOBs(DeviceRecord record);

    int updateByPrimaryKey(DeviceRecord record);

    List<DeviceRecord> getAllDevicesOrderByAddress();

    List<DeviceRecord> selectAllDeviceByIasId(Integer id);

    List<DeviceRecord> selectAllMasterController(Integer id);

    Integer selectIsUsingControllerCount(long id);

    List<Map> selectDeviceByIasIdAndUsageType(@Param("usageType") Integer usageType, @Param("iasId") Integer iasId);

    /**
     * 农田下的设备，传感器
     *
     * @param usageType
     * @param iasId
     * @param fid
     * @return
     */
    List<Map> selectDeviceByIasIdAndUsageTypeAndFid(@Param("usageType") Integer usageType, @Param("iasId") Integer iasId, @Param("fid") Integer fid);

    List<Map> selectDeviceByIasIdAndUsageTypeAndIrriAreaId(@Param("usageType") Integer usageType, @Param("iasId") Integer iasId, @Param("fid") Integer fid, @Param("irriId") Integer irriId);

    /**
     * 通过id查找设备详情
     *
     * @param id
     * @return
     */
    IasDeviceRecord selectDeviceById(@Param("id") Integer id);

    /**
     * 根据上表类型查找图标
     *
     * @param type
     * @return
     */
    String selectIconByType(Integer type);

    List<Map<String, String>> selectAllIcon();
}