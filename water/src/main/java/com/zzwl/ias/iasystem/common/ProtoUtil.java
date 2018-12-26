package com.zzwl.ias.iasystem.common;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Util;
import com.zzwl.ias.iasystem.OperateArgs;
import com.zzwl.ias.iasystem.ReqOperateDevice;
import com.zzwl.ias.iasystem.communication.IaCommandType;

import static com.zzwl.ias.iasystem.common.DeviceType.*;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-23
 * Time: 20:51
 */
public class ProtoUtil {
    private final static int MAX_OP_DURATION = 86400;   //设备运行最长时间24小时=86400秒

    /**
     * 设备状态更新报文中每种设备的状态信息长度是不一样的，该函数用于获取指定设备的状态信息长度
     *
     * @param devType 设备类型
     * @return 设备状态信息的长度
     */
    public static int getDevInfoLen(byte devType) {
        if (devType >= OP_DEV_TYPE_MIN_ID && devType <= OP_DEV_TYPE_MAX_ID) {
            //可操作设备状态信息长度固定为1
            return 1;
        }

        //传感器设备状态信息长度不相同
        switch (devType) {
            case SEN_SOIL_HUMI:
            case SEN_SOIL_TEMP:
            case SEN_AIR_HUMI:
            case SEN_AIR_TEMP:
            case SEN_LIGHT_ILLU:
            case SEN_CO2:
            case SENSOR_SOIL_PH: {
                return 2;
            }
            case SEN_SOIL_H_T:
            case SEN_AIR_H_T:
            case SEN_AIR_H_T_HANG: {
                return 4;
            }
            case SEN_H_T_I: {
                return 6;
            }
            case SENSOR_SOIL_HTCS: {
                return 8;
            }
            default: {
                return 0;
            }
        }
    }

    /**
     * 将设备状态更新报文中的设备状态信息转换成本地信息
     *
     * @param devType 设备类型
     * @param src     存放设备状态信息的字节数组
     * @param offset  设备状态信息的存放位置
     * @return 转换后的信息
     */
    public static int[] devInfoN2H(byte devType, byte[] src, int offset) {
        int[] result;
        if (devType >= OP_DEV_TYPE_MIN_ID && devType <= OP_DEV_TYPE_MAX_ID) {
            result = new int[1];
            result[0] = src[offset];
            return result;
        }

        switch (devType) {
            case SEN_SOIL_HUMI:
            case SEN_SOIL_TEMP:
            case SEN_AIR_HUMI:
            case SEN_AIR_TEMP:
            case SEN_CO2:
            case SENSOR_SOIL_PH: {
                result = new int[1];
                result[0] = Util.byteToShort(src, offset);
                break;
            }
            case SEN_LIGHT_ILLU: {
                result = new int[1];
                result[0] = getIlluRealValue(Util.byteToShort(src, offset));
                break;
            }
            case SEN_SOIL_H_T:
            case SEN_AIR_H_T:
            case SEN_AIR_H_T_HANG: {
                result = new int[2];
                result[0] = Util.byteToShort(src, offset);
                result[1] = Util.byteToShort(src, offset + 2);
                break;
            }
            case SEN_H_T_I: {
                result = new int[3];
                result[0] = Util.byteToShort(src, offset);
                result[1] = Util.byteToShort(src, offset + 2);
                result[2] = getIlluRealValue(Util.byteToShort(src, offset + 4));
                break;
            }
            case SENSOR_SOIL_HTCS: {
                result = new int[4];
                result[0] = Util.byteToShort(src, offset);
                result[1] = Util.byteToShort(src, offset + 2);
                result[2] = Util.byteToShort(src, offset + 4);
                result[3] = Util.byteToShort(src, offset + 6);
                break;
            }
            default: {
                result = null;
            }
        }
        return result;
    }

    /**
     * 获取设备操作命令数据部分的长度
     *
     * @param devType 操作类型，目前仅有启动设备和停止设备两个命令
     * @param opType  设备类型
     * @return 设备操作命令数据部分的长度
     */
    public static int getOpCmdDataLen(int devType, int opType) {
        switch (devType) {
            case DEV_ELEC_VALVE:
            case DEV_SHUTTER: {
                if (opType == IaCommandType.START_DEVICE) {
                    return 6;
                } else {
                    return 3;
                }
            }
            case DEV_MAGNETIC_VALVE:
            case DEV_PUMP:
            case DEV_FAN:
            case DEV_GROW_LIGHT:
            case DEV_HEALER:
            case DEV_DEHUMIDIFIER:
            case DEV_PULSE_VALVE: {
                if (opType == IaCommandType.START_DEVICE) {
                    return 5;
                } else {
                    return 3;
                }
            }
            default: {
                //do nothing
                return 0;
            }
        }
    }

