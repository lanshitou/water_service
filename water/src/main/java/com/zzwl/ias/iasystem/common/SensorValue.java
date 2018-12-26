package com.zzwl.ias.iasystem.common;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by HuXin on 2017/8/29.
 */
@JsonInclude
public class SensorValue implements Cloneable {

    final static private int DATA_TYPE_MIN = 1;

    /**
     * 土壤温度
     */
    final static public int SOIL_TEMP = 1;

    /**
     * 土壤湿度
     */
    final static public int SOIL_HUMI = 2;

    /**
     * 空气温度
     */
    final static public int AIR_TEMP = 3;

    /**
     * 空气湿度
     */
    final static public int AIR_HUMI = 4;

    /**
     * CO2浓度
     */
    final static public int CO2_CONC = 5;

    /**
     * 光照强度
     */
    final static public int ILLU_INTN = 6;

    /**
     * 蓄电池电压
     */
    final static public int BAT_VOL = 7;

    /**
     * 蓄电池在连接太阳能板时的电压
     */
    final static public int BAT_WITH_SOLAR_VOL = 8;

    /**
     * 太阳能板电压
     */
    final static public int SOLAR_VOL = 9;

    /**
     * DTU信号强度
     */
    final static public int SIGNAL_STR = 10;

    /**
     * 单板温度
     */
    final static public int BOARD_TEMP = 11;

    /**
     * 土壤电导率
     */
    final static public int SVT_SOIL_CONDUCTIVITY = 12;

    /**
     * 土壤盐分
     */
    final static public int SVT_SOIL_SALINITY = 13;

    /**
     * 土壤PH值
     */
    final static public int SVT_SOIL_PH = 14;

    final static private int DATA_TYPE_MAX = 14;

    /**
     * 温度最小值
     */
    final static public int TEMP_MIN = -1000;

    /**
     * 温度最大值
     */
    final static public int TEMP_MAX = 1000;

    /**
     * 湿度最小值
     */
    final static public int HUMI_MIN = 0;

    /**
     * 湿度最大值
     */
    final static public int HUMI_MAX = 1000;

    /**
     * CO2浓度最小值
     */
    final static public int CO2_MIN = 0;

    /**
     * CO2浓度最大值
     */
    final static public int CO2_MAX = 50000;

    /**
     * 光照强度最小值
     */
    final static public int ILLU_MIN = 0;

    /**
     * 光照强度最大值
     */
    final static public int ILLU_MAX = 500000;

    /**
     * 电压最小值
     */
    final static public int VOL_MIN = 0;

    /**
     * 电压最大值
     */
    final static public int VOL_MAX = 600;

    /**
     * DTU信号强度最小值
     */
    final static public int SIGNAL_STR_MIN = 0;

    /**
     * DTU信号强度最大值
     */
    final static public int SIGNAL_STR_MAX = 32;

    /**
     * 土壤电导率最小值
     */
    final static public int CONDUCTIVITY_MIN = 0;

    /**
     * 土壤电导率最大值
     */
    final static public int CONDUCTIVITY_MAX = 5000;

    /**
     * 土壤盐分最小值
     */
    final static public int SALINITY_MIN = 0;

    /**
     * 土壤盐分最大值
     */
    final static public int SALINITY_MAX = 3000;

    /**
     * 土壤PH最小值
     */
    final static public int PH_MIN = 0;

    /**
     * 土壤PH最大值
     */
    final static public int PH_MAX = 140;

    /**
     * 采集值非法
     */
    final static public int VALUE_INVALID = Integer.MAX_VALUE;

    /**
     * 采集值的类型
     */
    private int type;

    /**
     * 采集值
     */
    private int value;

    public static SensorValue create(int type) {
        if (type < DATA_TYPE_MIN || type > DATA_TYPE_MAX) {
            return null;
        }

        return new SensorValue(type);
    }

    private SensorValue(int type) {
        this.type = type;
        this.value = VALUE_INVALID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return getTypeName(type);
    }

    public void setValue(int value) {
        switch (type) {
            case SOIL_TEMP:
            case AIR_TEMP: {
                if (value < TEMP_MIN || value > TEMP_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case SOIL_HUMI:
            case AIR_HUMI: {
                if (value < HUMI_MIN || value > HUMI_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case CO2_CONC: {
                if (value < CO2_MIN || value > CO2_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case ILLU_INTN: {
                if (value < ILLU_MIN || value > ILLU_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case BAT_VOL:
            case BAT_WITH_SOLAR_VOL:
            case SOLAR_VOL: {
                if (value < VOL_MIN || value > VOL_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case SIGNAL_STR: {
                if (value < SIGNAL_STR_MIN || value > SIGNAL_STR_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case BOARD_TEMP: {
                if (value < TEMP_MIN || value > TEMP_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case SVT_SOIL_CONDUCTIVITY: {
                if (value < CONDUCTIVITY_MIN || value > CONDUCTIVITY_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case SVT_SOIL_SALINITY: {
                if (value < SALINITY_MIN || value > SALINITY_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            case SVT_SOIL_PH: {
                if (value < PH_MIN || value > PH_MAX) {
                    this.value = VALUE_INVALID;
                } else {
                    this.value = value;
                }
                break;
            }
            default: {
                this.value = VALUE_INVALID;
            }
        }
    }


    @Override
    public SensorValue clone() {
        SensorValue value = null;

        try {
            value = (SensorValue) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return value;
    }

    public static String getTypeName(int value) {
        switch (value) {
            case 1:
                return "土壤温度";
            case 2:
                return "土壤湿度";
            case 3:
                return "空气温度";
            case 4:
                return "空气湿度";
            case 5:
                return "CO2浓度";
            case 6:
                return "光照强度";
            case 7:
                return "蓄电池电压";
            case 8:
                return "蓄电池在连接太阳能板时的电压";
            case 9:
                return "太阳能板电压";
            case 10:
                return "DTU信号强度";
            case 11:
                return "单板温度";
            case 12:
                return "土壤电导率";
            case 13:
                return "土壤盐分";
            case 14:
                return "土壤PH值";
            default:
                return "传感器数值";
        }
    }
}
