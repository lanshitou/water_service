package com.zzwl.ias.iasystem;

import com.zzwl.ias.domain.NormalDeviceRecord;

/**
 * Created by HuXin on 2017/12/25.
 */
public class NormalDevice implements Comparable<NormalDevice> {
    NormalDeviceRecord normalDeviceRecord;

    public NormalDevice(NormalDeviceRecord normalDeviceRecord) {
        this.normalDeviceRecord = normalDeviceRecord;
    }

    public long getDevId() {
        return normalDeviceRecord.getDevId();
    }

//    public DeviceDTO getDeviceVo()
//    {
//        Device device = AppContext.deviceManager.getDevice(normalDeviceRecord.getDevId());
//        if (device != null)
//        {
//            DeviceDTO normalDeviceVo = new DeviceDTO(normalDeviceRecord);
//            normalDeviceVo.setDeviceVo(device.getDeviceVo());
//            return normalDeviceVo;
//        }
//        return null;
//    }

    public int getFarmlandId() {
        return normalDeviceRecord.getFmId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NormalDevice) {
            if (normalDeviceRecord.getDevId() == ((NormalDevice) obj).normalDeviceRecord.getDevId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(NormalDevice o) {
        long id1 = normalDeviceRecord.getDevId();
        long id2 = o.normalDeviceRecord.getDevId();
        if (id1 < id2) {
            return -1;
        } else if (id1 > id2) {
            return 1;
        }
        return 0;
    }
}