    public static ErrorCode getOpCmdData(ReqOperateDevice request, byte[] cmdData) {
        long devId = request.getDevId();
        int opType = request.getOpType();
        OperateArgs args = request.getArgs();
        //设备地址
        cmdData[0] = DeviceAddr.getControllerIdById(devId);
        cmdData[1] = DeviceAddr.getDevTypeById(devId);
        cmdData[2] = DeviceAddr.getDevSeqById(devId);

        if (opType == IaCommandType.START_DEVICE) {
            if (args == null || args.getDuration() == null) {
                return ErrorCode.OPERATION_INVALID;
            }

            //args[0]存放断网自动停止选项，只有0和1两个值
            //args[1]存放运行最大时长，0表示持续运行，非0值表示运行时长，单位为秒，最大值为MAX_OP_DURATION
            if ((args.getDuration() < 0 || args.getDuration() > MAX_OP_DURATION)) {
                return ErrorCode.OPERATION_INVALID;
            }

            //断网自动停止选项和运行时长存放在网络序的2字节整数中，最高位存放是否自动停止，低15位存放运行时长
            if (!args.getAutoStop()) {
                cmdData[3] = (byte) (args.getDuration() >> 8);
            } else {
                cmdData[3] = (byte) ((args.getDuration() >> 8) | 0x80);
            }
            cmdData[4] = (byte) (args.getDuration() & 0xFF);

            switch (DeviceAddr.getDevTypeById(devId)) {
                //电动阀和卷帘还需要1个开启程度的参数
                case DEV_ELEC_VALVE:
                case DEV_SHUTTER: {
                    //设备参数数量必须是3个
                    if (args.getPosition() == null) {
                        return ErrorCode.OPERATION_INVALID;
                    }

                    //开启程度目前仅支持全关和全开两种
                    if (args.getPosition() != 0 && args.getPosition() != 100) {
                        return ErrorCode.OPERATION_INVALID;
                    }
                    cmdData[5] = (byte) ((int) args.getPosition());
                    return ErrorCode.OK;
                }
                case DEV_MAGNETIC_VALVE:
                case DEV_PUMP:
                case DEV_FAN:
                case DEV_GROW_LIGHT:
                case DEV_HEALER:
                case DEV_DEHUMIDIFIER:
                case DEV_PULSE_VALVE: {
                    //忽略其余参数
                    return ErrorCode.OK;
                }
                default: {
                    return ErrorCode.DEV_TYPE_INVALID;
                }
            }
        } else if (opType == IaCommandType.STOP_DEVICE) {
            //忽略所有参数
            return ErrorCode.OK;
        }

        return ErrorCode.OPERATION_INVALID;
    }

    /**
     * 设备上报的光照强度值是经过压缩的，需要解析出真实值
     *
     * @param compressValue 光照强度压缩值
     * @return 光照强度实际值
     */
    static public int getIlluRealValue(short compressValue) {
        //压缩算法：真实值小于10000直接存放，大于等于10000时，超出部分除10加10000。超出最大允许值为非法，存放0xFFFF
        if (compressValue < 10000) {
            return compressValue;
        } else if (compressValue == 0xFFFF) {
            return SensorValue.VALUE_INVALID;
        } else {
            return compressValue + (compressValue - 10000) * 10;
        }
    }


    public static int getCtlerInfoLen(byte infoType) {
        switch (infoType) {
            case SensorValue.BAT_VOL:
            case SensorValue.BAT_WITH_SOLAR_VOL:
            case SensorValue.SOLAR_VOL:
            case SensorValue.BOARD_TEMP: {
                return 2;
            }
            case SensorValue.SIGNAL_STR: {
                return 1;
            }
            default: {
                return 0;
            }
        }
    }

    public static SensorValue ctlerInfoN2H(byte type, byte[] src, int offset) {
        SensorValue result;

        switch (type) {
            case SensorValue.BAT_VOL:
            case SensorValue.BAT_WITH_SOLAR_VOL:
            case SensorValue.SOLAR_VOL:
            case SensorValue.BOARD_TEMP: {
                result = SensorValue.create(type);
                if (result == null) {
                    return null;
                }
                result.setValue(Util.byteToShort(src, offset));
                break;
            }
            case SensorValue.SIGNAL_STR: {
                result = SensorValue.create(type);
                if (result == null) {
                    return null;
                }
                result.setValue(src[offset]);
                break;
            }
            default: {
                result = null;
            }
        }
        return result;
    }
}
