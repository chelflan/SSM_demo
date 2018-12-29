package com.fortis.impwx.controller;

import com.fortis.impwx.model.BSysUser;
import com.fortis.impwx.service.BSysUserService;
import com.fortis.impwx.utils.*;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * 功能描述
 *
 * @author zq
 * @description 用户登录
 * @date 2018/12/27
 */
@Controller
@RequestMapping("/api/user")
public class BSysUserController {
    private static final Logger LOGGER = Logger.getLogger(BSysUserController.class);

    @Autowired
    private BSysUserService bSysUserService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpSession session, @RequestBody Map<String, Object> param){

       Result result = new Result();
       String account = (String)param.get("userAcct");
       String passwd = (String)param.get("pwd");
        BSysUser bSysUser = new BSysUser();
        bSysUser.setUserAcct(account);
        bSysUser.setPasswd(MD5Util.md5(passwd));

        try{

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(bSysUser.getUserAcct(), bSysUser.getPasswd());
        subject.login(token);

        if(subject.isAuthenticated()) {
            bSysUser = bSysUserService.findByUserAcct(account);
            if(MD5Util.md5(passwd).equals(bSysUser.getPasswd())){
                result.setResultData(ResultCode.SUCCESS,bSysUser);
                result.setImpWxid(subject.getSession().getId().toString());
                return result;
            }else {
                result.setResultCode(ResultCode.INCORRECT_ACCOUNT);
                return result;
            }
        }else {
            result.setResultCode(ResultCode.USER_LOGIN_ERROR);
            return result;
        }

       }catch (AuthenticationException e) {
            if(e instanceof UnknownAccountException) {
                //账号不存在
            } else if (e instanceof LockedAccountException){
                result.setResultCode(ResultCode.USER_ACCOUNT_FORBIDDEN);
                return result;
            } else {
                if(e.getCause() instanceof MyBatisSystemException) {
                    if(e.getCause().getCause().getCause() instanceof CannotGetJdbcConnectionException) {
                        result.setResultCode(ResultCode.INTERFACE_REQUEST_TIMEOUT);
                        return result;
                    }
                }
                result.setResultCode(ResultCode.USER_LOGIN_ERROR);
                return result;
            }
        }
        result.setResultCode(ResultCode.USER_LOGIN_ERROR);
       return result;
    }

    @RequestMapping( value = "/userInfo", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Result userInfo() throws IOException {

        Result result = new Result();
        try {
            if(ShiroUtils.getSubject().isAuthenticated()) {
                BSysUser user = (BSysUser) ShiroUtils.getSubject().getPrincipal();
                if(user != null) {
                    user.setPasswd(null);
                }
                result.setResultData(ResultCode.SUCCESS, user);
            } else {
                result.setResultData(ResultCode.USER_NOT_LOGGED_IN, null);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultData(ResultCode.FAIL, "");
            return result;
        }
        return result;
    }

    @RequestMapping( value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject subject = SecurityUtils.getSubject();
        if(subject != null) {
            subject.logout();
            ShiroUtils.sessionInvalidate(request, response);
        }
    }

}
