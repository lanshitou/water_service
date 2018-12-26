package com.zzwl.ias.dto.warning;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-26
 * Time: 17:37
 */
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class WarningStatisticsDTO {
    private Integer id;
    private Integer thresholdWarningCount;
    private Integer offlineWarningCount;
    private Integer irriWarningCount;
    private Integer otherWarningCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getThresholdWarningCount() {
        return thresholdWarningCount;
    }

    public void setThresholdWarningCount(Integer thresholdWarningCount) {
        this.thresholdWarningCount = thresholdWarningCount;
    }

    public Integer getOfflineWarningCount() {
        return offlineWarningCount;
    }

    public void setOfflineWarningCount(Integer offlineWarningCount) {
        this.offlineWarningCount = offlineWarningCount;
    }

    public Integer getIrriWarningCount() {
        return irriWarningCount;
    }

    public void setIrriWarningCount(Integer irriWarningCount) {
        this.irriWarningCount = irriWarningCount;
    }

    public Integer getOtherWarningCount() {
        return otherWarningCount;
    }

    public void setOtherWarningCount(Integer otherWarningCount) {
        this.otherWarningCount = otherWarningCount;
    }
}
