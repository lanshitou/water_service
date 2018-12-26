package com.zzwl.ias.controller.iasystem;

import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.dto.iasystem.FarmlandDTO;
import com.zzwl.ias.service.IaSystemService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-28
 * Time: 16:59
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/iaSystems/{iaSystemId}/farmlands")
public class FarmlandController {
    @Autowired
    private IaSystemService iaSystemService;

    //获取园区详情
    @RequiresAuthentication
    @RequestMapping(path = "/{farmlandId}", method = RequestMethod.GET)
    public Result<?> getFarmlandDetail(@PathVariable Integer iaSystemId, @PathVariable Integer farmlandId) {
        FarmlandDTO farmlandDTO = iaSystemService.getFarmlandDetail(iaSystemId, farmlandId, CurrentUserUtil.getCurrentUserId());
        return Result.ok(farmlandDTO);
    }
}
