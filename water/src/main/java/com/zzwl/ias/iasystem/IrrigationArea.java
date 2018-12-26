package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.domain.IrrigationAreaRecord;
import com.zzwl.ias.domain.IrrigationTaskRecord;
import com.zzwl.ias.domain.User;
import com.zzwl.ias.dto.iasystem.DeviceDTO;
import com.zzwl.ias.dto.iasystem.IrriAreaDTO;
import com.zzwl.ias.iasystem.common.DeviceAddr;
import com.zzwl.ias.iasystem.constant.IaSystemConstant;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTaskState;
import com.zzwl.ias.mapper.IrrigationTaskRecordExtMapper;
import com.zzwl.ias.mapper.UserMapper;
import com.zzwl.ias.vo.IrrigationAreaVo;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import com.zzwl.ias.vo.UserBasicInfoVo;
import org.apache.shiro.SecurityUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by HuXin on 2017/12/14.
 */
public class IrrigationArea extends IasNormalObject {
    IrrigationTaskRecordExtMapper irrigationTaskRecordMapper;
    UserMapper userMapper;
    private IrrigationAreaRecord irrigationAreaRecord;
    public List<IasOpDevice> valves = new ArrayList<>();

    public IrrigationArea(IrrigationAreaRecord irrigationAreaRecord) {
        super();
        this.irrigationAreaRecord = irrigationAreaRecord;
        setAddr(IasObjectAddr.createIasIrriAreaAddr(irrigationAreaRecord.getIasId(),
                irrigationAreaRecord.getFmId(), irrigationAreaRecord.getId()));

        irrigationTaskRecordMapper = AppContext.getBean(IrrigationTaskRecordExtMapper.class);
        userMapper = AppContext.getBean(UserMapper.class);
    }

    public IrrigationAreaVo getIrrigationAreaVo() {
        IrrigationAreaVo irrigationAreaVo = new IrrigationAreaVo(irrigationAreaRecord);
        irrigationAreaVo.setValves(getValveVos());
        irrigationAreaVo.setNormalDevices(getNormalDeviceVos());
        if (SecurityUtils.getSubject().getPrincipal() == null) {
            irrigationAreaVo.setDcPoints(getSensorPointVos());
        } else {
            irrigationAreaVo.setDcPoints(getDcPointVos());
            irrigationAreaVo.setTaskState(getLatestTaskStateVo(irrigationAreaRecord));
        }
        return irrigationAreaVo;
    }

    public IrrigationAreaVo getIrriAreaVo() {
        IrrigationAreaVo irrigationAreaVo = new IrrigationAreaVo(irrigationAreaRecord);
        irrigationAreaVo.setTaskState(getLatestTaskStateVo(irrigationAreaRecord));
        return irrigationAreaVo;
    }

    @Override
    public int getId() {
        return irrigationAreaRecord.getId();
    }

    public void setName(String name) {
        irrigationAreaRecord.setName(name);
    }

    public String getName() {
        return irrigationAreaRecord.getName();
    }

    @Override
    public int getSortOrder() {
        return irrigationAreaRecord.getSortOrder();
    }

    public void setSortOrder(int sortOrder) {
        irrigationAreaRecord.setSortOrder(sortOrder);
    }

    public ErrorCode addValve(IasOpDevice iasOpDevice) {
        if (!DeviceAddr.isValve(iasOpDevice.getDevId())) {
            return ErrorCode.DEV_NOT_VALVE;
        }
        for (IasOpDevice valve : valves) {
            if (valve.getId() == iasOpDevice.getId()) {
                return ErrorCode.IAS_DEVICE_EXIST;
            }
        }
        valves.add(iasOpDevice);
        return ErrorCode.OK;
    }

