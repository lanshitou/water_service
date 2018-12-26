package com.zzwl.ias.service.impl;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.User;
import com.zzwl.ias.dto.device.DeviceOperateDTO;
import com.zzwl.ias.dto.device.ReqSensorHistoryDataDTO;
import com.zzwl.ias.dto.device.SensorHistoryDataDTO;
import com.zzwl.ias.dto.iasystem.DeviceDTO;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.mapper.DeviceOperateDOExtMapper;
import com.zzwl.ias.mapper.UserMapper;
import com.zzwl.ias.service.IaSystemUserMngService;
import com.zzwl.ias.vo.UserBasicInfoVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-29
 * Time: 17:23
 */
@Service
public class IaSystemUserMngServiceImpl implements IaSystemUserMngService {

    /**用户操作设备接口
     * @param request 操作设备相关的参数
     * @return 设备操作完成后的状态
     */
    @Override
    public DeviceDTO operateDevice(ReqOperateDevice request) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(request.getIasId());
        AssertEx.isTrue(iaSystem != null, ErrorCode.IASYSTEM_NOT_EXIST);

        //TODO 用户权限管理

        //操作设备
        Semaphore sem = new Semaphore(0);
        request.setFinishHandler((operateDeviceRequest) -> sem.release());
        iaSystem.operateDevice(request);
        //等待设备操作完成
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            //TODO 需要研究如何处理该异常
            AssertEx.isOK(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        AssertEx.isTrue(request.getResult() == ErrorCode.OK, request.getResult());

        DeviceDTO deviceDTO = iaSystem.getOpDeviceInfo(request.getIasDevId());
        AssertEx.isTrue(deviceDTO != null, ErrorCode.DEVICE_NOT_EXIST);
        return deviceDTO;
    }

    /**
     * 用户获取传感器历史采集值
     *
     * @param request 获取历史采集值的请求
     * @return 获取结果
     */
    @Override
    public SensorHistoryDataDTO getHistorySensorDate(ReqSensorHistoryDataDTO request) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(request.getIasId());
        if (iaSystem == null){
            return null;
        }

        //TODO 用户权限管理

        //获取采集结果
        return iaSystem.getSensorHistoryData(request);
    }

    /**
     * 获取设备操作记录
     *
     * @param userId 用户ID
     * @param iasId    智慧农业系统ID
     * @param deviceId 设备ID
     * @param type     查询类型
     * @param offset   起始位置
     * @param limit    偏移量
     * @return 设备操作记录
     */
    @Override
    public List<DeviceOperateDTO> listHistoryOperation(Integer userId, Integer iasId, Integer deviceId, Integer type, Integer offset, Integer limit) {
        //TODO 权限管理
        List<DeviceOperateDTO> operateDOS = AppContext.getBean(DeviceOperateDOExtMapper.class).listHistoryOperation(deviceId, type, offset, limit);
        AssertEx.isNotEmpty(operateDOS);
        UserMapper userMapper = AppContext.getBean(UserMapper.class);
        for (DeviceOperateDTO deviceOperateDTO : operateDOS){
            User user = userMapper.selectByUserId(deviceOperateDTO.getUserId());
            if (user != null) {
                UserBasicInfoVo basicInfoVo = new UserBasicInfoVo(user);
                deviceOperateDTO.setUser(basicInfoVo);
            }
            deviceOperateDTO.setUserId(null);
        }
        return operateDOS;
    }
}
