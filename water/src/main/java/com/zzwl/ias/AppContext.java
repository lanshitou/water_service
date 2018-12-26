package com.zzwl.ias;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzwl.ias.configuration.CameraConfig;
import com.zzwl.ias.configuration.GlobalConfig;
import com.zzwl.ias.iasystem.DeviceBindingMap;
import com.zzwl.ias.iasystem.IaSystemManager;
import com.zzwl.ias.iasystem.device.DeviceManager;
import com.zzwl.ias.iasystem.event.EventDispatcher;
import com.zzwl.ias.service.DeviceService;
import com.zzwl.ias.service.FarmlandService;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.service.MessageService;
import com.zzwl.ias.timer.TimerManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Console;

/**
 * Created by HuXin on 2017/12/12.
 */
@Component
public class AppContext implements ApplicationContextAware {
    private static ApplicationContext appContext;

    public static ObjectMapper objectMapper;
    public static FarmlandService farmlandService;
    public static TimerManager timerManager;
    public static DeviceManager deviceManager;
    public static IaSystemManager iaSystemManager;
    public static DeviceService deviceService;
    public static IaSystemService iaSystemService;
    public static MessageService messageService;

    public static EventDispatcher eventDispatcher;
    public static DeviceBindingMap deviceBindingMap;

    public static GlobalConfig globalConfig;

    private void appInit() {
        objectMapper = new ObjectMapper();
        farmlandService = appContext.getBean(FarmlandService.class);
        timerManager = appContext.getBean(TimerManager.class);
        deviceManager = appContext.getBean(DeviceManager.class);
        deviceService = appContext.getBean(DeviceService.class);
        iaSystemService = appContext.getBean(IaSystemService.class);
        iaSystemManager = appContext.getBean(IaSystemManager.class);
        eventDispatcher = appContext.getBean(EventDispatcher.class);
        deviceBindingMap = appContext.getBean(DeviceBindingMap.class);
        messageService = appContext.getBean(MessageService.class);
        globalConfig = appContext.getBean(GlobalConfig.class);

        eventDispatcher.start();
        globalConfig.load();
        appContext.getBean(CameraConfig.class).load();
        iaSystemManager.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
        appInit();
    }

    public static <T> T getBean(Class<T> classType) throws BeansException {
        return appContext.getBean(classType);
    }

}
