package com.zzwl.ias.iasystem.irrigation.task;

/**
 * Description:
 * User: HuXin
 * Date: 2018-06-28
 * Time: 14:45
 */
public interface NomalIrrigationTaskResult {
    int RESULT_OK = 0;
    int RESULT_USER_CACEL_OK = 2;
    int RESULT_START_PUMP_FAIL = 1;         //启动水泵失败
    int RESULT_STOP_PUMP_FAIL = 2;          //关闭水泵失败
    int RESULT_START_VALVE_FAIL = 3;        //启动阀门失败
    int RESULT_STOP_VALVE_FAIL = 4;         //关闭阀门失败
    int RESULT_USER_STOP = 5;                //用户停止任务
    int RESULT_PUMP_STOPPED = 6;            //任务执行过程中水泵异常关闭
    int RESULT_VALVE_STOPPED = 7;           //任务执行过程中阀门异常关闭
    int RESULT_WORK_MODE_CHANGED = 8;       //工作模式由自动切换到了手动
}
