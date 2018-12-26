package com.zzwl.ias.configuration;

import com.zzwl.ias.AppContext;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.constant.SysConfigName;
import com.zzwl.ias.dto.camera.GetAccessTokenFromYsDTO;
import com.zzwl.ias.dto.camera.VideoAccessTokenDTO;
import com.zzwl.ias.iasystem.constant.CameraConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;

/**
 * Description:
 * User: HuXin
 * Date: 2018-07-26
 * Time: 14:57
 */
@Configuration
@PropertySource("classpath:application.properties")
public class CameraConfig {

    @Value("${ys.camera.appKey}")
    private String appKey;

    @Value("${ys.camera.secret}")
    private String appSecret;

    @Value("${ys.camera.getAccessTokenUrl}")
    private String queryAccessTokenUrl;

    @Value("${ys.camera.addDevUrl}")
    private String addDevUrl;

    @Value("${ys.camera.rmDevUrl}")
    private String rmDevUrl;

    @Value("${ys.camera.openVideoUrl}")
    private String openVideoUrl;

    @Value("${ys.camera.getVideoAddrUrl}")
    private String queryVideoAddrUrl;

    @Value("${ys.camera.getDevCapabilityUrl}")
    private String queryDevCapabilityUrl;

    @Value("${ys.camera.openWsUrl}")
    private String openWsUrl;

    @Value("${ys.camera.getWsUrl}")
    private String queryWsUrl;

    private VideoAccessTokenDTO videoAccessTokenDTO;

    private final Object lock = new Object();

    public void load() {
        //从数据库中读取配置
        VideoAccessTokenDTO accessTokenDTO =
                AppContext.globalConfig.getConfig(SysConfigName.CAMERA_CFG, VideoAccessTokenDTO.class);
        if (accessTokenDTO != null) {
            int ONE_DAY = 24 * 60 * 60 * 1000;
            if (Calendar.getInstance().getTimeInMillis() + ONE_DAY < accessTokenDTO.getExpDate().getTimeInMillis()) {
                videoAccessTokenDTO = accessTokenDTO;
            }
        }

        if (videoAccessTokenDTO == null) {
            //未获取到Token，则1秒后进行获取
            AppContext.timerManager.createTimer(this::updateToken, 1, false);
        } else {
            //获取到Token，则在过期时进行获取
            AppContext.timerManager.createTimer(this::updateToken,
                    (int) (videoAccessTokenDTO.getExpDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000,
                    false);
        }
    }

    private void updateToken() {
        ErrorCode errorCode = queryTokenFromYs();
        if (errorCode == ErrorCode.OK) {
            //更新成功，则在下一次过期时进行更新
            AppContext.timerManager.createTimer(this::updateToken,
                    (int) (videoAccessTokenDTO.getExpDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000,
                    false);
        } else {
            //若更新失败，则30秒后再次更新
            AppContext.timerManager.createTimer(this::updateToken, 30, false);
        }
    }

    private ErrorCode queryTokenFromYs() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request;
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        try {
            map.add("appKey", appKey);
            map.add("appSecret", appSecret);
            request = new HttpEntity<>(map, headers);
            GetAccessTokenFromYsDTO getAccessTokenFromYsDTO = restTemplate.postForObject(queryAccessTokenUrl, request, GetAccessTokenFromYsDTO.class);
            if (!getAccessTokenFromYsDTO.getCode().equals(CameraConstant.CODE_OK)) {
                return ErrorCode.ACCESS_TOKEN_UNAVAILABLE;
            }

            //读取到的Token写入数据库
            VideoAccessTokenDTO videoAccessTokenDTO = new VideoAccessTokenDTO();
            videoAccessTokenDTO.setAccessToken(getAccessTokenFromYsDTO.getData().getAccessToken());
            //过期时间设置为6天后的凌晨2点整
            Calendar expDate = Calendar.getInstance();
            expDate.add(Calendar.DATE, 6);
            expDate.set(Calendar.HOUR, 2);
            expDate.set(Calendar.MINUTE, 0);
            expDate.set(Calendar.SECOND, 0);
            videoAccessTokenDTO.setExpDate(expDate);
            ErrorCode errorCode =
                    AppContext.globalConfig.setConfig(SysConfigName.CAMERA_CFG, videoAccessTokenDTO);
            if (errorCode != ErrorCode.OK) {
                return errorCode;
            }
            //更新
            synchronized (lock) {
                this.videoAccessTokenDTO = videoAccessTokenDTO;
            }
            return ErrorCode.OK;
        } catch (Exception e) {
            return ErrorCode.ACCESS_TOKEN_UNAVAILABLE;
        }
    }

    public String getAppKey() {
        return appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getQueryAccessTokenUrl() {
        return queryAccessTokenUrl;
    }

    public String getAddDevUrl() {
        return addDevUrl;
    }

    public String getRmDevUrl() {
        return rmDevUrl;
    }

    public String getOpenVideoUrl() {
        return openVideoUrl;
    }

    public String getQueryVideoAddrUrl() {
        return queryVideoAddrUrl;
    }

    public String getQueryDevCapabilityUrl() {
        return queryDevCapabilityUrl;
    }

    public VideoAccessTokenDTO getVideoAccessTokenDTO() {
        synchronized (lock) {
            return videoAccessTokenDTO;
        }
    }

    public String getAccessToken() {
        synchronized (lock) {
            return videoAccessTokenDTO != null ? videoAccessTokenDTO.getAccessToken() : null;
        }
    }

    public String getOpenWsUrl() {
        return openWsUrl;
    }

    public String getQueryWsUrl() {
        return queryWsUrl;
    }
}
