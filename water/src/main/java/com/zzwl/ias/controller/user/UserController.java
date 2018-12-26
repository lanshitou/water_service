package com.zzwl.ias.controller.user;

import com.zzwl.ias.common.CurrentUserUtil;
import com.zzwl.ias.common.ErrorCode;
import com.zzwl.ias.common.Result;
import com.zzwl.ias.domain.VerificationRecordType;
import com.zzwl.ias.service.UserService;
import com.zzwl.ias.shiro.StatelessSessionManager;
import com.zzwl.ias.vo.TokenVo;
import com.zzwl.ias.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by HuXin on 2017/12/8.
 */
@RestController
@CrossOrigin
@RequestMapping("/v2/user")
public class UserController {

    @Autowired
    private UserService userService;

    //登陆
    @RequestMapping(path = "/oauth", method = RequestMethod.GET)
    public Object login(HttpServletRequest request, @RequestParam String mobile, @RequestParam String password) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return Result.error(ErrorCode.USER_ALREADY_LOGIN);
        }

        UsernamePasswordToken token = new UsernamePasswordToken(mobile, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            return Result.error(ErrorCode.USER_AUTHC_FAIL);
        }
        String tokenFromServer = request.getAttribute(StatelessSessionManager.TOKEN_FROM_SERVER).toString();
        TokenVo tokenVo = new TokenVo();
        tokenVo.setUid(CurrentUserUtil.getCurrentUserId());
        tokenVo.setToken(tokenFromServer);

        StatelessSessionManager.OauthToken = tokenFromServer;
        return Result.ok(tokenVo);
    }

    //登出
    @RequestMapping(path = "/oauth", method = RequestMethod.DELETE)
    @RequiresAuthentication
    public Object logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null) {
            return Result.error(ErrorCode.USER_NOT_LOGIN);
        } else {
            subject.logout();
            return Result.ok();
        }
    }

    //注册
    @RequestMapping(path = "/oauth", method = RequestMethod.PUT)
    public Object register(@RequestParam String mobile,
                           @RequestParam String password,
                           @RequestParam String vcode,
                           @RequestParam String nickName,
                           HttpServletRequest request) {
        ErrorCode errorCode = userService.register(mobile, password, vcode, nickName);
        if (errorCode == ErrorCode.OK) {
            return login(request, mobile, password);
        }

        return Result.error(errorCode);
    }

    //修改密码
    @RequiresAuthentication
    @RequestMapping(path = "/update_pwd", method = RequestMethod.PATCH)
    public Object changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        ErrorCode errorCode = userService.changePassword(oldPassword, newPassword);
        if (errorCode == ErrorCode.OK) {
            logout();
        }
        return Result.error(errorCode);
    }

    //忘记密码
    @RequestMapping(path = "/forget_pwd", method = RequestMethod.PATCH)
    public Object forgetPassword(@RequestParam String mobile, @RequestParam String newPassword, @RequestParam String vcode) {
        return Result.error(userService.changePassword(mobile, newPassword, vcode));
    }

    //修改手机号
    @RequiresAuthentication
    @RequestMapping(path = "/mobile", method = RequestMethod.PATCH)
    public Object changeMobile(@RequestParam String newMobile, @RequestParam String vcode) {
        Integer currentId = CurrentUserUtil.getCurrentUserId();
        return Result.error(userService.changeMobile(currentId, newMobile, vcode));
    }

    //校验旧手机号
    @RequiresAuthentication
    @RequestMapping(path = "/check", method = RequestMethod.POST)
    public Object checkOldMobile(@RequestParam String oldMobile, @RequestParam String vcode) {
        return Result.error(userService.checkMobile(oldMobile, vcode, VerificationRecordType.VCODE_TYPE_CHANGE_PASS));
    }

    //获取验证码
    @RequestMapping(path = "/vcode", method = RequestMethod.GET)
    public Object getVerificationCode(@RequestParam String mobile, @RequestParam int type) {
        if (type == VerificationRecordType.VCODE_TYPE_REGISTER) {
            return Result.error(userService.sendRegisterVerificationCode(mobile));
        } else if (type == VerificationRecordType.VCODE_TYPE_CHANGE_PASS) {
            return Result.error(userService.sendChangePassCode(mobile));
        } else {
            return Result.error(ErrorCode.INVALID_PARAMS);
        }
    }

    //获取个人信息
    @RequiresAuthentication
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    public Object getUserInfo() {
        Integer currentId = CurrentUserUtil.getCurrentUserId();
        return Result.error(userService.findUserByUserId(currentId));
    }

    //更新个人信息
    @RequiresAuthentication
    @RequestMapping(path = "/info", method = RequestMethod.PATCH)
    public Object updateUserInfo(@RequestBody UserVo userVo) {
        Integer currentId = CurrentUserUtil.getCurrentUserId();
        return Result.error(userService.updateUserInfo(currentId, userVo));
    }

    //用户服务协议
    @RequestMapping(path = "/agreement", method = RequestMethod.GET)
    public Object getAgreement() {
        return Result.error(userService.getAgreement());
    }

}
