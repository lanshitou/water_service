package com.zzwl.ias.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedList;

/**
 * Created by HuXin on 2018/1/16.
 */
public class IrrigationRequestVo {
    public final static int REQUEST_TYPE_ADD = 1;
    public final static int REQUEST_TYPE_REMOVE = 2;
    private int userId;
    private int iaSystemId;
    private int type;
    private IrrigationTaskVo tasks[];
    private LinkedList<IrrigationTaskStateVo> allTaskState;

    @JsonIgnore
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public IrrigationTaskVo[] getTasks() {
        return tasks;
    }

    public void setTasks(IrrigationTaskVo[] tasks) {
        this.tasks = tasks;
    }

    public int getIaSystemId() {
        return iaSystemId;
    }

    public void setIaSystemId(int iaSystemId) {
        this.iaSystemId = iaSystemId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LinkedList<IrrigationTaskStateVo> getAllTaskState() {
        return allTaskState;
    }

    public void setAllTaskState(LinkedList<IrrigationTaskStateVo> allTaskState) {
        this.allTaskState = allTaskState;
    }

    public boolean hasDupTask() {
        for (IrrigationTaskVo irrigationTaskVo : tasks) {
            for (IrrigationTaskVo irrigationTaskVo1 : tasks) {
                if (irrigationTaskVo != irrigationTaskVo1
                        && irrigationTaskVo.getIrrigationAreaId() == irrigationTaskVo1.getIrrigationAreaId()) {
                    return true;
                }
            }
        }
        return false;
    }

    @JsonIgnore
    public boolean isEmpty() {
        if (tasks == null) {
            return true;
        } else {
            return false;
        }
    }
}
