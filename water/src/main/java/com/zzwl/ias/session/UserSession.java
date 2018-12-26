package com.zzwl.ias.session;

import org.apache.shiro.session.mgt.SimpleSession;

/**
 * Created by HuXin on 2017/12/12.
 */
public class UserSession extends SimpleSession {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
