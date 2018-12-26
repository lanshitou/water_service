package com.zzwl.ias.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

/**
 * Created by HuXin on 2017/12/12.
 */
public class UserSessionFactory implements SessionFactory {

    @Override
    public Session createSession(SessionContext initData) {
        return new UserSession();
    }
}
