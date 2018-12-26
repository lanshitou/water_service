package com.zzwl.ias.controller.iasystem;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.configuration.CameraConfig;
import com.zzwl.ias.dto.camera.CameraDTO;
import com.zzwl.ias.dto.camera.VideoAccessTokenDTO;
import com.zzwl.ias.iasystem.IaSystem;
import com.zzwl.ias.service.IaSystemService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

/**
 * Description:
 * User: HuXin
 * Date: 2018-09-28
 * Time: 8:26
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/iaSystems")
public class CameraController {
    private final IaSystemService iaSystemService;

    @Autowired
    public CameraController(IaSystemService iaSystemService) {
        this.iaSystemService = iaSystemService;
    }

    /** 获取摄像头accessToken
     * @return 摄像头的accessToken
     */
    @RequiresAuthentication
    @RequestMapping(path = "/cameras/accessToken", method = RequestMethod.GET)
    public Object getCameraAccessToken() {
        CameraConfig cameraConfig = AppContext.getBean(CameraConfig.class);
        VideoAccessTokenDTO videoAccessTokenDTO = cameraConfig.getVideoAccessTokenDTO();
        if (videoAccessTokenDTO == null) {
            return Result.error(ErrorCode.ACCESS_TOKEN_UNAVAILABLE);
        }
        return Result.ok(videoAccessTokenDTO);
    }

    /** 获取智慧农业系统摄像头列表
     * @param iaSystemId 智慧农业系统ID
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/{iaSystemId}/cameras", method = RequestMethod.GET)
    public Object getAllCameras(@PathVariable int iaSystemId) {
        LinkedList<CameraDTO> cameraDTOS = new LinkedList<>();
        ErrorCode result = iaSystemService.getAllCameras(iaSystemId, cameraDTOS);
        if (result != ErrorCode.OK) {
            return Result.error(result);
        }
        return Result.ok(cameraDTOS);
    }
}
