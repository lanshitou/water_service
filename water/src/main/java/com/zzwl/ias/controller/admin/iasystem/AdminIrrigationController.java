package com.zzwl.ias.controller.admin.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.mapper.IrrigationAreaRecordMapper;
import com.zzwl.ias.vo.iasystem.IrriAreaConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/irrigations")
public class AdminIrrigationController {

    private final IrrigationAreaRecordMapper irrigationAreaRecordMapper;

    @Autowired
    public AdminIrrigationController(IrrigationAreaRecordMapper irrigationAreaRecordMapper) {
        this.irrigationAreaRecordMapper = irrigationAreaRecordMapper;
    }

    /**
     * 增加灌溉区
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Result addIrriArea(IrriAreaConfigVo irriAreaConfigVo) {
        ErrorCode errorCode = AppContext.iaSystemManager.addIrrigationArea(irriAreaConfigVo);
        return Result.error(errorCode);
    }

    /**
     * 删除灌溉区
     */
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    public Result delIrriArea(IrriAreaConfigVo irriAreaConfigVo) {
        ErrorCode errorCode = AppContext.iaSystemManager.delIrrigationArea(irriAreaConfigVo);
        return Result.error(errorCode);
    }

    /**
     * 更新灌溉区
     */
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Result updateIrriArea(IrriAreaConfigVo irriAreaConfigVo) {
        ErrorCode errorCode = AppContext.iaSystemManager.updateIrrigationArea(irriAreaConfigVo);
        return Result.error(errorCode);
    }
}
