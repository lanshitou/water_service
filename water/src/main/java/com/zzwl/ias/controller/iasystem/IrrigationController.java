package com.zzwl.ias.controller.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.iasystem.FarmlandDTO;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.service.IrrigationService;
import com.zzwl.ias.dto.irrigation.ReqUpdateNormalIrrigationDTO;
import com.zzwl.ias.vo.IrrigationTaskStateVo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedList;
import java.util.List;


/**
 * Description:
 * User: HuXin
 * Date: 2018-09-28
 * Time: 8:31
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/iaSystems/{iaSystemId}/irrigation")
public class IrrigationController {
    private final IaSystemService iaSystemService;

    @Autowired
    public IrrigationController(IaSystemService iaSystemService) {
        this.iaSystemService = iaSystemService;
    }

    //获取灌溉任务信息
    @RequiresAuthentication
    @RequestMapping(path = "/tasks/summary", method = RequestMethod.GET)
    public Result<?> listIrriArea(@PathVariable Integer iaSystemId) {
        List<FarmlandDTO> farmlandDTOS = AppContext.getBean(IrrigationService.class).listIrrigationTaskSummary(CurrentUserUtil.getCurrentUserId(), iaSystemId);
        return Result.ok(farmlandDTOS);
    }

    @RequiresAuthentication
    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public Result<?> listIrriTask(@PathVariable Integer iaSystemId) {
        List<IrrigationTaskStateVo> irrigationTaskStateVos = AppContext.getBean(IrrigationService.class).listIrrigationTask(CurrentUserUtil.getCurrentUserId(), iaSystemId);
        return Result.ok(irrigationTaskStateVos);
    }

    /** 添加或删除灌溉任务
     * @param iaSystemId 智慧农业系统ID
     * @param irrigationRequestVo 灌溉任务更新请求
     * @return 更新结果
     */
    @RequiresAuthentication
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Result<?> updateIrrigationTask(@PathVariable Integer iaSystemId, @RequestBody ReqUpdateNormalIrrigationDTO irrigationRequestVo) {
        irrigationRequestVo.check();
        irrigationRequestVo.setIaSystemId(iaSystemId);
        iaSystemService.updateIrrigationTasks(irrigationRequestVo);
        return Result.ok();
    }

    /** 获取历史灌溉任务
     * @param iaSystemId 智慧农业系统gID
     * @param irriAreaId 灌溉区ID
     * @param offset 偏移值
     * @param limit 数量
     * @return 历史灌溉任务
     */
    @RequiresAuthentication
    @RequestMapping(path = "/{irriAreaId}/history")
    public Result<?> listHistoryIrrigation(@PathVariable Integer iaSystemId, @PathVariable Integer irriAreaId,
                                      @RequestParam Integer offset, @RequestParam Integer limit) {
        LinkedList<IrrigationTaskStateVo> irrigationTaskStateVos = iaSystemService.listHistoryIrrigation(
                CurrentUserUtil.getCurrentUserId(), iaSystemId, irriAreaId, offset, limit);
        AssertEx.isNotEmpty(irrigationTaskStateVos);
        return Result.ok(irrigationTaskStateVos);
    }
}