    public ErrorCode delValve(IasDeviceRecord iasDeviceRecord) {
        if (!DeviceAddr.isValve(iasDeviceRecord.getDevId())) {
            return ErrorCode.DEV_NOT_VALVE;
        }
        for (IasOpDevice valve : valves) {
            if (valve.getDevId() == iasDeviceRecord.getDevId()) {
                valves.remove(valve);
                return ErrorCode.OK;
            }
        }
        return ErrorCode.IAS_DEVICE_NOT_EXIST;
    }

    public Integer[] getValveIds() {
        if (valves.size() == 0) {
            return null;
        } else {
            Integer[] valveIds = new Integer[valves.size()];
            int i = 0;
            for (IasOpDevice valve : valves) {
                valveIds[i] = valve.getId();
                i++;
            }
            return valveIds;
        }
    }

    private LinkedList<DeviceDTO> getValveVos() {
        if (valves.size() != 0) {
            LinkedList<DeviceDTO> deviceVos = new LinkedList<>();
            for (IasOpDevice valve : valves) {
                deviceVos.add(valve.getNormalDeviceVo());
            }
            return deviceVos;
        }
        return null;
    }

    private IrrigationTaskStateVo getLatestTaskStateVo(IrrigationAreaRecord irrigationAreaRecord) {
        //系统中所有的任务
        List<IrrigationTaskStateVo> iasIrrigationTaskStateVos = AppContext.iaSystemManager.getIaSystemById(irrigationAreaRecord.getIasId()).listTaskStateVo();
        IrrigationTaskStateVo irrigationTaskStateVo = new IrrigationTaskStateVo();
        //正在运行的任务中是否有
        IrrigationTaskStateVo result = null;
        do {
            for (IrrigationTaskStateVo irrigationTaskStateVo1 : iasIrrigationTaskStateVos) {
                if (irrigationTaskStateVo1.getIrriAreaId() == irrigationAreaRecord.getId()) {
                    //listTaskStateVo()得到的是预计的时间,这是正在运行的任务,排队中的为null,正在浇水的实际浇水时间= finishTime - startTime,所以这里的finishTime为当前时间即可
                    if (irrigationTaskStateVo1.getStatus() == NormalIrrigationTaskState.STATE_WAITING) {
                        irrigationTaskStateVo1.setStartTime(null);
                        irrigationTaskStateVo1.setFinishTime(null);
                    } else if (irrigationTaskStateVo1.getStatus() == NormalIrrigationTaskState.STATE_IRRIGATING) {
                        irrigationTaskStateVo1.setFinishTime(Calendar.getInstance().getTime());
                    } else {
                        irrigationTaskStateVo1.setStartTime(null);
                        irrigationTaskStateVo1.setFinishTime(null);
                    }
                    result = irrigationTaskStateVo1;
                    break;
                }
            }
            if (result != null) {
                break;
            }
            //到数据库查任务
            IrrigationTaskRecord irrigationTaskRecord = irrigationTaskRecordMapper.selectLatestRecord(irrigationAreaRecord.getId());
            if (irrigationTaskRecord != null) {
                irrigationTaskStateVo.setFarmlandId(irrigationTaskRecord.getFarmlandId());
                irrigationTaskStateVo.setIrriAreaId(irrigationTaskRecord.getIrriAreaId());
                //创建任务的用户 和 结束任务的用户
                User user1 = userMapper.selectByUserId(irrigationTaskRecord.getCreateUser());
                UserBasicInfoVo createUser = new UserBasicInfoVo(user1);
                irrigationTaskStateVo.setCreateUser(createUser);
                if (irrigationTaskRecord.getDeleteUser() == null) {
                    irrigationTaskStateVo.setDeleteUser(null);
                } else {
                    User user2 = userMapper.selectByUserId(irrigationTaskRecord.getDeleteUser());
                    UserBasicInfoVo deleteUser = new UserBasicInfoVo(user2);
                    irrigationTaskStateVo.setDeleteUser(deleteUser);
                }
                //时间
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(irrigationTaskRecord.getCreateTime());
                irrigationTaskStateVo.setAddTime(calendar1.getTime());
                if (irrigationTaskRecord.getStartTime() != null) {
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(irrigationTaskRecord.getStartTime());
                    irrigationTaskStateVo.setStartTime(calendar2.getTime());
                }
                if (irrigationTaskRecord.getResult() == 0) {
                    Calendar calendar3 = Calendar.getInstance();
                    if (irrigationTaskRecord.getStartTime() == null) {
                        irrigationTaskStateVo.setStartTime(null);
                        irrigationTaskStateVo.setFinishTime(null);
                    } else {
                        calendar3.setTimeInMillis(irrigationTaskRecord.getStartTime().getTime() + irrigationTaskRecord.getExpDuration() * 1000);
                        irrigationTaskStateVo.setFinishTime(calendar3.getTime());
                    }
                } else if (irrigationTaskRecord.getResult() == 100) {
                    Calendar calendar4 = Calendar.getInstance();
                    calendar4.setTime(irrigationTaskRecord.getFinishTime());
                    irrigationTaskStateVo.setFinishTime(calendar4.getTime());
                } else {
                    irrigationTaskStateVo.setFinishTime(null);
                }

                //
                irrigationTaskStateVo.setExpDuration(irrigationTaskRecord.getExpDuration());
                irrigationTaskStateVo.setStatus(irrigationTaskRecord.getStatus());
                irrigationTaskStateVo.setResult(irrigationTaskRecord.getResult());
                result = irrigationTaskStateVo;
                break;
            }
        } while (false);

        if (result == null) {
            return null;
        }
        int status = result.getStatus();
        switch (status) {
            case NormalIrrigationTaskState.STATE_WAITING:
                status = 1;
                break;
            case NormalIrrigationTaskState.STATE_STARTING_VALVES:
            case NormalIrrigationTaskState.STATE_WAITING_PUMP_STARTED:
                status = 2;
                break;
            case NormalIrrigationTaskState.STATE_IRRIGATING:
                status = 3;
                break;
            case NormalIrrigationTaskState.STATE_WAITING_FINISH:
            case NormalIrrigationTaskState.STATE_STOPPING_VALVES:
                status = 4;
                break;
            case NormalIrrigationTaskState.STATE_FINISH:
                status = 5;
                break;
            default:
                //不会到这
                break;
        }
        result.setStatus(status);
        return result;
    }

