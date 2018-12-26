package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.AlarmConfigRecord;
import com.zzwl.ias.domain.IasDeviceRecord;
import com.zzwl.ias.domain.WarningDO;
import com.zzwl.ias.dto.iasystem.DcPointDTO;
import com.zzwl.ias.dto.device.ReqSensorHistoryDataDTO;
import com.zzwl.ias.dto.device.SensorHistoryDataDTO;
import com.zzwl.ias.dto.warning.ReqConfigSensorThresholdWarningDTO;
import com.zzwl.ias.dto.warning.ReqGetSensorThresholdWarningCfg;
import com.zzwl.ias.dto.warning.ThresholdWarningCfgDTO;
import com.zzwl.ias.dto.warning.ThresholdWarningCfgExtDTO;
import com.zzwl.ias.iasystem.common.SensorValue;
import com.zzwl.ias.iasystem.constant.WarningConstant;
import com.zzwl.ias.iasystem.event.DeviceStateChangeEvent;
import com.zzwl.ias.iasystem.warning.SensorThresholdWarning;
import com.zzwl.ias.mapper.AlarmConfigRecordExtMapper;
import com.zzwl.ias.mapper.WarningDOExtMapper;
import com.zzwl.ias.vo.ThresholdAlarmConfigVo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Description:智慧农业系统中的传感器设备
 * User: HuXin
 * Date: 2018-04-16
 * Time: 8:47
 */
public class IasSensor extends IasObject {
    private IasDeviceRecord deviceRecord;

    public IasSensor(IasDeviceRecord deviceRecord) {
        this.deviceRecord = deviceRecord;
        setAddr(IasObjectAddr.createIasDevAddr(deviceRecord));
    }

    public SensorHistoryDataDTO getSensorHistoryData(ReqSensorHistoryDataDTO reqSensorHistoryDataDTO){
        reqSensorHistoryDataDTO.setDevId(deviceRecord.getDevId());
        return AppContext.deviceService.getSensorHistoryData(reqSensorHistoryDataDTO);
    }



    public int getIasId() {
        return deviceRecord.getIasId();
    }

    public Integer getFmId() {
        return deviceRecord.getFarmlandId();
    }

    public int getIrriAreaId() {
        return deviceRecord.getIrriAreaId();
    }

    public int getIrriFerId() {
        return deviceRecord.getIrriFerId();
    }

    public long getIasDevId() {
        return deviceRecord.getIasDevId();
    }

    public long getDevId() {
        return deviceRecord.getDevId();
    }

    @Override
    public int getId() {
        return deviceRecord.getId();
    }

    @Override
    String getName() {
        return deviceRecord.getName();
    }

    @Override
    public int getSortOrder() {
        return deviceRecord.getSortOrder();
    }

    public SensorValue[] getSensorValues(){
        return AppContext.deviceService.getDeviceById(getDevId()).getSensorValues();
    }

    /** 配置传感器阈值告警
     * @param config 传感器阈值告警的配置
     * @return 配置结果
     */
    public ErrorCode configSensorThresholdWarning(ReqConfigSensorThresholdWarningDTO config) {
        //对告警进行配置
        Integer upperLimit = null;
        Integer lowerLimit = null;
        for (ThresholdWarningCfgDTO thresholdWarningCfgDTO : config.getConfig()) {
            if (thresholdWarningCfgDTO.getAlarmType() == WarningConstant.OVER_UPPER_LIMIT_WARN) {
                upperLimit = thresholdWarningCfgDTO.getThreshold();
            } else if (thresholdWarningCfgDTO.getAlarmType() == WarningConstant.UNDER_LOWER_LIMIT_WARN) {
                lowerLimit = thresholdWarningCfgDTO.getThreshold();
            }
        }
        AlarmConfigRecord alarmConfigRecord = new AlarmConfigRecord();
        alarmConfigRecord.setDevId(config.getDevId());
        alarmConfigRecord.setAlarmTypeId(config.getDataType());
        alarmConfigRecord.setAlarmwarnUplimit(upperLimit);
        alarmConfigRecord.setAlarmwarnLowlimit(lowerLimit);
        if (AppContext.getBean(AlarmConfigRecordExtMapper.class).upsert(alarmConfigRecord) == 0) {
            return ErrorCode.DATABASE_ERROR;
        }

        //配置修改后，立即检查是否有告警
        AppContext.getBean(SensorThresholdWarning.class)
                .checkThresholdWarning(this, getSensorValues(), WarningConstant.RELEASE_TYPE_USER_CHANGE_CONFIG);

        return ErrorCode.OK;
    }

