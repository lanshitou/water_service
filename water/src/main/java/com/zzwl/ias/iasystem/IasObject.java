package com.zzwl.ias.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.iasystem.common.DeviceState;
import com.zzwl.ias.iasystem.constant.WarningConstant;
import com.zzwl.ias.iasystem.warning.DeviceOfflineWarning;
import com.zzwl.ias.timer.IaTimer;

/**
 * Description:智慧农业系统对象。智慧农业系统、农田、灌溉区、可操作设备、数据采集传感器都继承自该类。
 * User: HuXin
 * Date: 2018-04-14
 * Time: 12:37
 */
public abstract class IasObject implements Comparable<IasObject> {
    private IasObjectAddr addr;
    private IaTimer offlineTimer = null;

    abstract public int getId();
    abstract String getName();
    abstract public int getSortOrder();

    public IasObject(){
        offlineCheck(DeviceState.DEV_STATE_OFFLINE, DeviceState.DEV_STATE_OFFLINE);
    }

    public IasObjectAddr getAddr() {
        return addr;
    }

    public void setAddr(IasObjectAddr addr) {
        this.addr = addr;
    }

    public void offlineCheck(int oldState, int newState){
        if ( !(this instanceof IasSensor || this instanceof IasOpDevice)){
            return;
        }

        if (newState == DeviceState.DEV_STATE_OFFLINE){
            //离线告警产生
            if (offlineTimer == null){
                offlineTimer = AppContext.timerManager.createTimer(()->{
                    //生成离线告警
                    AppContext.getBean(DeviceOfflineWarning.class).createOfflineWarning(this);
                    offlineTimer = null;
                }, WarningConstant.DEVICE_OFFLINE_WARNING_TIME, false);
            }
        }else{
            if (oldState == DeviceState.DEV_STATE_OFFLINE){
                //离线告警消除
                if (offlineTimer != null){
                    AppContext.timerManager.deleteTimer(offlineTimer);
                    offlineTimer = null;
                }
                AppContext.getBean(DeviceOfflineWarning.class).clearOfflineWarning(this);
            }
        }
    }

    @Override
    public int compareTo(IasObject o) {
        int result = this.getSortOrder() - o.getSortOrder();
        if (result != 0) {
            return result;
        } else {
            return this.getId() - o.getId();
        }
    }
}
