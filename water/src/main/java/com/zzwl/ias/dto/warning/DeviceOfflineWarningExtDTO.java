package com.zzwl.ias.dto.warning;

import java.util.Date;

/**
 * Description:
 * User: HuXin
 * Date: 2018-10-12
 * Time: 11:01
 */
public class DeviceOfflineWarningExtDTO {
    private Date offlineTime;
    private Integer devType;

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public Integer getDevType() {
        return devType;
    }

    public void setDevType(Integer devType) {
        this.devType = devType;
    }
}
