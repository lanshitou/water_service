package com.zzwl.ias.controller.admin.iasystem;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.service.RegionService;
import com.zzwl.ias.vo.RegionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Lvpin on 2018/11/8.
 */
@RestController
@RequestMapping("/api/admin/region")
public class CommonRegionController {
    @Autowired
    private RegionService regionService;

    @GetMapping("/get_region")
    public Result getRegion(@RequestParam("pid") Integer pid) {
        List<RegionVo> regionVos = regionService.selectRegionTree(pid);
        return Result.ok(regionVos);
    }

    @RequestMapping(value = "/add_address", method = RequestMethod.POST)
    public Result addAddress(@RequestParam("pid") Integer pid, @RequestParam("name") String name) {
        int id = regionService.insertRegion(pid, name);
        if (id == 0) {
            return Result.error(ErrorCode.ADDRESS_ALREADY_EXISTS);
        }
        return Result.ok(id);
    }

    @RequestMapping(value = "/get_address", method = RequestMethod.GET)
    public Result getAddress(@RequestParam("id") Integer id) {
        String s = regionService.selectNameById(id);
        return Result.ok(s);
    }
}
