package com.zzwl.ias.dto.irrigation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Calendar;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IrriTaskStatisticDTO {
    private Integer waitCount;
    private Integer runningCount;
    private Date finishTime;

    public Integer getWaitCount() {
        return waitCount;
    }

    public void setWaitCount(Integer waitCount) {
        this.waitCount = waitCount;
    }

    public Integer getRunningCount() {
        return runningCount;
    }

    public void setRunningCount(Integer runningCount) {
        this.runningCount = runningCount;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
