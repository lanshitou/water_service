package com.zzwl.ias.service;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.*;
import com.zzwl.ias.dto.camera.CameraConfigRequestDTO;
import com.zzwl.ias.dto.camera.CameraDTO;
import com.zzwl.ias.dto.iasystem.*;
import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;
import com.zzwl.ias.dto.warning.WarningStatisticsDTO;
import com.zzwl.ias.vo.*;
import com.zzwl.ias.vo.iasystem.IaSystemDetailVo;
import com.zzwl.ias.vo.iasystem.IasConfigVo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description: 对IaSystem相关的数据库表进行操作
 * User: HuXin
 * Date: 2018-05-17
 * Time: 16:40
 */
public interface IaSystemService {
    //智慧农业系统增删改查接口
    List<IaSystemRecord> getAllIaSystemRecords();

    List<IaSystemRecord> getAllWeatherStation();

    List<IaSystemRecord> getIaSystemsByKeyword(String keyword);

    IaSystemRecord getIaSystemById(int id);

    IaSystemDetailVo getIaSystemVo(int iasId);

    ErrorCode addIaSystem(IasConfigVo iasConfigVo);

    ErrorCode delIaSystem(int iasId);

    ErrorCode updateIaSystem(IaSystemRecord iaSystemRecord);

    //农田增删改查接口
    List<FarmlandRecord> getAllFarmlandRecords();

    ErrorCode addFarmland(FarmlandRecord record);

    ErrorCode delFarmland(int id);

    ErrorCode updateFarmland(FarmlandRecord farmlandRecord);

    //灌溉区增删改查接口
    List<IrrigationAreaRecord> getAllIrrigationAreaRecords();

    ErrorCode addIrrigationArea(IrrigationAreaRecord irrigationAreaRecord);

    ErrorCode delIrrigationArea(int id);

    ErrorCode updateIrrigationArea(IrrigationAreaRecord irrigationAreaRecord);

    //水肥一体化增删改查接口
    List<IrriFerRecord> getAllIrriFerRecords();

    int countIrriFer(int iasId);

    ErrorCode addIrriFer(IrriFerRecord irriFerRecord);

    ErrorCode delIrriFer(int id);

    ErrorCode updateIrriFer(IrriFerRecord irriFerRecord);

    //设备增删改查接口
    List<IasDeviceRecord> getAllIasDeviceRecords();

    IasDeviceRecord getIasDeviceRecordByDevId(long id);

    List<DeviceRecord> getDevListByIasId(int iasId);

    List<DeviceRecord> getSensorListByIasId(int iasId);

    Integer selectUsageTypeByDevId(long id);

    ErrorCode addDev(IasDeviceRecord iasDeviceRecord);

    ErrorCode delDev(long id);

    List<DevTypeVo> getDevUsage();

    AlarmConfigRecord getSensorConfig(long devId, int type);

    //权限相关接口
    List<UserFarmlandRecord> getByUserAndIasWithPermission(int userId, int iasId);

    boolean isAdmin(int userId, int iasId);

    boolean isIasOwner(int userId, int iasId);

    FarmlandVo FarmlandDetail(int iasId, int fmId);

    //    List<FarmlandVo> getFmAndIrriArea(int iasId);
    boolean getValveStatus(int iasId, int fmId);

    int updateIaSystemRecord(IaSystemRecord record);

    boolean getDcPointStatus(int iasId, int fmId);

    DcPointDataVo getDcPointData(long sensorId, int dataType, long startDate, long endDate);
//    List<FarmlandRecord> getFarmlandRecordByIaSystemId(int iaSystemId);
//    List<DcPointRecord> getDcPointRecordByIaSystemId(int iaSystemId);
//    IasPumpRecord getPumpByIaSystemId(int iaSystemId);
//
//    List<IaSystemVo> getIaSystemByUser(int userId);
//    IaSystemVo getIaSystemById(int userId, int id);
//    FarmlandVo getFarmlandById(int userId, int iaSystemId, int farmlandId);
//    ErrorCode updateIrrigationTasks(IrrigationRequestVo irrigationRequestVo);

    //数据库操作相关接口
//    int insertDcPointRecord(DcPointRecord dcPointRecord);
//    int insertIasPumpRecord(IasPumpRecord iasPumpRecord);
    int insertFarmlandRecord(FarmlandRecord farmlandRecord);

    //    int insertIrriAreaRecord(IrrigationAreaRecord irrigationAreaRecord);
//    int insertNormalDeviceRecord(NormalDeviceRecord normalDeviceRecord);
    int upsertLatestIrrRecord(LatestIrrRecord latestIrrRecord);

    List<LatestIrrRecord> getLatestIrrRecordByIaSystem(int iaSystemId);

