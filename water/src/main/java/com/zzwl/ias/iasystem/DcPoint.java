package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.domain.DataCollectionRecord;
import com.zzwl.ias.domain.DcPointRecord;
import com.zzwl.ias.iasystem.common.SensorValue;
import com.zzwl.ias.mapper.AlarmConfigRecordMapper;
import com.zzwl.ias.mapper.AlarmThresholdRecordMapper;
import com.zzwl.ias.mapper.DataCollectionRecordMapper;
import com.zzwl.ias.mapper.MessageAlarmRecordMapper;
import com.zzwl.ias.dto.iasystem.DcPointDTO;
import com.zzwl.ias.vo.DeviceVo;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by HuXin on 2018/1/9.
 */
public class DcPoint implements Comparable<DcPoint> {
    private DcPointRecord dcPointRecord;
    private DataCollectionRecordMapper dataCollectionRecordMapper;
    private AlarmConfigRecordMapper alarmConfigRecordMapper;
    private MessageAlarmRecordMapper messageAlarmRecordMapper;
    private AlarmThresholdRecordMapper alarmThresholdRecordMapper;

    public DcPoint(DcPointRecord dcPointRecord) {
        this.dcPointRecord = dcPointRecord;
        dataCollectionRecordMapper = AppContext.getBean(DataCollectionRecordMapper.class);
    }

    public long getDcPointId() {
        return dcPointRecord.getId();
    }

    public long getSensorId() {
        return dcPointRecord.getSensorId();
    }

    public int getOwnerId() {
        return dcPointRecord.getOwnerId();
    }

    public DcPointDTO getDcPointVo() {
//        DcPointDTO dcPointVo = new DcPointDTO(dcPointRecord);
//        Device device = AppContext.deviceManager.getDevice(dcPointRecord.getSensorId());
//        if (device != null)
//        {
//            DeviceVo deviceVo = device.getDeviceVo();
//            dcPointVo.setDeviceVo(deviceVo);
//        }
//        return dcPointVo;
        return null;
    }


    public void collectSensorData() {
        collectSensorData(Calendar.getInstance().getTime());
    }

    public void collectSensorData(Date date) {
        DataCollectionRecord record = new DataCollectionRecord();
        DeviceVo deviceVo = AppContext.deviceService.getDeviceById(getSensorId());
        if (deviceVo != null) {
            for (SensorValue sensorValue : deviceVo.getSensorValues()) {
                record.setSensorId(getDcPointId());
                record.setTime(date);
                record.setType(sensorValue.getType());
                record.setValue(sensorValue.getValue());
                record.setCollectionType(2);
                dataCollectionRecordMapper.insert(record);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DcPoint) {
            if (dcPointRecord.getSensorId().longValue() == ((DcPoint) obj).dcPointRecord.getSensorId().longValue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(DcPoint o) {
        long id1 = dcPointRecord.getSensorId();
        long id2 = o.dcPointRecord.getSensorId();
        if (id1 < id2) {
            return -1;
        } else if (id1 > id2) {
            return 1;
        }
        return 0;
    }
}
