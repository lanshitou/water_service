package com.zzwl.ias.controller.admin.iasystem;

import com.github.pagehelper.PageInfo;
import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.domain.DeviceRecord;
import com.zzwl.ias.domain.IaSystemRecord;
import com.zzwl.ias.dto.camera.CameraConfigRequestDTO;
import com.zzwl.ias.dto.iasystem.AddIaSystemAndFarmlandDTO;
import com.zzwl.ias.dto.iasystem.IaSystemQueryDTO;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.vo.DevTypeVo;
import com.zzwl.ias.vo.IaSystemListVo;
import com.zzwl.ias.vo.IasystemAndFarmlandVo;
import com.zzwl.ias.vo.iasystem.IaSystemDetailVo;
import com.zzwl.ias.vo.iasystem.IasConfigVo;
import com.zzwl.ias.vo.iasystem.IasDeviceConfigVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * User: HuXin
 * Date: 2018-05-18
 * Time: 14:47
 */
@RestController
@RequestMapping("/api/admin/iasystems")
public class IaSystemConfigController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IaSystemService iaSystemService;

    @Autowired
    public IaSystemConfigController(IaSystemService iaSystemService) {
        this.iaSystemService = iaSystemService;
    }

    //增加智慧农业系统
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Result addIaSystem(IasConfigVo iasConfigVo) {
        if (!iasConfigVo.check()) {
            return Result.error(ErrorCode.INVALID_PARAMS);
        }

        ErrorCode errorCode = iaSystemService.addIaSystem(iasConfigVo);
        if (errorCode != ErrorCode.OK) {
            return Result.error(errorCode);
        }
        return Result.ok(iasConfigVo);
    }

    //删除农业系统
    @RequestMapping(path = "/{iasId}", method = RequestMethod.DELETE)
    public Object delIaSystem(@PathVariable int iasId) {
        ErrorCode errorCode = AppContext.iaSystemManager.delIaSystem(iasId);
        return Result.error(errorCode);
    }

    //修改农业系统
    @RequestMapping(path = "/{iasId}", method = RequestMethod.PUT)
    public Object updateIaSystem(@PathVariable int iasId, IasConfigVo iasConfigVo) {
        iasConfigVo.setId(iasId);
        ErrorCode errorCode = AppContext.iaSystemManager.updateIaSystem(iasConfigVo);
        return Result.error(errorCode);
    }

    //获取指定的智慧农业系统
    @RequestMapping(path = "/{iasId}", method = RequestMethod.GET)
    public Result getIaSystem(@PathVariable int iasId) {
        IaSystemDetailVo iaSystemVo = iaSystemService.getIaSystemVo(iasId);
        if (iaSystemVo == null) {
            return Result.error(ErrorCode.IASYSTEM_NOT_EXIST);
        }
        return Result.ok(iaSystemVo);
    }