    public IasObject getDeviceById(long id) {
        IasObject iasObject;

        iasObject = getDevById(id);
        if (iasObject != null) {
            return iasObject;
        }

        for (IasOpDevice valve : valves) {
            iasObject = valve.getDeviceById(id);
            if (iasObject != null) {
                return iasObject;
            }
        }

        return null;
    }

    public IasObject findIasObjectByAddr(IasObjectAddr addr) {
        IasObject iasObject;

        if (getAddr().equals(addr)){
            return this;
        }

        iasObject = findByAddr(addr);
        if (iasObject != null) {
            return iasObject;
        }

        for (IasOpDevice valve : valves) {
            iasObject = valve.findIasObjectByAddr(addr);
            if (iasObject != null) {
                return iasObject;
            }
        }

        return null;
    }

    //TODO 接口梳理

    /** 获取灌溉区详细信息
     * @param type 查询类型
     * @return 灌溉区详细信息
     */
    IrriAreaDTO getIrriAreaDetail(Integer type){
        IrriAreaDTO irriAreaDTO = new IrriAreaDTO(irrigationAreaRecord);
        if (type == IaSystemConstant.QUERY_TYPE_ALL) {
            irriAreaDTO.setDcPoints(getDcPointVos());
            irriAreaDTO.setDevices(getNormalDeviceVos());
        }
        if (type != IaSystemConstant.QUERY_TYPE_IRRI_TASK) {
            irriAreaDTO.setValves(getValveVos());
        }
        return irriAreaDTO;
    }
}

