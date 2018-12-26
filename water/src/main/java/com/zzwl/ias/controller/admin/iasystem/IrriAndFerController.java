package com.zzwl.ias.controller.admin.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.vo.iasystem.IrriFerSystemConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/irriandfers")
public class IrriAndFerController {

    @Autowired
    IaSystemService iaSystemService;

    /**
     * 新增水肥一体化
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Result addIrriAndFer(IrriFerSystemConfigVo irriFerSystemConfigVo) {
        ErrorCode errorCode = AppContext.iaSystemManager.addIrriAndFerSystem(irriFerSystemConfigVo);
        return Result.error(errorCode);
    }

    /**
     * 删除水肥一体化
     */
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public Result delIrriAndFer(@RequestParam int id,
                                @RequestParam int iasId) {
        ErrorCode errorCode = AppContext.iaSystemManager.delIrriAndFerSystem(id, iasId);
        return Result.error(errorCode);
    }

    /**
     * 更新水肥一体化
     */
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Result updateIrriAndFer(IrriFerSystemConfigVo irriFerSystemConfigVo) {
        ErrorCode errorCode = AppContext.iaSystemManager.updateIrriAndFerSystem(irriFerSystemConfigVo);
        return Result.error(errorCode);
    }
}
