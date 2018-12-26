package com.zzwl.ias.dto.warning;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-15
 * Time: 11:08
 */
public class ThresholdWarningExtDTO {
    private Integer sensorId;
    private Integer dataType;
    private Integer threshold;

    public Integer getSensorId() {
        return sensorId;
    }

    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }
}
