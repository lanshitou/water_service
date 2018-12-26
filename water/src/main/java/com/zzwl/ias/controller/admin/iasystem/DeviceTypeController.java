package com.zzwl.ias.controller.admin.iasystem;

import com.zzwl.ias.common.Result;
import com.zzwl.ias.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * User: HuXin
 * Date: 2018-05-29
 * Time: 10:28
 */

@RestController
@RequestMapping("/api/admin/device")
public class DeviceTypeController {
    @Autowired
    private DeviceService deviceService;

    //获取设备类型
    @GetMapping("/type")
    public Object getDevType() {
        return Result.ok(deviceService.selectAllDeviceType());
        //return Result.ok(DeviceType.getDevType());
    }
}
