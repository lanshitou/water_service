package com.zzwl.ias.iasystem.irrigation;

import com.zzwl.ias.iasystem.irrigation.task.NormalIrrigationTaskState;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import com.zzwl.ias.dto.irrigation.IrriTaskStatisticDTO;

import java.util.Calendar;
import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-27
 * Time: 9:34
 */
public class IrrigationUtil {
    /** 根据灌溉任务信息生成统计数据
     * @param irrigationTaskStateVos 灌溉任务列表
     * @return 灌溉任务统计数据
     */
    public static IrriTaskStatisticDTO getIrrigateTaskBasicInfoVo(LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos) {
        IrriTaskStatisticDTO irrigateTaskBasicInfoVo = new IrriTaskStatisticDTO();
        irrigateTaskBasicInfoVo.setRunningCount(0);
        irrigateTaskBasicInfoVo.setWaitCount(0);
        if (irrigationTaskStateVos.size() == 0) {
            return irrigateTaskBasicInfoVo;
        }
        int waitAmount = 0;
        int wateringAmount = 0;
        for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos) {
            int state = irrigationTaskStateVo.getStatus();
            if (state == NormalIrrigationTaskState.STATE_STARTING_VALVES
                    || state == NormalIrrigationTaskState.STATE_WAITING_PUMP_STARTED
                    || state == NormalIrrigationTaskState.STATE_IRRIGATING) {
                wateringAmount++;
            }
            if (state == NormalIrrigationTaskState.STATE_WAITING) {
                waitAmount++;
            }
        }
        irrigateTaskBasicInfoVo.setWaitCount(waitAmount);
        irrigateTaskBasicInfoVo.setRunningCount(wateringAmount);
        irrigateTaskBasicInfoVo.setFinishTime(irrigationTaskStateVos.getLast().getFinishTime());
        return irrigateTaskBasicInfoVo;
    }


    /** 估算任务起始和结束时间
     * @param irrigationTaskStateVos 任务列表
     * @param m 同时可执行的任务数量
     */
    public static void estimateTaskTime(LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos, int m) {
        /* 算法说明：1）开始计算前，排除已经完成的任务；2）对于前m（m=最大可执行任务的数量）个任务，未启动的任务的开始时间认为是当前时间，
           结束时间认为是启动时间加上持续时间。3)对于后面的任务，依次如下计算：启动时间 = 前面所有任务中最早结束任务的结束时间，
           结束时间 = 启动时间加上持续时间，已经计算过结束时间的任务，不在参与计算。
          */
        for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos) {
            int state = irrigationTaskStateVo.getStatus();
            if (state == NormalIrrigationTaskState.STATE_WAITING_FINISH
                    || state == NormalIrrigationTaskState.STATE_STOPPING_VALVES || state == NormalIrrigationTaskState.STATE_FINISH){
                //运行结束或者正在结束的任务不需要参与计算
                irrigationTaskStateVo.setFlag(false);
                if (state != NormalIrrigationTaskState.STATE_FINISH){
                    //正在结束的任务可以认为结束时间为当前时间
                    irrigationTaskStateVo.setFinishTime(Calendar.getInstance().getTime());
                }
            }else{
                irrigationTaskStateVo.setFlag(true);
                if (state != NormalIrrigationTaskState.STATE_WAITING){
                    //对已经开始运行的任务，可以认为其结束时间为开始时间加上持续时间
                    Calendar finishTime = Calendar.getInstance();
                    finishTime.setTime(irrigationTaskStateVo.getStartTime());
                    finishTime.add(Calendar.SECOND, irrigationTaskStateVo.getExpDuration());
                    irrigationTaskStateVo.setFinishTime(finishTime.getTime());
                }
            }
        }

        int index = 0;
        for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos) {
            if (!irrigationTaskStateVo.isFlag()) {
                continue;
            }

            int state = irrigationTaskStateVo.getStatus();
            if (index < m) {
                //当前运行任务数量小于可运行任务数量，则任务可以立即启动。
                if (state == NormalIrrigationTaskState.STATE_WAITING) {
                    Calendar startTime = Calendar.getInstance();
                    Calendar finishTime = Calendar.getInstance();
                    finishTime.add(Calendar.SECOND, irrigationTaskStateVo.getExpDuration());
                    irrigationTaskStateVo.setStartTime(startTime.getTime());
                    irrigationTaskStateVo.setFinishTime(finishTime.getTime());
                }
            } else {
                //找出前index个任务中，运行时间最小的
                int index2 = 0;
                IrrigationTaskStateVo temp = null;
                for (IrrigationTaskStateVo irrigationTaskStateVo1 : irrigationTaskStateVos) {
                    if (index2 >= index) {
                        break;
                    }

                    if (!irrigationTaskStateVo1.isFlag()) {
                        index2++;
                        continue;
                    }

                    if (temp == null) {
                        temp = irrigationTaskStateVo1;
                    } else {
                        if (irrigationTaskStateVo1.getFinishTime().before(temp.getFinishTime())) {
                            temp = irrigationTaskStateVo1;
                        }
                    }
                    index2++;
                }

                if (temp != null && temp.getFinishTime() != null) {
                    temp.setFlag(false);
                    Calendar startTime = Calendar.getInstance();
                    startTime.setTime(temp.getFinishTime());
                    Calendar finishTime = (Calendar) startTime.clone();
                    finishTime.add(Calendar.SECOND, irrigationTaskStateVo.getExpDuration());
                    irrigationTaskStateVo.setStartTime(startTime.getTime());
                    irrigationTaskStateVo.setFinishTime(finishTime.getTime());
                }
            }
            index++;
        }
    }
}
