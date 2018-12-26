package com.zzwl.ias.mapper;

import com.zzwl.ias.domain.IaSystemRecord;
import com.zzwl.ias.dto.iasystem.AddIaSystemAndFarmlandDTO;
import com.zzwl.ias.dto.iasystem.IaSystemQueryDTO;
import com.zzwl.ias.vo.IaSystemListVo;
import com.zzwl.ias.vo.IasystemAndFarmlandVo;
import com.zzwl.ias.vo.iasystem.IaSystemDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface IaSystemRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IaSystemRecord record);

    int insertSelective(IaSystemRecord record);

    IaSystemRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IaSystemRecord record);

    int updateByPrimaryKey(IaSystemRecord record);

    List<IaSystemRecord> selectAllIaSystemRecord();

    List<IaSystemRecord> selectAllWeatherStation();

    IaSystemRecord selectIaSystemById(Integer id);

    List<IaSystemRecord> selectIaSystemRecordByKeyword(@Param("keyword") String keyword);

    /**
     * 查找用户下的系统
     */
    List<IasystemAndFarmlandVo> selectUserSystem(@Param("userId") Integer userId);

    /**
     * 查找系统下的农田
     *
     * @param userId
     * @param iasId
     * @return
     */
    List<IasystemAndFarmlandVo> selectIasystemAndFarmland(@Param("userId") Integer userId, @Param("iasId") Integer iasId);

    /**
     * 用户绑定农田
     *
     * @param addIaSystemAndFarmlandDTO
     */
    void insertIaSystemAndFarmland(AddIaSystemAndFarmlandDTO addIaSystemAndFarmlandDTO);

    /**
     * 判断该农田是否已绑定
     *
     * @param userId
     * @param fid
     * @return
     */
    int countIaSystemAndFarmland(@Param("userId") Integer userId, @Param("fid") Integer fid, @Param("iasId") Integer iasId);

    /**
     * 修改系统权限
     *
     * @param userId
     * @param roleId
     * @param iasId
     */
    void updateIaSystemRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId, @Param("iasId") Integer iasId);

    /**
     * 删除用户下的农田
     *
     * @param userId
     * @param iasId
     */
    void deleteUserAndIaSystem(@Param("userId") Integer userId, @Param("iasId") Integer iasId);

    /**
     * 系统下的所有农田
     *
     * @param iasId
     * @return
     */
    List<IasystemAndFarmlandVo> selectAllFarmlandInIaSystem(@Param("iasId") Integer iasId);

    /**
     * 系统列表
     *
     * @return
     */
    List<IaSystemListVo> selectIaSystemList(IaSystemQueryDTO iaSystemQueryDTO);

    /**
     * 系统详情（修改）
     *
     * @param iasId
     * @return
     */
    IaSystemDetailVo selectIaSystemDetail(@Param("iasId") Integer iasId);
}