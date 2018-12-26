package com.zzwl.ias.dto.device;

import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-25
 * Time: 9:29
 */
public class SensorDataDTO {
    private Date time;
    private Integer value;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
