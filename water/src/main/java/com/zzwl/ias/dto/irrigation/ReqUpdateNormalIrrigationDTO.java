package com.zzwl.ias.dto.irrigation;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.dto.RequestDTO;
import com.zzwl.ias.vo.iasystem.irrigation.NormalIrrigationTaskVo;

/**
 * Description:添加/停止灌溉任务请求
 * User: HuXin
 * Date: 2018-04-10
 * Time: 10:32
 */
public class ReqUpdateNormalIrrigationDTO extends RequestDTO{
    public final static int REQUEST_TYPE_ADD = 1;
    public final static int REQUEST_TYPE_REMOVE = 2;

    private Integer iaSystemId;
    private Integer requestType;
    private NormalIrrigationTaskVo tasks[];

    public void check() {
        AssertEx.isTrue(requestType != null, ErrorCode.INVALID_PARAMS);
        AssertEx.isTrue(requestType == REQUEST_TYPE_ADD || requestType == REQUEST_TYPE_REMOVE, ErrorCode.INVALID_PARAMS);
        AssertEx.isTrue(tasks != null && tasks.length != 0, ErrorCode.INVALID_PARAMS);
        for (NormalIrrigationTaskVo task : tasks) {
            task.check(requestType);
        }
    }

    public int getIaSystemId() {
        return iaSystemId;
    }

    public void setIaSystemId(int iaSystemId) {
        this.iaSystemId = iaSystemId;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public NormalIrrigationTaskVo[] getTasks() {
        return tasks;
    }

    public void setTasks(NormalIrrigationTaskVo[] tasks) {
        this.tasks = tasks;
    }
}
