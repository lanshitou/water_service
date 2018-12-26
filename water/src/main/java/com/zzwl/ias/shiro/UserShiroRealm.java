package com.zzwl.ias.shiro;

import com.zzwl.ias.domain.User;
import com.zzwl.ias.mapper.UserMapper;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(UserShiroRealm.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    PasswordService passwordService;


    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String mobile = (String) token.getPrincipal(); // 得到登陆手机号
        String password = new String((char[]) token.getCredentials());
        String pwd = passwordService.encryptPassword(password);// 得到密码
        User user = userMapper.selectByMobile((String) token.getPrincipal());
        if (user == null) {
            throw new ShiroException();//账号异常
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            throw new UnknownAccountException(); //如果用户名错误
        }
        if (!user.getPassword().equals(pwd)) {
            throw new IncorrectCredentialsException(); //如果密码错误
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getId(), //用户id
                password, //密码
                getName()  //realm name
        );

        return authenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
