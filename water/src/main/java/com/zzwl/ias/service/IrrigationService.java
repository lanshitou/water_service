package com.zzwl.ias.service;

import com.zzwl.ias.dto.iasystem.FarmlandDTO;
import com.zzwl.ias.vo.IrrigationTaskStateVo;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-30
 * Time: 9:08
 */
public interface IrrigationService {

    /** 记录灌溉任务并通知用户
     * @param task 灌溉任务
     */
    void recordIrrigationTaskAndNotify(IrrigationTaskStateVo task);


    /** 获取灌溉任务信息
     * @param userId 用户ID
     * @param iaSystemId 智慧农业系统ID
     * @return 灌溉任务信息（使用FarmlandDTO返回，但是仅添加灌溉任务相关内容）
     */
    LinkedList<IrrigationTaskStateVo> listIrrigationTask(Integer userId, Integer iaSystemId);

    /** 获取灌溉任务摘要信息
     * @param userId 用户ID
     * @param iaSystemId 智慧农业系统ID
     * @return 灌溉任务信息列表
     */
    LinkedList<FarmlandDTO> listIrrigationTaskSummary(Integer userId, Integer iaSystemId);
}