    public ThresholdWarningCfgExtDTO getSensorThresholdWarningConfig(ReqGetSensorThresholdWarningCfg request) {
        ThresholdWarningCfgExtDTO cfg = new ThresholdWarningCfgExtDTO();

        //获取告警配置信息
        LinkedList<ThresholdWarningCfgDTO> thresholdWarningCfgDTOS = new LinkedList<>();
        AlarmConfigRecord alarmConfigRecord =
                AppContext.getBean(AlarmConfigRecordExtMapper.class).selectByDevIdAndType(deviceRecord.getId(), request.getDataType());
        if (alarmConfigRecord != null) {
            if (alarmConfigRecord.getAlarmwarnUplimit() != null) {
                ThresholdWarningCfgDTO upperLimit = new ThresholdWarningCfgDTO();
                upperLimit.setAlarmType(ThresholdAlarmConfigVo.OVER_UPPER_LIMIT);
                upperLimit.setThreshold(alarmConfigRecord.getAlarmwarnUplimit());
                thresholdWarningCfgDTOS.add(upperLimit);
            }
            if (alarmConfigRecord.getAlarmwarnLowlimit() != null) {
                ThresholdWarningCfgDTO lowerLimit = new ThresholdWarningCfgDTO();
                lowerLimit.setAlarmType(ThresholdAlarmConfigVo.UNDER_LOWER_LIMIT);
                lowerLimit.setThreshold(alarmConfigRecord.getAlarmwarnLowlimit());
                thresholdWarningCfgDTOS.add(lowerLimit);
            }
        }

        //获取采集点信息
        DcPointDTO dcPointDTO = getDcPointDTO(request.getDataType());

        cfg.setConfigs(thresholdWarningCfgDTOS);
        cfg.setDcPoint(dcPointDTO);
        return cfg;
    }

    public void checkThresholdWarning(DeviceStateChangeEvent event) {
        //阈值告警检查
        AppContext.getBean(SensorThresholdWarning.class).checkThresholdWarning(this, event.getNewSensorValues(), null);
    }


    /** 获取传感器数据采集信息
     * @return 传感器数据采集信息
     */
    public LinkedList<DcPointDTO> getDcPointVo() {
        LinkedList<DcPointDTO> dcPointDTOS = new LinkedList<>();
        SensorValue[] sensorValues = AppContext.deviceManager.getDevice(deviceRecord.getDevId()).getSensorValues();
        for (SensorValue sensorValue : sensorValues){
            DcPointDTO dcPointDTO = new DcPointDTO(deviceRecord);
            dcPointDTO.setStatus(AppContext.deviceManager.getDevice(getDevId()).getState());
            dcPointDTO.setType(sensorValue.getType());
            dcPointDTO.setValue(sensorValue.getValue());
            //检查是否有告警
            List<WarningDO> warningDOS = AppContext.getBean(WarningDOExtMapper.class).listCurrWarningByAddrAndType(getAddr(), null, null);
            warningDOS.removeIf(warningDO ->
                    (warningDO.getType() == WarningConstant.OVER_UPPER_LIMIT_WARN || warningDO.getType() == WarningConstant.UNDER_LOWER_LIMIT_WARN)
                    && warningDO.getSubType() != sensorValue.getType());
            if (warningDOS.size() != 0){
                dcPointDTO.setAlarmType(warningDOS.get(0).getType());
            } else{
              dcPointDTO.setAlarmType(WarningConstant.WARNING_NONE);
            }
            dcPointDTOS.add(dcPointDTO);
        }

        return dcPointDTOS;
    }

    /** 获取传感器指定的数据采集信息
     * @param dataType 数据类型
     * @return 数据采集信息
     */
    private DcPointDTO getDcPointDTO(int dataType) {
        LinkedList<DcPointDTO> dcPointDTOS = getDcPointVo();
        for (DcPointDTO dcPointDTO : dcPointDTOS){
            if (dcPointDTO.getType() == dataType){
                return dcPointDTO;
            }
        }
        return null;
    }
}
