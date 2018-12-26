package com.zzwl.ias.dto.device;

import java.util.Date;
import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-21
 * Time: 14:25
 */
public class SensorHistoryDataDTO {
    private LinkedList<SensorDataDTO> history;

    public LinkedList<SensorDataDTO> getHistory() {
        return history;
    }
    public void setHistory(LinkedList<SensorDataDTO> history) {
        this.history = history;
    }
}
