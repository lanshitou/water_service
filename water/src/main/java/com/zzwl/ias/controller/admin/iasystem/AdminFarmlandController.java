package com.zzwl.ias.controller.admin.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.service.FarmlandService;
import com.zzwl.ias.vo.iasystem.FarmlandConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/farmlands")
public class AdminFarmlandController {

    @Autowired
    FarmlandService farmlandService;

    //增加大棚
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Result addFarmland(FarmlandConfigVo farmlandConfigVo) {
        ErrorCode errorCode = farmlandService.addFarmland(farmlandConfigVo);
        return Result.error(errorCode);
    }

    //删除大棚
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public Object delFarmland(@RequestParam int id,
                              @RequestParam int iasId) {
        ErrorCode errorCode = AppContext.iaSystemManager.delFarmland(id, iasId);
        return Result.error(errorCode);
    }

    //修改大棚
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Result update(FarmlandConfigVo farmlandConfigVo) {
        ErrorCode errorCode = AppContext.iaSystemManager.updateFarmland(farmlandConfigVo);
        return Result.error(errorCode);
    }
}