//    //获取所有系统列表
//    @RequestMapping(path = "", method = RequestMethod.GET)
//    public Result getAllIaSystems() {
//        List<IaSystemRecord> iaSystemRecordList = iaSystemService.getAllIaSystemRecords();
//        if (CollectionUtils.isEmpty(iaSystemRecordList)) {
//            return Result.ok(ErrorCode.IASYSTEM_NOT_EXIST);
//        }
//        return Result.ok(iaSystemRecordList);
//    }

    //获取气象站
    @RequestMapping(path = "weatherstation", method = RequestMethod.GET)
    public Result getAllWeatherStation() {
        List<IaSystemRecord> iaSystemRecordList = iaSystemService.getAllWeatherStation();
        if (CollectionUtils.isEmpty(iaSystemRecordList)) {
            return Result.ok(ErrorCode.IASYSTEM_NOT_EXIST);
        }
        return Result.ok(iaSystemRecordList);
    }

    //根据关键字获取系统列表
    @RequestMapping(path = "getbykeyword", method = RequestMethod.GET)
    public Result getIaSystemsByKeyword(@RequestParam String keyword) {
        List<IaSystemRecord> iaSystemRecordList = iaSystemService.getIaSystemsByKeyword(keyword);
        if (CollectionUtils.isEmpty(iaSystemRecordList)) {
            return Result.error(ErrorCode.IASYSTEM_NOT_EXIST);
        }
        return Result.ok(iaSystemRecordList);
    }

    //系统详情
    @RequestMapping(path = "detail", method = RequestMethod.GET)
    public Result getIaSystemDetail(@RequestParam int id) {
        IaSystemRecord iaSystemRecord = iaSystemService.getIaSystemById(id);
        if (iaSystemRecord == null) {
            return Result.error(ErrorCode.IASYSTEM_NOT_EXIST);
        }
        return Result.ok(iaSystemRecord);
    }

    //安装设备
    @PostMapping(path = "installdevice")
    public Result installDevice(IasDeviceConfigVo iasDeviceConfigVo) {
        ErrorCode errorCode = AppContext.iaSystemManager.addIasDevice(iasDeviceConfigVo);
        return Result.error(errorCode);
    }

    //拆卸设备
    @PostMapping(path = "uninstalldevice/{id}")
    public Result uninstallDevice(@PathVariable Integer id) {
        ErrorCode errorCode = AppContext.iaSystemManager.delIasDevice(id);
        return Result.error(errorCode);
    }

    /**
     * 设备用途
     */
    @GetMapping("usage")
    public Object getDevType() {
        List<DevTypeVo> usageList = iaSystemService.getDevUsage();
        if (CollectionUtils.isEmpty(usageList)) {
            return Result.error(ErrorCode.CONTROLLER_NOT_EXIST);
        }
        return Result.ok(usageList);
    }

    /**
     * 设备列表
     */
    @GetMapping("devicelist")
    public Object getDevList(@RequestParam int iasId) {
        List<DeviceRecord> deviceList = iaSystemService.getDevListByIasId(iasId);
        if (CollectionUtils.isEmpty(deviceList)) {
            return Result.error(ErrorCode.CONTROLLER_NOT_EXIST);
        }
        return Result.ok(deviceList);
    }

    /**
     * 传感器列表
     */
    @GetMapping("sensorlist")
    public Object getSensorList(@RequestParam int iasId) {
        List<DeviceRecord> deviceList = iaSystemService.getSensorListByIasId(iasId);
        if (CollectionUtils.isEmpty(deviceList)) {
            return Result.error(ErrorCode.CONTROLLER_NOT_EXIST);
        }
        return Result.ok(deviceList);
    }

    /**
     * 向智慧农业系统中添加摄像头
     *
     * @param iasId           智慧农业系统ID
     * @param cameraConfigDTO 智慧农业系统配置
     * @return 添加的结果
     */
    @RequestMapping(path = "/{iasId}/cameras", method = RequestMethod.POST)
    public Object addCamera(@PathVariable int iasId, @RequestBody CameraConfigRequestDTO cameraConfigDTO) {
        cameraConfigDTO.setIaSystemId(iasId);
        cameraConfigDTO.check();

        ErrorCode result = iaSystemService.addCamera(cameraConfigDTO);
        return Result.error(result);
    }

    @RequestMapping(path = "/{iasId}/cameras/{cameraId}", method = RequestMethod.DELETE)
    public Object removeCamera(@PathVariable int iasId, @PathVariable int cameraId) {
        ErrorCode result = iaSystemService.removeCamera(iasId, cameraId);
        return Result.error(result);
    }

    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public Object getUserIasystem(@PathVariable("id") Integer userId) {
        List<IasystemAndFarmlandVo> iasystemAndFarmlandVos = iaSystemService.selectUserSystem(userId);
        return Result.ok(iasystemAndFarmlandVos);
    }

    @RequestMapping(path = "/user/{id}/{iasId}", method = RequestMethod.GET)
    public Object getIasystemFarmland(@PathVariable("id") Integer userId, @PathVariable("iasId") Integer iasId) {
        List<IasystemAndFarmlandVo> iasystemAndFarmlandVos = iaSystemService.selectIasystemAndFarmland(userId, iasId);
        return Result.ok(iasystemAndFarmlandVos);
    }

    /**
     * 用户绑定农田
     *
     * @param addIaSystemAndFarmlandDTO
     * @return
     */
    @RequestMapping(path = "/add_farmland", method = RequestMethod.PUT)
    public Object addIasystem(AddIaSystemAndFarmlandDTO addIaSystemAndFarmlandDTO) {
        iaSystemService.insertIaSystemAndFarmland(addIaSystemAndFarmlandDTO);
        return Result.ok();
    }

    /**
     * 系统修改权限
     *
     * @param userId
     * @param roleId
     * @param iasId
     * @return
     */
    @RequestMapping(path = "/update_role", method = RequestMethod.POST)
    public Object updateIaSystemRole(@RequestParam Integer userId, @RequestParam Integer roleId, @RequestParam Integer iasId) {
        iaSystemService.updateIaSystemRole(userId, roleId, iasId);
        return Result.ok();
    }

    /**
     * 系统列表
     *
     * @param dto
     * @return
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Object selectIaSystemList(IaSystemQueryDTO dto) {
        PageInfo<IaSystemListVo> iaSystemListVoPageInfo = iaSystemService.selectIaSystemList(dto);
        return Result.ok(iaSystemListVoPageInfo);
    }

    /**
     * 系统详情
     *
     * @param iasId
     * @return
     */
    @RequestMapping(path = "/get_ias_detail/{iasId}", method = RequestMethod.GET)
    public Object selectIaSystemDetail(@PathVariable Integer iasId) {
        Map map = iaSystemService.selectIaSystemDetail(iasId);
        return Result.ok(map);
    }

}
