package com.zzwl.ias.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import com.zzwl.ias.security.UserCredentialsMatcher;
import com.zzwl.ias.session.UserSessionFactory;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.crypto.hash.format.DefaultHashFormatFactory;
import org.apache.shiro.crypto.hash.format.HashFormat;
import org.apache.shiro.crypto.hash.format.HashFormatFactory;
import org.apache.shiro.crypto.hash.format.Shiro1CryptFormat;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HuXin on 2017/12/8.
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public UserShiroRealm userShiroRealm() {
        //app用户realm
        return new UserShiroRealm();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager);
        return filterFactoryBean;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager(Realm realm, SessionManager sessionManager, RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm);
        securityManager.setSessionManager(new StatelessSessionManager());
        securityManager.setRememberMeManager(rememberMeManager);
        List<Realm> realms = new ArrayList<>();
        realms.add(userShiroRealm());
        securityManager.setRealms(realms);
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager(SessionFactory sessionFactory) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdCookie(cookie());
        sessionManager.setSessionFactory(sessionFactory);
        return sessionManager;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return new UserSessionFactory();
    }

    @Bean
    public Cookie cookie() {
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("sid");
        cookie.setMaxAge(180000);
        return cookie;
    }

    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        return rememberMeManager;
    }

    @Bean
    public Realm realm(PasswordMatcher passwordMatcher) {
        JdbcRealm jdbcRealm = new JdbcRealm();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ias_v2_1");
        dataSource.setUsername("ias_admin");
        dataSource.setPassword("Admin@ias_180701");
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setSaltStyle(JdbcRealm.SaltStyle.COLUMN);
        jdbcRealm.setCredentialsMatcher(passwordMatcher);
        return jdbcRealm;
    }

    @Bean
    public PasswordMatcher passwordMatcher(PasswordService passwordService) {
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        passwordMatcher.setPasswordService(passwordService);
        return passwordMatcher;
    }

    public CredentialsMatcher credentialsMatcher() {
        return new UserCredentialsMatcher();
    }


    @Bean
    public PasswordService passwordService(HashService hashService) {
        DefaultPasswordService passwordService = new DefaultPasswordService();
        passwordService.setHashService(hashService);
        HashFormat hashFormat = new Shiro1CryptFormat();
        passwordService.setHashFormat(hashFormat);
        HashFormatFactory hashFormatFactory = new DefaultHashFormatFactory();
        passwordService.setHashFormatFactory(hashFormatFactory);
        return passwordService;
    }

    @Bean
    public HashService hashService() {
        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName("MD5");
        return hashService;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

}