    LatestIrrRecord getLatestIrrRecordByIrrAreaId(int irrAreaId);
//
//
//    ErrorCode addIaSystem(IasConfigVo iasConfigVo);
//    ErrorCode setIaSystemWorkMode(int userId, int iaSystemId, int workMode);
//    ErrorCode addDcPointForIaSystem(int iaSystemId, DcPointConfigVo dcPointConfigVo);
//    ErrorCode addPump(int iaSystemId, PumpConfigVo pumpConfigVo);
//    ErrorCode addFarmland(int iaSystemId, FarmlandConfigVo farmlandConfigVo);
//    ErrorCode addDcPointForFarmland(int iaSystemId, int farmlandId, DcPointConfigVo dcPointConfigVo);
//    ErrorCode addIrriArea(int iaSystemId, int farmlandId, IrriAreaConfigVo irriAreaConfigVo);
//    ErrorCode addNormalDevice(int iaSystemId, int farmlandId, NormalDevConfigVo normalDevConfigVo);
//    ErrorCode addUser(IaSystemUserConfigVo iaSystemUserConfigVo);
//    void operateDevice(ReqOperateDevice request);
//    ErrorCode getIrrigationTask(int userId, int iaSystemId, LinkedList<IrrigationTaskStateVo> taskStateVos);

    ErrorCode addCamera(CameraConfigRequestDTO cameraConfigDTO);

    ErrorCode removeCamera(int iasId, int cameraId);

    ErrorCode getAllCameras(int iaSystemId, LinkedList<CameraDTO> cameraDTOS);

    //TODO 接口梳理

    /**
     * 获取用户的智慧农业系统列表
     *
     * @param userId 用户ID
     * @return 用户智慧农业系统列表
     */
    LinkedList<IaSystemBasicInfoDTO> getIaSystemsByUser(int userId);

    /**
     * 获取智慧农业系统统计信息
     *
     * @param userId 用户ID
     * @return 智慧农业系统统计信息
     */
    WarningStatisticsDTO getIaSystemState(int userId, int iasId);

    /**
     * 更新灌溉任务
     *
     * @param requestVo 更新灌溉任务请求
     */
    void updateIrrigationTasks(ReqUpdateNormalIrrigationDTO requestVo);

    /**
     * 获取智慧农业系统摘要信息
     *
     * @param iasId  智慧农业系统ID
     * @param userId 用户ID
     * @return 智慧农业系统摘要信息
     */
    IaSystemDTO getIaSystemSummary(Integer iasId, Integer userId);

    /**
     * 获取农田详情
     *
     * @param iaSystemId 智慧农业系统ID
     * @param farmlandId 农田ID
     * @param userId     用户ID
     * @return 农田详情
     */
    FarmlandDTO getFarmlandDetail(Integer iaSystemId, Integer farmlandId, Integer userId);

    /**
     * 获取历史灌溉任务
     *
     * @param userId     用户ID
     * @param iaSystemId 智慧农业系统ID
     * @param irriAreaId 灌溉区ID
     * @param offset     偏移值
     * @param limit      数量
     * @return 历史灌溉任务
     */
    LinkedList<IrrigationTaskStateVo> listHistoryIrrigation(Integer userId, Integer iaSystemId, Integer irriAreaId, Integer offset, Integer limit);

    /**
     * 设置智慧农业系统工作模式
     *
     * @param userId     用户ID
     * @param iaSystemId 智慧农业系统ID
     * @param mode       工作模式
     */
    void setWorkMode(Integer userId, Integer iaSystemId, Integer mode);

    /**
     * 查找用户下的系统
     */
    List<IasystemAndFarmlandVo> selectUserSystem(Integer userId);

    /**
     * 查找系统下的农田
     *
     * @param userId
     * @param iasId
     * @return
     */
    List<IasystemAndFarmlandVo> selectIasystemAndFarmland(Integer userId, Integer iasId);

    /**
     * 用户绑定农田
     *
     * @param addIaSystemAndFarmlandDTO
     */
    void insertIaSystemAndFarmland(AddIaSystemAndFarmlandDTO addIaSystemAndFarmlandDTO);

    /**
     * 修改系统权限
     *
     * @param userId
     * @param roleId
     * @param iasId
     */
    void updateIaSystemRole(Integer userId, Integer roleId, Integer iasId);

    /**
     * 系统列表
     *
     * @return
     */
    PageInfo<IaSystemListVo> selectIaSystemList(IaSystemQueryDTO dto);

    /**
     * 系统详情
     *
     * @param iasId
     * @return
     */
    Map selectIaSystemDetail(Integer iasId);

    /**
     * 通过id查找设备详情
     *
     * @param id
     * @return
     */
    IasDeviceRecord selectDeviceById(Integer id);
}
