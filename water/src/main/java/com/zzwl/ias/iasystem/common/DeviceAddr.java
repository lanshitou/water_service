package com.zzwl.ias.iasystem.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zzwl.ias.iasystem.device.Controller;

/**
 * Created by HuXin on 2017/8/26.
 */
@JsonInclude
public class DeviceAddr implements Cloneable {
    public final static int MIN_DEV_SEQ_NUM = 0;        //设备编号最小值
    public final static int MAX_OP_DEV_SEQ_NUM = 59;    //可操作设备编号最大值
    public final static int MAX_SENSOR_SEQ_NUM = 9;     //传感器设备编号最大值

    private long id;

    /**
     * 系统ID
     */
    private int sysId;

    /**
     * 控制器ID
     */
    private byte ctlerId;

    /**
     * 设备类型
     */
    private byte devType;

    /**
     * 设备编号
     */
    private byte devSeq;

    public DeviceAddr() {
        sysId = 0;
        ctlerId = 0;
        devType = 0;
        devSeq = 0;
        id = 0;
    }


    public DeviceAddr(int sysId, byte ctlerId, byte devType, byte devSeq) {
        this.sysId = sysId;
        this.ctlerId = ctlerId;
        this.devType = devType;
        this.devSeq = devSeq;
        addrToId();
    }

    public DeviceAddr(long id) {
        sysId = (int) (id >> 32);
        ctlerId = (byte) (id >> 16);
        devType = (byte) (id >> 8);
        devSeq = (byte) (id);
        this.id = id;
    }

    private void addrToId() {
        id = sysId;
        id = id << 32;
        long temp = ((ctlerId << 16) & 0xFF0000) | ((devType << 8) & 0xFF00) | (devSeq & 0xFF);
        id = id | temp;
    }

    public int getSysId() {
        return sysId;
    }

    public byte getCtlerId() {
        return ctlerId;
    }

    public byte getDevType() {
        return devType;
    }

    public byte getDevSeq() {
        return devSeq;
    }

    public void setId(long id) {
        sysId = (int) (id >> 32);
        ctlerId = (byte) (id >> 16);
        devType = (byte) (id >> 8);
        devSeq = (byte) (id);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean isValid() {
        return isValid(id);
    }

    public static int getSysIdById(long id) {
        return (int) (id >> 32);
    }

    public static byte getControllerIdById(long id) {
        return (byte) (id >> 16);
    }

    public static byte getDevTypeById(long id) {
        return (byte) (id >> 8);
    }

    public static byte getDevSeqById(long id) {
        return (byte) (id);
    }

    public static boolean isMasterController(long id) {
        byte devType = getDevTypeById(id);
        byte ctlerId = getControllerIdById(id);
        if (devType == DeviceType.DEV_CONTROLLER && ctlerId == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSlaveController(long id) {
        byte devType = getDevTypeById(id);
        byte ctlerId = getControllerIdById(id);
        if (devType == 0 && ctlerId != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSensor(long id) {
        byte devType = getDevTypeById(id);
        if (devType >= DeviceType.SEN_TYPE_MIN_ID && devType <= DeviceType.SEN_TYPE_MAX_ID) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValve(long id) {
        byte devType = getDevTypeById(id);
        if (devType == DeviceType.DEV_PULSE_VALVE || devType == DeviceType.DEV_MAGNETIC_VALVE || devType == DeviceType.DEV_ELEC_VALVE) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPump(long id) {
        return getDevTypeById(id) == DeviceType.DEV_PUMP;
    }

    public static boolean isElecValve(long id){
        return getDevTypeById(id) == DeviceType.DEV_ELEC_VALVE;
    }

    public static boolean isShutter(long id){
        return getDevTypeById(id) == DeviceType.DEV_SHUTTER;
    }

    public static boolean isValid(long id) {
        byte ctlerId = getControllerIdById(id);
        byte devType = getDevTypeById(id);
        byte devSeq = getDevSeqById(id);

        //控制器地址
        if (devType == DeviceType.DEV_CONTROLLER) {
            if (ctlerId < Controller.MIN_CTLER_ID || ctlerId > Controller.MAX_CTLER_ID) {
                return false;
            }

            if (devSeq != 0) {
                return false;
            }
            return true;
        }

        if (devType >= DeviceType.OP_DEV_TYPE_MIN_ID && devType <= DeviceType.OP_DEV_TYPE_MAX_ID) {
            if (devSeq < MIN_DEV_SEQ_NUM || devSeq > MAX_OP_DEV_SEQ_NUM) {
                return false;
            }

            return true;
        }

        if (devType >= DeviceType.SEN_TYPE_MIN_ID && devType <= DeviceType.SEN_TYPE_MAX_ID) {
            //目前，每种传感器设备每个控制器上最多支持10个
            if (devSeq < MIN_DEV_SEQ_NUM || devSeq > MAX_SENSOR_SEQ_NUM) {
                return false;
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        DeviceAddr other = (DeviceAddr) obj;
        if (this.sysId == other.sysId && this.ctlerId == other.ctlerId
                && this.devType == other.devType && this.devSeq == other.devSeq) {
            return true;
        }

        return false;
    }

    @Override
    public DeviceAddr clone() {
        DeviceAddr addr = null;

        try {
            addr = (DeviceAddr) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return addr;
    }
}
