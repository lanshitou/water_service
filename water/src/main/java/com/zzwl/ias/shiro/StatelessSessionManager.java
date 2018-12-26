package com.zzwl.ias.shiro;

import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import java.io.Serializable;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StatelessSessionManager extends DefaultWebSessionManager {
    /**
     * 这个是服务端要返回给客户端，
     */
    public final static String TOKEN_FROM_SERVER = "TOKEN";
    /**
     * 这个是客户端请求给服务端带的header
     */
    public final static String TOKEN_FROM_APP = "AppToken";

    public static String OauthToken;

    @Override
    public Serializable getSessionId(SessionKey key) {
        Serializable sessionId = key.getSessionId();
        if (sessionId == null) {
            HttpServletRequest request = WebUtils.getHttpRequest(key);
            HttpServletResponse response = WebUtils.getHttpResponse(key);
            sessionId = this.getSessionId(request, response);
        }
        HttpServletRequest request = WebUtils.getHttpRequest(key);
        request.setAttribute(TOKEN_FROM_SERVER, sessionId.toString());
        return sessionId;
    }

    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(TOKEN_FROM_APP);
        if (token == null) {
            token = UUID.randomUUID().toString();
        }

        if (token.equals("generalToken") && OauthToken != null) token = OauthToken;
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,
                ShiroHttpServletRequest.COOKIE_SESSION_ID_SOURCE);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
        request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
        request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());
        return token;
    }

}
