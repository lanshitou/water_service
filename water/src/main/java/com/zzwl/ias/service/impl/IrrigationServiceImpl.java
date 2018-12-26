package com.zzwl.ias.service.impl;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.domain.IrrigationTaskRecord;
import com.zzwl.ias.dto.iasystem.FarmlandDTO;
import com.zzwl.ias.dto.iasystem.IaSystemDTO;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.iasystem.constant.IaSystemConstant;
import com.zzwl.ias.iasystem.irrigation.task.NormalIrriTaskResult;
import com.zzwl.ias.iasystem.message.IrrigationMessageUtil;
import com.zzwl.ias.iasystem.warning.IrrigationWarning;
import com.zzwl.ias.mapper.IrrigationTaskRecordExtMapper;
import com.zzwl.ias.service.IrrigationService;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-30
 * Time: 15:06
 */
@Service
public class IrrigationServiceImpl implements IrrigationService{
    private final IrrigationTaskRecordExtMapper irrigationTaskRecordExtMapper;

    @Autowired
    public IrrigationServiceImpl(IrrigationTaskRecordExtMapper irrigationTaskRecordExtMapper) {
        this.irrigationTaskRecordExtMapper = irrigationTaskRecordExtMapper;
    }

    /**
     * 记录灌溉任务并通知用户
     *
     * @param task 灌溉任务
     */
    @Override
    public void recordIrrigationTaskAndNotify(IrrigationTaskStateVo task) {
        IrrigationTaskRecord record = new IrrigationTaskRecord();
        record.setIasId(task.getIasId());
        record.setFarmlandId(task.getFarmlandId());
        record.setIrriAreaId(task.getIrriAreaId());
        record.setExpDuration(task.getExpDuration());
        record.setCreateTime(task.getAddTime());
        record.setStartTime(task.getStartTime());
        record.setFinishTime(task.getFinishTime());
        record.setCreateUser(task.getCreateUser().getUid());
        record.setDeleteUser(task.getDeleteUser() != null ?  task.getDeleteUser().getUid() : null);
        record.setStatus(task.getStatus());
        record.setResult(task.getResult().getCode());
        if (task.getResult() == NormalIrriTaskResult.OK){
            record.setIrriDuration(task.getExpDuration());
        }else{
            if (task.getIrriStartTime() == null){
                record.setIrriDuration(0);
            }else{
                int irriDuration = (int)((Calendar.getInstance().getTime().getTime() - task.getIrriStartTime().getTime()) / 1000);
                record.setIrriDuration(irriDuration);
            }
        }
        task.setIrriDuration(record.getIrriDuration());
        irrigationTaskRecordExtMapper.insert(record);

        if (task.getResult() == NormalIrriTaskResult.OK || task.getResult() == NormalIrriTaskResult.USER_CANCEL_OK){
            IrrigationMessageUtil.createTaskStateChangeMsg(task);
        }else{
            AppContext.getBean(IrrigationWarning.class).checkIrriWarning(task);
        }
    }

    /**
     * 获取灌溉任务信息
     *
     * @param userId     用户ID
     * @param iaSystemId 智慧农业系统ID
     * @return 灌溉任务信息（使用FarmlandDTO返回，但是仅添加灌溉任务相关内容）
     */
    @Override
    public LinkedList<FarmlandDTO> listIrrigationTaskSummary(Integer userId, Integer iaSystemId) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(iaSystemId);
        AssertEx.isTrue(iaSystem != null, ErrorCode.IASYSTEM_NOT_EXIST);
        IaSystemDTO iaSystemDTO = iaSystem.getIaSystemInfo(IaSystemConstant.QUERY_TYPE_IRRI_TASK);

        //TODO 权限管理

        return iaSystemDTO.getFarmlands();
    }

    /**
     * 获取灌溉任务信息
     *
     * @param userId     用户ID
     * @param iaSystemId 智慧农业系统ID
     * @return 灌溉任务信息类表
     */
    @Override
    public LinkedList<IrrigationTaskStateVo> listIrrigationTask(Integer userId, Integer iaSystemId) {
        IaSystem iaSystem = AppContext.iaSystemManager.getIaSystemById(iaSystemId);
        AssertEx.isTrue(iaSystem != null, ErrorCode.IASYSTEM_NOT_EXIST);

        LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos = iaSystem.listTaskStateVo();
        AssertEx.isNotEmpty(irrigationTaskStateVos);

        for (IrrigationTaskStateVo irrigationTaskStateVo : irrigationTaskStateVos){
            irrigationTaskStateVo.insertUserInfo();
            irrigationTaskStateVo.stateTranslateForApi();
        }

        return irrigationTaskStateVos;
    }
}
