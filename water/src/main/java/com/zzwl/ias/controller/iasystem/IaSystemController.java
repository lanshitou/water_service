package com.zzwl.ias.controller.iasystem;

import com.zzwl.ias.common.AssertEx;
import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.iasystem.IaSystemDTO;
import com.zzwl.ias.dto.warning.WarningStatisticsDTO;
import com.zzwl.ias.iasystem.constant.IaSystemConstant;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.dto.iasystem.IaSystemBasicInfoDTO;
import com.zzwl.ias.vo.iasystem.IaSystemVo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

import static com.zzwl.ias.AppContext.iaSystemManager;

/**
 * Description:
 * User: HuXin
 * Date: 2018-05-26
 * Time: 11:29
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/iaSystems")
public class IaSystemController {

    private final IaSystemService iaSystemService;

    @Autowired
    public IaSystemController(IaSystemService iaSystemService) {
        this.iaSystemService = iaSystemService;
    }


    /** 获取用户的智慧农业系统列表
     * @return 用户的智慧农业系统列表
     */
    @RequiresAuthentication
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Result<?> getUserIaSystemBasicInfo() {
        LinkedList<IaSystemBasicInfoDTO> iaSystemVos = iaSystemService.getIaSystemsByUser(CurrentUserUtil.getCurrentUserId());
        if (iaSystemVos.size() == 0) {
            return Result.error(ErrorCode.EMPTY);
        } else {
            return Result.ok(iaSystemVos);
        }
    }


    /** 获取智慧农业系统状态统计信息
     * @param iasId 智慧农业系统ID
     * @return 智慧农业系统统计信息
     */
    @RequiresAuthentication
    @RequestMapping(path = "/{iasId}/state", method = RequestMethod.GET)
    public Result<?> getIaSystemState(@PathVariable Integer iasId){
        WarningStatisticsDTO warningStatisticsDTO = iaSystemService.getIaSystemState(CurrentUserUtil.getCurrentUserId(), iasId);
        return Result.ok(warningStatisticsDTO);
    }

    /** 获取智慧农业系统摘要信息
     * @param iasId 智慧农业系统ID
     * @return 智慧农业系统摘要信息
     */
    @RequiresAuthentication
    @RequestMapping(path = "/{iasId}/summary", method = RequestMethod.GET)
    public Result<?> getIaSystemSummary(@PathVariable Integer iasId){
        IaSystemDTO iaSystemDTO = iaSystemService.getIaSystemSummary(iasId, CurrentUserUtil.getCurrentUserId());
        return Result.ok(iaSystemDTO);
    }

    /** 切换智慧农业系统工作模式
     * @param iaSystemId 智慧农业系统ID
     * @param mode 工作模式
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/{iaSystemId}/mode", method = RequestMethod.POST)
    public Object changeMode(@PathVariable Integer iaSystemId, @RequestParam Integer mode) {
        iaSystemService.setWorkMode(CurrentUserUtil.getCurrentUserId(), iaSystemId, mode);
        return Result.ok();
    }

}
