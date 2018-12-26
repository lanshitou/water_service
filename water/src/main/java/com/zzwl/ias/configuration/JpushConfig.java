package com.zzwl.ias.configuration;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NativeHttpClient;
import cn.jpush.api.JPushClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class JpushConfig {
    @Value("${jpush.MASTER_SECRET}")
    private String MASTER_SECRET;
    @Value("${jpush.APP_KEY}")
    private String APP_KEY;

    @Bean
    public JPushClient getJPushClient() {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, clientConfig);
        String authCode = ServiceHelper.getBasicAuthorization(APP_KEY, MASTER_SECRET);
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        NativeHttpClient httpClient = new NativeHttpClient(authCode, null, clientConfig);
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use NativeHttpClient.
//        ApacheHttpClient httpClient = new ApacheHttpClient(authCode, null, clientConfig);
        jpushClient.getPushClient().setHttpClient(httpClient);
        return jpushClient;
    }
}
