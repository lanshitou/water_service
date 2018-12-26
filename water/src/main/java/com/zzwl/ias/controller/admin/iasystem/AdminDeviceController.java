package com.zzwl.ias.controller.admin.iasystem;

import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.service.DeviceService;
import com.zzwl.ias.vo.DeviceConfigVo;
import com.zzwl.ias.vo.DeviceTopoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by HuXin on 2018/2/26.
 */
@RestController
@RequestMapping("/api/admin/iasystems/{iasId}/devices")
public class AdminDeviceController {
    private final DeviceService deviceService;

    @Autowired
    public AdminDeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    //增加设备
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Object addDevices(@PathVariable int iasId, DeviceConfigVo deviceConfigVo) {
        if (!deviceConfigVo.check()) {
            return Result.error(ErrorCode.INVALID_PARAMS);
        }
        deviceConfigVo.setIasId(iasId);
        deviceConfigVo.generateDevId();
        ErrorCode errorCode = deviceService.addDevice(deviceConfigVo);
        return Result.error(errorCode);
    }

    //删除设备
    @RequestMapping(path = "/delete/{devId}", method = RequestMethod.POST)
    public Object delDevice(@PathVariable int iasId, @PathVariable long devId) {
        ErrorCode errorCode = deviceService.delDevice(iasId, devId);
        return Result.error(errorCode);
    }

    //修改设备信息
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Object updateDevices(@PathVariable int iasId, DeviceConfigVo deviceConfigVo) {
        if (!deviceConfigVo.updateCheck()) {
            return Result.error(ErrorCode.INVALID_PARAMS);
        }
        ErrorCode errorCode = deviceService.updateDevice(deviceConfigVo);
        return Result.error(errorCode);
    }

    //设备拓扑结构图
    @GetMapping("/topo")
    public Object getDeviceTopo(@PathVariable Integer iasId) {
        DeviceTopoVo deviceTopo = deviceService.getDeviceTopo(iasId);
        if (deviceTopo == null) {
            return Result.error(ErrorCode.CONTROLLER_NOT_EXIST);
        }
        return Result.ok(deviceTopo);
    }

    public static void main(String[] args) {
        double π = Math.PI;
        System.out.println(Math.sin(π/3));
    }
}
