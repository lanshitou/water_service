package com.zzwl.ias.service;

import java.util.List;

/**
 * Description:
 * User: HuXin
 * Date: 2018-08-11
 * Time: 15:19
 */
public interface MessagePushService {
    void push(String title, String content, Object extent, List<Integer> users);
}
