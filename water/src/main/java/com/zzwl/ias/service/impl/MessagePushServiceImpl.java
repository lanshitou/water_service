package com.zzwl.ias.service.impl;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.zzwl.ias.service.MessagePushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-11
 * Time: 15:21
 */
@Service
public class MessagePushServiceImpl implements MessagePushService {
    private ObjectMapper objectMapper;
    private final JPushClient jPushClient;
    private LinkedBlockingQueue<PushPayload> pushList;
    private PushTask pushTask;

    @Autowired
    public MessagePushServiceImpl(JPushClient jPushClient) {
        this.jPushClient = jPushClient;
        objectMapper = new ObjectMapper();
        pushList = new LinkedBlockingQueue<>();
        pushTask = new PushTask(jPushClient, pushList);
        Thread t = new Thread(pushTask);
        t.setName("MessagePushService");
        t.start();
    }

    @Override
    public void push(String title, String content, Object extent, List<Integer> users) {
        if (users.size() == 0) {
            return;
        }

        String ext;
        try {
            ext = objectMapper.writeValueAsString(extent);
        } catch (JsonProcessingException e) {
            return;
        }

        AndroidNotification androidNotification = AndroidNotification.newBuilder()
                .setAlert(content)
                .setTitle(title)
                .addExtra("ext", ext)
                .build();

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("title", new JsonPrimitive(title));
        jsonObject.add("body", new JsonPrimitive(content));
        IosNotification iosNotification = IosNotification.newBuilder()
                .setAlert(jsonObject)
                .addExtra("ext", ext)
                .build();

        LinkedList<String> uids = new LinkedList<>();
        for (Integer uid : users) {
            uids.add(uid.toString());
        }

        PushPayload pushPayload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias(uids))
                        .build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(androidNotification)
                        .addPlatformNotification(iosNotification)
                        .build())
                .build();

        try {
            pushList.put(pushPayload);
        } catch (InterruptedException e) {
            //TODO
        }
    }

    private class PushTask implements Runnable {
        private JPushClient jPushClient;
        private LinkedBlockingQueue<PushPayload> pushList;

        private PushTask(JPushClient jPushClient, LinkedBlockingQueue<PushPayload> pushList) {
            this.jPushClient = jPushClient;
            this.pushList = pushList;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    PushPayload payload = pushList.take();
                    jPushClient.sendPush(payload);
                } catch (Exception e) {
                    //TODO
                }
            }
        }
    }

    class IosAlertPayload {
        private String title;
        private String body;

        public IosAlertPayload(String title, String body) {
            this.title = title;
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"title\":\"" + title + "\"," +
                    "\"body\":\"" + body +  "\"" +
                    '}';
        }
    }
}
