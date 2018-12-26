package com.zzwl.ias.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.*;
import com.zzwl.ias.dto.camera.CameraConfigRequestDTO;
import com.zzwl.ias.dto.camera.CameraDTO;
import com.zzwl.ias.dto.iasystem.*;
import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;
import com.zzwl.ias.dto.warning.WarningStatisticsDTO;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.iasystem.IaSystemManager;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.constant.IaSystemConstant;
import com.zzwl.ias.iasystem.constant.IrrigationConstant;
import com.zzwl.ias.iasystem.constant.PermissionConstant;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTaskState;
import com.zzwl.ias.iasystem.permission.PermissionManager;
import com.zzwl.ias.iasystem.permission.UserIasPermission;
import com.zzwl.ias.iasystem.warning.WarningUtil;
import com.zzwl.ias.mapper.*;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.vo.*;
import com.zzwl.ias.vo.iasystem.IaSystemDetailVo;
import com.zzwl.ias.vo.iasystem.IasConfigVo;
import com.zzwl.ias.vo.iasystem.IrriFerSystemConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zzwl.ias.common.RoleType.IAS_ADMIN;
import static com.zzwl.ias.iasystem.constant.IasObjectType.*;

/**
 * Created by HuXin on 2018/1/9.
 */
@Service
public class IaSystemServiceImpl implements IaSystemService {

    @Autowired
    private IaSystemRecordMapper iaSystemRecordMapper;

    @Autowired
    private FarmlandRecordMapper farmlandRecordMapper;

    @Autowired
    private IrrigationAreaRecordMapper irrigationAreaRecordMapper;

    @Autowired
    private IrriFerRecordMapper irriFerRecordMapper;
    @Autowired
    private IasDeviceRecordMapper iasDeviceRecordMapper;

    @Autowired
    private DeviceRecordMapper deviceRecordMapper;

    @Autowired
    private LatestIrrRecordMapper latestIrrRecordMapper;

    @Autowired
    private IaSystemManager iaSystemManager;

    @Autowired
    private LatestIrrRecordExtMapper latestIrrRecordExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FarmlandRecordExtMapper farmlandRecordExtMapper;

