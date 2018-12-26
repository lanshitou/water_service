package com.zzwl.ias.configuration;

import com.zzwl.ias.iasystem.DeviceBindingMap;
import com.zzwl.ias.iasystem.IaSystemManager;
import com.zzwl.ias.iasystem.communication.IaCommServer;
import com.zzwl.ias.iasystem.device.DeviceManager;
import com.zzwl.ias.iasystem.event.EventDispatcher;
import com.zzwl.ias.service.IaSystemService;
import com.zzwl.ias.timer.TimerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by HuXin on 2017/12/13.
 */
@Configuration
public class AppConfig {

    @Bean
    public TimerManager timerManager() {
        TimerManager timerManager = new TimerManager();
        timerManager.Init();
        return timerManager;
    }

    @Bean
    public IaCommServer iaCommServer() {
        return new IaCommServer(6667);
    }

    @Bean
    public DeviceManager deviceManager(IaCommServer iaCommServer) {
        return new DeviceManager(iaCommServer);
    }

    @Bean
    public IaSystemManager iaSystemManager(DeviceManager deviceManager, IaSystemService iaSystemService) {
        return new IaSystemManager(deviceManager, iaSystemService);
    }

    @Bean
    public EventDispatcher eventDispatcher() {
        return new EventDispatcher(5);
    }

    @Bean
    public DeviceBindingMap deviceBindingMap() {
        return new DeviceBindingMap();
    }
}
