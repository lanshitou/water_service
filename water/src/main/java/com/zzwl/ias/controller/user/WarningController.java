package com.zzwl.ias.controller.user;

import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.warning.WarningCountDto;
import com.zzwl.ias.dto.warning.WarningDTO;
import com.zzwl.ias.dto.warning.WarningSummaryDTO;
import com.zzwl.ias.service.MessageService;
import com.zzwl.ias.service.WarningService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description: 告警
 * User: MaYong
 * Date: 2018-06-29
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/alarms")
public class WarningController {

    private final MessageService messageService;

    private final WarningService warningService;

    @Autowired
    public WarningController(MessageService messageService, WarningService warningService) {
        this.messageService = messageService;
        this.warningService = warningService;
    }

    //正在告警的数量
    @RequiresAuthentication
    @RequestMapping(value = "/current/count", method = RequestMethod.GET)
    public Object countWarning() {
        int count = messageService.countWarning();
        WarningCountDto warningCountDto = new WarningCountDto();
        warningCountDto.setCount(count);
        return Result.ok(warningCountDto);
    }

    //TODO 接口梳理

    /** 获取告警详情
     * @param warningId 告警ID
     * @return 告警详情
     */
    @RequiresAuthentication
    @RequestMapping(value = "/{warningId}", method = RequestMethod.GET)
    public Result<?> getWarningDetail(@PathVariable Long warningId){
        WarningDTO warningDTO = warningService.getWarningDetail(warningId);
        return Result.ok(warningDTO);
    }

    /** 清除指定的告警，目前仅支持灌溉任务失败告警的清除
     * @param warningId 告警ID
     * @return 清除结果
     */
    @RequiresAuthentication
    @RequestMapping(value = "/{warningId}/clear", method = RequestMethod.POST)
    public Result<?> clearWarning(@PathVariable Long warningId){
        warningService.clearWarning(CurrentUserUtil.getCurrentUserId(), warningId);
        return getWarningDetail(warningId);
    }

    /** 获取告警摘要信息列表
     * @param iasId 智慧农业系统ID
     * @param cleared 当前告警or历史告警
     * @param type 告警类别
     * @param offset 偏移量，当前告警时无效
     * @param limit 数量
     * @return 告警摘要信息列表
     */
    @RequiresAuthentication
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result<?> listWarningSummary(@RequestParam Integer iasId, @RequestParam Integer cleared, @RequestParam Integer type, @RequestParam Integer offset, @RequestParam Integer limit) {
        List<WarningSummaryDTO> warningDTOS = warningService.listWarningSummary(CurrentUserUtil.getCurrentUserId(), iasId, type, cleared, offset, limit);
        return Result.ok(warningDTOS);
    }
}