    @Override
    public List<IaSystemRecord> getAllIaSystemRecords() {
        try {
            return iaSystemRecordMapper.selectAllIaSystemRecord();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<IaSystemRecord> getAllWeatherStation() {
        return iaSystemRecordMapper.selectAllWeatherStation();
    }

    @Override
    public List<IaSystemRecord> getIaSystemsByKeyword(String keyword) {
        String sql;
        if (keyword == null || keyword == "") {
            sql = null;
        } else {
            sql = "%" + keyword.trim() + "%";
        }
        return iaSystemRecordMapper.selectIaSystemRecordByKeyword(sql);
    }

    @Override
    public IaSystemRecord getIaSystemById(int id) {
        return iaSystemRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public IaSystemDetailVo getIaSystemVo(int iasId) {
        IaSystemDetailVo iaSystemDetailVo = iaSystemRecordMapper.selectIaSystemDetail(iasId);
        return iaSystemDetailVo;
    }

    @Override
    @Transactional
    public ErrorCode addIaSystem(IasConfigVo iasConfigVo) {
        //向数据库中添加智慧农业系统
        IaSystemRecord iaSystemRecord = new IaSystemRecord();
        iaSystemRecord.setType(iasConfigVo.getType());
        iaSystemRecord.setName(iasConfigVo.getName());
        iaSystemRecord.setAlias(iasConfigVo.getAlias());
        iaSystemRecord.setMode(iasConfigVo.getMode());
        iaSystemRecord.setMaxIrrNum(iasConfigVo.getMaxIrrNum());
        iaSystemRecord.setIsDelete(false);
        iaSystemRecord.setSortOrder(iasConfigVo.getSortOrder());
        iaSystemRecord.setProvinceId(iasConfigVo.getProvinceId());
        iaSystemRecord.setCityId(iasConfigVo.getCityId());
        iaSystemRecord.setDistrictId(iasConfigVo.getDistrictId());
        iaSystemRecord.setTownId(iasConfigVo.getTownId());
        iaSystemRecord.setVillageId(iasConfigVo.getVillageId());
        iaSystemRecord.setAddress(iasConfigVo.getAddress());
        try {
            iaSystemRecordMapper.insert(iaSystemRecord);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        iasConfigVo.setId(iaSystemRecord.getId());
        //向系统中添加智慧农业系统
        ErrorCode errorCode = AppContext.iaSystemManager.addIaSystem(iaSystemRecord);
        //添加水肥一体化
        IrriFerSystemConfigVo irriFerSystemConfigVo = new IrriFerSystemConfigVo();
        irriFerSystemConfigVo.setIasId(iasConfigVo.getId());
        errorCode = AppContext.iaSystemManager.addIrriAndFerSystem(irriFerSystemConfigVo);
        return errorCode;
    }

    @Override
    public ErrorCode delIaSystem(int iasId) {
        try {
            if (iaSystemRecordMapper.deleteByPrimaryKey(iasId) != 1) {
                return ErrorCode.IASYSTEM_NOT_EXIST;
            }
        } catch (DataIntegrityViolationException de) {

            return ErrorCode.IAS_NOT_DELETABLE;
        } catch (Exception e) {

            return ErrorCode.DATABASE_ERROR;
        }

        return ErrorCode.OK;
    }

    @Override
    @Transactional
    public ErrorCode updateIaSystem(IaSystemRecord iaSystemRecord) {
        try {
            if (iaSystemRecordMapper.updateByPrimaryKey(iaSystemRecord) != 1) {
                return ErrorCode.DATABASE_ERROR;
            }
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public List<FarmlandRecord> getAllFarmlandRecords() {
        return farmlandRecordMapper.selectAllFarmlandRecord();
    }

    @Override
    public ErrorCode addFarmland(FarmlandRecord record) {
        try {
            farmlandRecordMapper.insert(record);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode delFarmland(int id) {
        try {
            if (farmlandRecordMapper.deleteByPrimaryKey(id) != 1) {
                return ErrorCode.FARMLAND_NOT_EXIST;
            }
        } catch (DataIntegrityViolationException de) {
            return ErrorCode.FARMLAND_NOT_DELETABLE;
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode updateFarmland(FarmlandRecord farmlandRecord) {
        try {
            if (farmlandRecordMapper.updateByPrimaryKey(farmlandRecord) != 1) {
                return ErrorCode.DATABASE_ERROR;
            }
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public List<IrrigationAreaRecord> getAllIrrigationAreaRecords() {
        return irrigationAreaRecordMapper.selectAllIrrigationAreaRecord();
    }

    @Override
    @Transactional
    public ErrorCode addIrrigationArea(IrrigationAreaRecord irrigationAreaRecord) {
        try {
            irrigationAreaRecordMapper.insert(irrigationAreaRecord);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    @Transactional
    public ErrorCode delIrrigationArea(int id) {
        try {
            irrigationAreaRecordMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    @Transactional
    public ErrorCode updateIrrigationArea(IrrigationAreaRecord irrigationAreaRecord) {
        try {
            irrigationAreaRecordMapper.updateByPrimaryKeySelective(irrigationAreaRecord);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public List<IrriFerRecord> getAllIrriFerRecords() {
        return irriFerRecordMapper.selectAllIrriFerRecords();
    }

    @Override
    public int countIrriFer(int iasId) {
        return irriFerRecordMapper.countIrriFer(iasId);
    }

    @Override
    public ErrorCode addIrriFer(IrriFerRecord irriFerRecord) {
        try {
            irriFerRecordMapper.insertIrriFer(irriFerRecord);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode delIrriFer(int id) {
        try {
            if (irriFerRecordMapper.deleteByPrimaryKey(id) != 1) {
                return ErrorCode.IRRI_FER_SYSTEM_NOT_EXIST;
            }
        } catch (DataIntegrityViolationException de) {
            return ErrorCode.IRRIFER_NOT_DELETABLE;
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public ErrorCode updateIrriFer(IrriFerRecord irriFerRecord) {
        try {
            if (irriFerRecordMapper.updateByPrimaryKeySelective(irriFerRecord) != 1) {
                return ErrorCode.IRRI_FER_SYSTEM_NOT_EXIST;
            }
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public List<IasDeviceRecord> getAllIasDeviceRecords() {
        return AppContext.getBean(IasDeviceRecordExtMapper.class).selectAllIasDeviceRecord();
    }

    @Override
    public IasDeviceRecord getIasDeviceRecordByDevId(long id) {
        return AppContext.getBean(IasDeviceRecordExtMapper.class).selectByDevId(id);
    }

    @Override
    public List<DeviceRecord> getDevListByIasId(int iasId) {
        //获取非控制器类设备列表
        List<DeviceRecord> deviceList = new ArrayList<>();
        List<DeviceRecord> deviceRecords = deviceRecordMapper.selectAllDeviceByIasId(iasId);
        for (DeviceRecord deviceRecord : deviceRecords) {
            if (!DeviceAddr.isMasterController(deviceRecord.getId()) && !DeviceAddr.isSlaveController(deviceRecord.getId())) {
                deviceList.add(deviceRecord);
            }
        }
        return deviceList;
    }

    @Override
    public List<DeviceRecord> getSensorListByIasId(int iasId) {
        //获取传感器
        List<DeviceRecord> sensorList = new ArrayList<>();
        List<DeviceRecord> deviceRecords = deviceRecordMapper.selectAllDeviceByIasId(iasId);
        for (DeviceRecord deviceRecord : deviceRecords) {
            if (DeviceAddr.isSensor(deviceRecord.getId())) {
                sensorList.add(deviceRecord);
            }
        }
        return sensorList;
    }

    @Override
    public Integer selectUsageTypeByDevId(long id) {
        return AppContext.getBean(IasDeviceRecordExtMapper.class).selectUsageTypeByDevId(id);
    }

    @Override
    public ErrorCode addDev(IasDeviceRecord iasDeviceRecord) {
        //往智慧农业系统中添加设备
        //水肥一体化只能加水泵
        if (iasDeviceRecord.getIrriFerId() != null && iasDeviceRecord.getIasDevId() == null) {
            if (DeviceAddr.getDevTypeById(iasDeviceRecord.getDevId()) != 3) {
                return ErrorCode.IRRIFER_ONLY_PUMP;
            }
        }

        try {
            if (iasDeviceRecordMapper.insertSelective(iasDeviceRecord) != 1) {
                return ErrorCode.DATABASE_ERROR;
            }
        } catch (DuplicateKeyException duplicateKeyException) {
            return ErrorCode.DEV_EXISTIN_IAS;
        } catch
        (Exception e) {
            e.printStackTrace();
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    @Transactional
    public ErrorCode delDev(long devId) {
        if (AppContext.getBean(IasDeviceRecordExtMapper.class).countIasDevId(devId) > 0) {
            return ErrorCode.SENSOR_ON_DEV;
        }
        try {
            AppContext.getBean(IasDeviceRecordExtMapper.class).deleteByDevId(devId);
        } catch (Exception e) {
            return ErrorCode.DATABASE_ERROR;
        }
        return ErrorCode.OK;
    }

    @Override
    public List<DevTypeVo> getDevUsage() {
        List<DevTypeVo> usageList = new ArrayList<>();
        DevTypeVo vo1 = new DevTypeVo(1, "水肥一体化系统中的水泵");
        usageList.add(vo1);
        DevTypeVo vo2 = new DevTypeVo(2, "灌溉区中的阀门");
        usageList.add(vo2);
        DevTypeVo vo3 = new DevTypeVo(3, "智慧农业系统中的普通可操作设备");
        usageList.add(vo3);
        DevTypeVo vo4 = new DevTypeVo(4, "农田中的普通可操作设备");
        usageList.add(vo4);
        DevTypeVo vo5 = new DevTypeVo(5, "灌溉区中的普通可操作设备");
        usageList.add(vo5);
        DevTypeVo vo101 = new DevTypeVo(101, "智慧农业系统的传感器");
        usageList.add(vo101);
        DevTypeVo vo102 = new DevTypeVo(102, "农田中的传感器");
        usageList.add(vo102);
        DevTypeVo vo103 = new DevTypeVo(103, "灌溉区中的传感器");
        usageList.add(vo103);
        DevTypeVo vo104 = new DevTypeVo(104, "设备的传感器");
        usageList.add(vo104);
        return usageList;
    }

    @Override
    public List<UserFarmlandRecord> getByUserAndIasWithPermission(int userId, int iasId) {
        return AppContext.getBean(UserFarmlandRecordExtMapper.class).selectByIasIdAndUserId(userId, iasId);
    }

    @Override
    public boolean isAdmin(int userId, int iasId) {
        Integer role = AppContext.getBean(PermissionManager.class).getUserRoleOnIas(userId, iasId);
        if (role == PermissionConstant.IASYSTEM_USER_ADMIN) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isIasOwner(int userId, int iasId) {
        Integer role = AppContext.getBean(PermissionManager.class).getUserRoleOnIas(userId, iasId);
        if (role == PermissionConstant.IASYSTEM_USER_OWNER) {
            return true;
        }
        return false;
    }

    @Override
    public FarmlandVo FarmlandDetail(int iasId, int fmId) {
        List<FarmlandVo> farmlandVos = iaSystemManager.getIaSystemById(iasId).getIaSystemVo().getFarmlands();
        //赋权限
        if (farmlandVos.size() != 0) {
            for (FarmlandVo farmlandVo : farmlandVos) {
                if (farmlandVo.getId() == fmId) {
                    if (iaSystemManager.getIaSystemById(iasId).getWorkMode() == IaSystemConstant.WORK_MODE_AUTO) {
                        for (IrrigationAreaVo irriAreaVo : farmlandVo.getIrrigationAreas()) {
                            if (irriAreaVo.getValves() != null) {
                                for (DeviceDTO valve : irriAreaVo.getValves()) {
                                    valve.setOperable(false);
                                }
                            }
                        }
                    }
                    return farmlandVo;
                }
            }
        }
        return null;
    }

    @Override
    public boolean getValveStatus(int iasId, int fmId) {
        List<FarmlandVo> farmlandVos = iaSystemManager.getIaSystemById(iasId).getIaSystemAreaVo().getFarmlands();
        if (farmlandVos.size() != 0) {
            for (FarmlandVo farmlandVo : farmlandVos) {
                if (farmlandVo.getId() == fmId) {
                    for (IrrigationAreaVo irriAreaVo : farmlandVo.getIrrigationAreas()) {
                        if (irriAreaVo.getTaskState() != null && irriAreaVo.getTaskState().getStatus() >= 2 && irriAreaVo.getTaskState().getStatus() <= 4) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public AlarmConfigRecord getSensorConfig(long DevId, int type) {
        return null;
    }

    @Override
    public int updateIaSystemRecord(IaSystemRecord record) {
        return iaSystemRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public boolean getDcPointStatus(int iasId, int fmId) {
        List<FarmlandVo> farmlandVos = iaSystemManager.getIaSystemById(iasId).getIaSystemVo().getFarmlands();
        //遍历查找农田下的传感器
        if (farmlandVos.size() != 0) {
            for (FarmlandVo farmlandVo : farmlandVos) {
                //农田中的传感器
                if (farmlandVo.getId() == fmId) {
                    if (farmlandVo.getDcPoints() != null) {
                        for (DcPointDTO dcPointDTO : farmlandVo.getDcPoints()) {
                            if (dcPointDTO.getAlarmType() != ThresholdAlarmConfigVo.NORMAL) {
                                return true;
                            }
                        }
                    }
                    if (farmlandVo.getIrrigationAreas() != null) {
                        for (IrrigationAreaVo irrigationAreaVo : farmlandVo.getIrrigationAreas()) {
                            //灌溉区中的传感器
                            if (irrigationAreaVo.getDcPoints() != null) {
                                for (DcPointDTO dcPointDTO : irrigationAreaVo.getDcPoints()) {
                                    if (dcPointDTO.getAlarmType() != ThresholdAlarmConfigVo.NORMAL) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

    @Override
    public DcPointDataVo getDcPointData(long sensorId, int dataType, long startDate, long endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = null;
        Date endTime = null;
        String startDate1 = format.format(startDate);
        String endDate1 = format.format(endDate);
        try {
            startTime = format.parse(startDate1);
            endTime = format.parse(endDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long interval = endDate - startDate;
        int hours = (int) interval / (1000 * 60 * 60);
        List<DataCollectionRecord> collections = null;
        //dataCollectionRecordMapper.selectValueList(sensorId, dataType, startTime, endTime);
        List<Integer> value = new LinkedList<>();
        for (DataCollectionRecord collectionRecord : collections) {
            value.add(collectionRecord.getValue());
        }
        //如果缺少数据,用0x7FFFFFFF补
        if (collections.size() != hours) {
            for (int i = 0, j = 0; i < hours; j++, i++) {
                if (j < collections.size()) {
                    if (collections.get(j).getTime().getTime() - (startDate + 1000 * 60 * 60 * i) > 1000 * 10) {
                        value.add(i, 0x7FFFFFFF);
                        j = j - 1;
                    }
                } else {
                    value.add(i, 0x7FFFFFFF);
                }

            }
        }
        DcPointDataVo dcPointDataVo = new DcPointDataVo();
        dcPointDataVo.setDataType(dataType);
        dcPointDataVo.setStartDate(startDate);
        dcPointDataVo.setEndDate(endDate);
        dcPointDataVo.setCollections(value.toArray());
        return dcPointDataVo;
    }

    @Override
    public int insertFarmlandRecord(FarmlandRecord farmlandRecord) {
        return farmlandRecordMapper.insert(farmlandRecord);
    }

    @Override
    public int upsertLatestIrrRecord(LatestIrrRecord latestIrrRecord) {
        return latestIrrRecordExtMapper.upsert(latestIrrRecord);
    }

    @Override
    public List<LatestIrrRecord> getLatestIrrRecordByIaSystem(int iaSystemId) {
        return latestIrrRecordExtMapper.selectByIaSystem(iaSystemId);
    }

    @Override
    public LatestIrrRecord getLatestIrrRecordByIrrAreaId(int irrAreaId) {
        return latestIrrRecordMapper.selectByIrrAreaId(irrAreaId);
    }

    @Override
    public ErrorCode addCamera(CameraConfigRequestDTO cameraConfigDTO) {
        IaSystem iaSystem = iaSystemManager.getIaSystemById(cameraConfigDTO.getIaSystemId());
        if (iaSystem == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.addCamera(cameraConfigDTO);
    }

    @Override
    public ErrorCode removeCamera(int iasId, int cameraId) {
        IaSystem iaSystem = iaSystemManager.getIaSystemById(iasId);
        if (iaSystem == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }
        return iaSystem.removeCamera(cameraId);
    }

    @Override
    public ErrorCode getAllCameras(int iaSystemId, LinkedList<CameraDTO> cameraDTOS) {
        IaSystem iaSystem = iaSystemManager.getIaSystemById(iaSystemId);
        if (iaSystem == null) {
            return ErrorCode.IASYSTEM_NOT_EXIST;
        }

        return iaSystem.getAllCameras(cameraDTOS);
    }

    //TODO 接口梳理

    /**
     * 获取用户的智慧农业系统列表
     *
     * @param userId 用户ID
     * @return 用户智慧农业系统列表
     */
    @Override
    public LinkedList<IaSystemBasicInfoDTO> getIaSystemsByUser(int userId) {
        LinkedList<IaSystemBasicInfoDTO> iaSystemBasicInfoDTOS = new LinkedList<>();
        List<UserIasRecord> userIasRecords =
                AppContext.getBean(UserIasRecordExtMapper.class).selectByUserId(userId);
        for (UserIasRecord userIasRecord : userIasRecords) {
            IaSystemBasicInfoDTO iaSystemBasicInfoDTO = new IaSystemBasicInfoDTO();
            IaSystemRecord iaSystemRecord = AppContext.getBean(IaSystemRecordMapper.class).selectIaSystemById(userIasRecord.getIasId());
            String name = iaSystemRecord.getName();
            String alias = iaSystemRecord.getAlias();
            iaSystemBasicInfoDTO.setName(alias == null || alias.trim().equals("") ? name : alias);
            iaSystemBasicInfoDTO.setId(userIasRecord.getIasId());
            iaSystemBasicInfoDTOS.add(iaSystemBasicInfoDTO);
        }
        AssertEx.isNotEmpty(iaSystemBasicInfoDTOS);
        return iaSystemBasicInfoDTOS;
    }

    /**
     * 获取智慧农业系统统计信息
     *
     * @param userId 用户ID
     * @param iasId  智慧农业系统ID
     * @return 智慧农业系统统计信息
     */
    @Override
    public WarningStatisticsDTO getIaSystemState(int userId, int iasId) {
        IaSystem iaSystem = iaSystemManager.getIaSystemById(iasId);
        if (iaSystem == null) {
            AssertEx.isOK(ErrorCode.IASYSTEM_EXIST);
        }

        //获取告警统计信息
        LinkedList<WarningDO> warningDOS = AppContext.getBean(WarningDOExtMapper.class).listCurrWarningByIasId(iasId);

        //TODO 根据用户权限对告警进行过滤

        WarningStatisticsDTO warningStatisticsDTO;
        warningStatisticsDTO = WarningUtil.getWarningStat(warningDOS, null);
        warningStatisticsDTO.setId(iasId);

        return warningStatisticsDTO;
    }

    /**
     * 获取智慧农业系统摘要信息
     *
     * @param iasId  智慧农业系统ID
     * @param userId 用户ID
     * @return 智慧农业系统摘要信息
     */
    @Override
    public IaSystemDTO getIaSystemSummary(Integer iasId, Integer userId) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(iasId);
        AssertEx.isTrue(iaSystem != null, ErrorCode.IASYSTEM_NOT_EXIST);
        //获取智慧农业系统摘要信息
        IaSystemDTO iaSystemDTO = iaSystem.getIaSystemInfo(IaSystemConstant.QUERY_TYPE_SUMMARY);
        //获取智慧农业系统告警信息
        LinkedList<WarningDO> warningDOS = AppContext.getBean(WarningDOExtMapper.class).listCurrWarningByIasId(iasId);

        //权限管理，根据权限对农田和告警进行过滤
        UserIasPermission userIasPermission = AppContext.getBean(PermissionManager.class)
                .getUserIasPermission(iasId, userId);
        if (userIasPermission.getRole() == PermissionConstant.IASYSTEM_USER_RETRIEVE) {
            iaSystemDTO.setConfigMode(false);   //普通用户不能对工作模式进行切换
            if (iaSystemDTO.getFarmlands() != null) {
                iaSystemDTO.getFarmlands().removeIf(farmlandDTO -> !userIasPermission.getFarmlands().contains(farmlandDTO.getFarmlandId()));
                Iterator<WarningDO> it = warningDOS.iterator();
                while (it.hasNext()) {
                    WarningDO warningDO = it.next();
                    if (warningDO.getAddrFarmland() != null) {
                        if (!userIasPermission.getFarmlands().contains(warningDO.getAddrFarmland())) {
                            it.remove();
                        }
                    }
                }
            }
        }

        //获取告警统计信息
        WarningStatisticsDTO warningStatisticsDTO;
        warningStatisticsDTO = WarningUtil.getWarningStat(warningDOS, null);
        iaSystemDTO.setWarningStat(warningStatisticsDTO);
        if (iaSystemDTO.getFarmlands() != null) {
            for (FarmlandDTO farmlandDTO : iaSystemDTO.getFarmlands()) {
                warningStatisticsDTO = WarningUtil.getWarningStat(warningDOS, farmlandDTO.getFarmlandId());
                farmlandDTO.setWarningStat(warningStatisticsDTO);
            }
        }

        //获取浇水状态
        if (iaSystemDTO.getFarmlands() != null) {
            if (iaSystem.getWorkMode() == IaSystemConstant.WORK_MODE_AUTO) {
                //自动模式下检查灌溉任务
                for (FarmlandDTO farmlandDTO : iaSystemDTO.getFarmlands()) {
                    farmlandDTO.setIrriStatus(IrrigationConstant.IRRI_STAT_NOT_WORKING);
                    for (IrriAreaDTO irriAreaDTO : farmlandDTO.getIrriAreas()) {
                        IrrigationTaskStateVo irrigationTaskStateVo = irriAreaDTO.getIrriTask();
                        if (irrigationTaskStateVo != null) {
                            int status = irrigationTaskStateVo.getStatus();
                            //农田下任意一个灌溉区正在浇水，则状态设置为浇水，否则设置为等待浇水
                            if (status == NormalIrrigationTaskState.STATE_WAITING) {
                                if (farmlandDTO.getIrriStatus() != IrrigationConstant.IRRI_STAT_WORKING) {
                                    farmlandDTO.setIrriStatus(IrrigationConstant.IRRI_STAT_WAITING);
                                }
                            } else {
                                farmlandDTO.setIrriStatus(IrrigationConstant.IRRI_STAT_WORKING);
                            }
                        }
                    }
                }
            } else {
                //手动模式下根据阀门和水泵的状态获取浇水状态
                for (FarmlandDTO farmlandDTO : iaSystemDTO.getFarmlands()) {
                    farmlandDTO.setIrriStatus(IrrigationConstant.IRRI_STAT_NOT_WORKING);
                    DeviceDTO pump = iaSystemDTO.getIrriAndFerSystem().getPump();
                    if (pump != null) {
                        if (pump.getStatus() == DeviceState.DEV_STATE_OFFLINE) {
                            farmlandDTO.setIrriStatus(IrrigationConstant.IRRI_STAT_UNKNOWN);
                            continue;
                        } else if (pump.getStatus() == DeviceState.DEV_STATE_OFF) {
                            continue;
                        }
                    }

                    //农田下的灌溉区中任意一个阀门开启则设置为正在浇水，如果没有打开的阀门，但是存在离线的阀门，则状态为未知
                    for (IrriAreaDTO irriAreaDTO : farmlandDTO.getIrriAreas()) {
                        LinkedList<DeviceDTO> valves = irriAreaDTO.getValves();
                        if (valves != null) {
                            for (DeviceDTO valve : valves) {
                                if (valve.getStatus() == DeviceState.DEV_STATE_ON) {
                                    farmlandDTO.setIrriStatus(IrrigationConstant.IRRI_STAT_WORKING);
                                } else if (valve.getStatus() == DeviceState.DEV_STATE_OFFLINE) {
                                    if (farmlandDTO.getIrriStatus() == IrrigationConstant.IRRI_STAT_NOT_WORKING) {
                                        farmlandDTO.setIrriStatus(IrrigationConstant.IRRI_STAT_UNKNOWN);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //去除灌溉区信息
            for (FarmlandDTO farmlandDTO : iaSystemDTO.getFarmlands()) {
                farmlandDTO.setIrriAreas(null);
            }
        }

        //自动模式下，设置水泵不能操作
        if (iaSystem.getWorkMode() == IaSystemConstant.WORK_MODE_AUTO) {
            if (iaSystemDTO.getIrriAndFerSystem() != null
                    && iaSystemDTO.getIrriAndFerSystem().getPump() != null) {
                iaSystemDTO.getIrriAndFerSystem().getPump().setOperable(false);
            }
        }

        return iaSystemDTO;
    }

    /**
     * 更新灌溉任务
     *
     * @param requestVo 更新灌溉任务请求
     */
    @Override
    public void updateIrrigationTasks(ReqUpdateNormalIrrigationDTO requestVo) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(requestVo.getIaSystemId());
        AssertEx.isTrue(iaSystem != null, ErrorCode.IASYSTEM_NOT_EXIST);
        iaSystem.updateIrrigationTasks(requestVo);
    }

    /**
     * 获取农田详情
     *
     * @param iaSystemId 智慧农业系统ID
     * @param farmlandId 农田ID
     * @param userId     用户ID
     * @return 农田详情
     */
    @Override
    public FarmlandDTO getFarmlandDetail(Integer iaSystemId, Integer farmlandId, Integer userId) {
        //TODO 权限管理
        UserIasPermission permission = AppContext.getBean(PermissionManager.class).getUserIasPermission(iaSystemId, userId);

        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(iaSystemId);
        AssertEx.isTrue(iaSystem != null, ErrorCode.IASYSTEM_NOT_EXIST);

        FarmlandDTO farmlandDTO = iaSystem.getFarmlandDetail(farmlandId);
        AssertEx.isTrue(farmlandDTO != null, ErrorCode.FARMLAND_NOT_EXIST);

        //对当前没有进行灌溉任务的设备，添加历史灌溉任务
        if (iaSystem.getWorkMode() == IaSystemConstant.WORK_MODE_AUTO) {
            for (IrriAreaDTO irriAreaDTO : farmlandDTO.getIrriAreas()) {
                if (irriAreaDTO.getIrriTask() == null) {
                    IrrigationTaskRecord irrigationTaskRecord =
                            AppContext.getBean(IrrigationTaskRecordExtMapper.class).selectLatestRecord(irriAreaDTO.getIrriAreaId());
                    if (irrigationTaskRecord != null) {
                        IrrigationTaskStateVo irrigationTaskStateVo = new IrrigationTaskStateVo(irrigationTaskRecord);
                        irrigationTaskStateVo.setCreateUser(null);
                        irrigationTaskStateVo.setDeleteUser(null);
                        irriAreaDTO.setIrriTask(irrigationTaskStateVo);
                    }
                }

                if (irriAreaDTO.getIrriTask() != null) {
                    irriAreaDTO.getIrriTask().stateTranslateForApi();
                }
            }
        }

        //自动模式下，设备灌溉阀门不能操作
        if (farmlandDTO.getMode() == IaSystemConstant.WORK_MODE_AUTO) {
            for (IrriAreaDTO areaDTO : farmlandDTO.getIrriAreas()) {
                for (DeviceDTO valve : areaDTO.getValves()) {
                    valve.setOperable(false);
                }
            }
        }

        return farmlandDTO;
    }

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
    @Override
    public LinkedList<IrrigationTaskStateVo> listHistoryIrrigation(Integer userId, Integer iaSystemId, Integer irriAreaId, Integer offset, Integer limit) {
        //TODO 权限管理
        UserIasPermission permission = AppContext.getBean(PermissionManager.class).getUserIasPermission(iaSystemId, userId);

        //获取当前任务
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(iaSystemId);
        AssertEx.isTrue(iaSystem != null, ErrorCode.IASYSTEM_NOT_EXIST);

        LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos = iaSystem.listTaskStateVo();
        if (offset == 0) {
            IrrigationTaskStateVo curr = null;
            for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos) {
                if (irrigationTaskStateVo.getIrriAreaId().equals(irriAreaId)) {
                    curr = irrigationTaskStateVo;
                    break;
                }
            }
            if (curr != null) {
                irrigationTaskStateVos.clear();
                irrigationTaskStateVos.add(curr);
                limit--;
            }
        }

        //获取历史灌溉任务
        LinkedList<IrrigationTaskRecord> records =
                AppContext.getBean(IrrigationTaskRecordExtMapper.class).selectByIrriAreaId(irriAreaId, offset, limit);
        for (IrrigationTaskRecord record : records) {
            irrigationTaskStateVos.add(new IrrigationTaskStateVo(record));
        }

        //获取灌溉任务用户信息及灌溉任务状态转换
        for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos) {
            irrigationTaskStateVo.insertUserInfo();
            irrigationTaskStateVo.stateTranslateForApi();
        }

        return irrigationTaskStateVos;
    }

    /**
     * 设置智慧农业系统工作模式
     *
     * @param userId     用户ID
     * @param iaSystemId 智慧农业系统ID
     * @param mode       工作模式
     */
    @Override
    public void setWorkMode(Integer userId, Integer iaSystemId, Integer mode) {
        AssertEx.isTrue(mode == IaSystemConstant.WORK_MODE_MANUAL || mode == IaSystemConstant.WORK_MODE_AUTO, ErrorCode.INVALID_PARAMS);
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(iaSystemId);
        AssertEx.isTrue(iaSystem != null, ErrorCode.IASYSTEM_NOT_EXIST);

        //权限管理，普通用户不能对工作模式进行切换
        UserIasPermission permission = AppContext.getBean(PermissionManager.class).getUserIasPermission(iaSystemId, userId);
        AssertEx.isTrue(permission.getRole() != PermissionConstant.IASYSTEM_USER_RETRIEVE, ErrorCode.USER_HAS_NO_PERMISSION);

        ErrorCode errorCode = iaSystem.setWorkMode(mode);
        AssertEx.isOK(errorCode);
    }

    @Override
    public List<IasystemAndFarmlandVo> selectUserSystem(Integer userId) {
        return iaSystemRecordMapper.selectUserSystem(userId);
    }

    @Override
    public List<IasystemAndFarmlandVo> selectIasystemAndFarmland(Integer userId, Integer iasId) {
        return iaSystemRecordMapper.selectIasystemAndFarmland(userId, iasId);
    }

    @Override
    @Transactional
    public void insertIaSystemAndFarmland(AddIaSystemAndFarmlandDTO addIaSystemAndFarmlandDTO) {
        iaSystemRecordMapper.deleteUserAndIaSystem(addIaSystemAndFarmlandDTO.getUserId(), addIaSystemAndFarmlandDTO.getIasId());
        if (addIaSystemAndFarmlandDTO.getFids().size() != 0) {
            iaSystemRecordMapper.insertIaSystemAndFarmland(addIaSystemAndFarmlandDTO);
        }
    }

    @Override
    @Transactional
    public void updateIaSystemRole(Integer userId, Integer roleId, Integer iasId) {
        List<Integer> fids = new ArrayList<>();
        if (IAS_ADMIN.getValue() == roleId) {
            List<IasystemAndFarmlandVo> iasystemAndFarmlandVos = iaSystemRecordMapper.selectAllFarmlandInIaSystem(iasId);
            for (IasystemAndFarmlandVo iasystemAndFarmlandVo : iasystemAndFarmlandVos) {
                fids.add(iasystemAndFarmlandVo.getId());
            }
            AddIaSystemAndFarmlandDTO addIaSystemAndFarmlandDTO = new AddIaSystemAndFarmlandDTO();
            addIaSystemAndFarmlandDTO.setIasId(iasId);
            addIaSystemAndFarmlandDTO.setRoleId(roleId);
            addIaSystemAndFarmlandDTO.setUserId(userId);
            addIaSystemAndFarmlandDTO.setFids(fids);
            if (fids.size() > 0) {
                iaSystemRecordMapper.insertIaSystemAndFarmland(addIaSystemAndFarmlandDTO);
            }
        }
        iaSystemRecordMapper.updateIaSystemRole(userId, roleId, iasId);
    }

    @Override
    public PageInfo<IaSystemListVo> selectIaSystemList(IaSystemQueryDTO dto) {
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        return new PageInfo<>(iaSystemRecordMapper.selectIaSystemList(dto));
    }

    @Override
    public Map selectIaSystemDetail(Integer iasId) {
        //查询所有类型icon，提高效率
        List<Map<String, String>> icons = deviceRecordMapper.selectAllIcon();
        Map<String, String> ids = new HashMap<>();
        for (Map map : icons) {
            ids.put(String.valueOf(map.get("cron")), String.valueOf(map.get("code")));
        }
        //水肥一体化
        Map<String, Object> irriFerSystem = irriFerRecordMapper.selectIrriFerSyStemByIasId(iasId);
        if (irriFerSystem != null) {
            List<Map> maps = deviceRecordMapper.selectDeviceByIasIdAndUsageType(IRRI_FER_PUMP, iasId);
            for (Map map : maps) {
                String dev_id = String.valueOf(map.get("dev_id"));
                byte dev_type = (byte) ((Long.valueOf(dev_id)) >> 8);
                map.put("icon", ids.get(String.valueOf(dev_type)));
            }
            irriFerSystem.put("device", maps);
        }

        //农田
        List<Map> farmlands = farmlandRecordExtMapper.selectFarmlandByIasId(iasId);
        for (Map farmland : farmlands) {
            Integer fid = (Integer) farmland.get("id");
            //农田下的设备
            List<Map> farmlandDevices = deviceRecordMapper.selectDeviceByIasIdAndUsageTypeAndFid(FM_NORMAL_DEVICE, iasId, fid);
            for (Map farmlandDevice : farmlandDevices) {
                String dev_id = String.valueOf(farmlandDevice.get("dev_id"));
                byte dev_type = (byte) ((Long.valueOf(dev_id)) >> 8);
                farmlandDevice.put("icon", ids.get(String.valueOf(dev_type)));
            }
            //农田下的传感器
            List<Map> farmlandSensors = deviceRecordMapper.selectDeviceByIasIdAndUsageTypeAndFid(FM_SENSOR, iasId, fid);
            for (Map farmlandSensor : farmlandSensors) {
                String dev_id = String.valueOf(farmlandSensor.get("dev_id"));
                byte dev_type = (byte) ((Long.valueOf(dev_id)) >> 8);
                farmlandSensor.put("icon", ids.get(String.valueOf(dev_type)));
            }
            //农田下的灌溉区
            List<IrrigationAreaRecord> irrigationAreaRecords = irrigationAreaRecordMapper.selectByFarmlandId(fid);
            List<Map> IrrigationAreas = new ArrayList<>();
            for (IrrigationAreaRecord irrigationAreaRecord : irrigationAreaRecords) {
                Map<String, Object> IrrigationAreaMap = new HashMap<>();
                List<Map> devices = deviceRecordMapper.selectDeviceByIasIdAndUsageTypeAndIrriAreaId(AREA_NORMAL_DEVICE, iasId, fid, irrigationAreaRecord.getId());
                for (Map device : devices) {
                    String dev_id = String.valueOf(device.get("dev_id"));
                    byte dev_type = (byte) ((Long.valueOf(dev_id)) >> 8);
                    device.put("icon", ids.get(String.valueOf(dev_type)));
                }
                List<Map> sensors = deviceRecordMapper.selectDeviceByIasIdAndUsageTypeAndIrriAreaId(AREA_SENSOR, iasId, fid, irrigationAreaRecord.getId());
                for (Map sensor : sensors) {
                    String dev_id = String.valueOf(sensor.get("dev_id"));
                    byte dev_type = (byte) ((Long.valueOf(dev_id)) >> 8);
                    sensor.put("icon", ids.get(String.valueOf(dev_type)));
                }
                List<Map> valves = deviceRecordMapper.selectDeviceByIasIdAndUsageTypeAndIrriAreaId(IRRI_VALVE, iasId, fid, irrigationAreaRecord.getId());
                for (Map valve : valves) {
                    String dev_id = String.valueOf(valve.get("dev_id"));
                    byte dev_type = (byte) ((Long.valueOf(dev_id)) >> 8);
                    valve.put("icon", ids.get(String.valueOf(dev_type)));
                }
                IrrigationAreaMap.put("id", irrigationAreaRecord.getId());
                IrrigationAreaMap.put("name", irrigationAreaRecord.getName());
                IrrigationAreaMap.put("devices", devices);
                IrrigationAreaMap.put("sensor", sensors);
                IrrigationAreaMap.put("valves", valves);
                IrrigationAreas.add(IrrigationAreaMap);
            }
            farmland.put("irriArea", IrrigationAreas);
            farmland.put("devices", farmlandDevices);
            farmland.put("sensor", farmlandSensors);
        }
        //公共设备
        Map<String, Object> publicDevice = new HashMap<>();
        List<Map> devices = deviceRecordMapper.selectDeviceByIasIdAndUsageType(IAS_NORMAL_DEVICE, iasId);
        for (Map device : devices) {
            String dev_id = String.valueOf(device.get("dev_id"));
            byte dev_type = (byte) ((Long.valueOf(dev_id)) >> 8);
            device.put("icon", ids.get(String.valueOf(dev_type)));
        }
        publicDevice.put("devices", devices);
        List<Map> sensors = deviceRecordMapper.selectDeviceByIasIdAndUsageType(IAS_SENSOR, iasId);
        for (Map sensor : sensors) {
            String dev_id = String.valueOf(sensor.get("dev_id"));
            byte dev_type = (byte) ((Long.valueOf(dev_id)) >> 8);
            sensor.put("icon", ids.get(String.valueOf(dev_type)));
        }
        publicDevice.put("sensors", sensors);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("publicDevice", publicDevice);
        resultMap.put("irriFerSyStem", irriFerSystem);
        resultMap.put("farmlands", farmlands);
        return resultMap;
    }

    @Override
    public IasDeviceRecord selectDeviceById(Integer id) {
        IasDeviceRecord iasDeviceRecord = deviceRecordMapper.selectDeviceById(id);
        return iasDeviceRecord;
    }

}
