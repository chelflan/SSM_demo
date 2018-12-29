package com.fortis.impwx.utils;

import com.fortis.impwx.model.BSysUser;
import com.fortis.impwx.shiro.MyHttpServletRequestWrapper;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class ShiroUtils {

    private static final Logger logger = Logger.getLogger(ShiroUtils.class);

    /**
     * 按指定 sessionId 获取session信息
     */
    public static ShiroHttpSession getSession(String sessionId,
                                              HttpServletRequest request,
                                              HttpServletResponse response) {

        ShiroHttpSession se = null;
        try{
            MyHttpServletRequestWrapper myHttpReq = (MyHttpServletRequestWrapper)request;
            ShiroHttpServletRequest shiroReq = (ShiroHttpServletRequest) myHttpReq.getRequest();
            se = (ShiroHttpSession) shiroReq.getSession();
            if(se.isNew()) {
                SessionKey key = new WebSessionKey(sessionId, request, response);
                se = new ShiroHttpSession(SecurityUtils.getSecurityManager().getSession(key), request, request.getServletContext());
            }
        } catch (Exception e){
            logger.debug("sessionId失效：", e.getCause());
            return null;
        }
        return se;
    }

    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {

        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();

        Cookie[] cookies = request.getCookies();

        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 获取登录用户的信息
     */
    public static BSysUser readUserModel(HttpServletRequest req, HttpServletResponse resp) {

        BSysUser um = null;
        try{
            String sid = req.getHeader(Global.TOKEN);
            ShiroHttpSession se = getSession(sid, req, resp);
            if(se != null) {
                um = (BSysUser) se.getAttribute(Global.CURRENT_USER);
            }
        } catch (Exception e){
            logger.debug("通过session获取用户信息readUserModel", e.getCause());
        }
        return um;
    }

    /**
     * session 设置过期
     */
    public static void sessionInvalidate(HttpServletRequest req, HttpServletResponse resp) {
        try{
            ShiroHttpSession se = getSession(req.getHeader(Global.TOKEN), req, resp);
            if(se != null) {
                se.removeAttribute(Global.CURRENT_USER);
                se.invalidate();
            }
        } catch (Exception e){
            logger.debug("session设置过期sessionInvalidate：", e.getCause());
        }
    }

    /**
     * 按指定sessionId获取用户信息
     * 无sessionId 默认读取 request中sessionId
     */
    public static BSysUser readUserModel(String sessionId, HttpServletRequest req, HttpServletResponse resp) {

        if(sessionId == null) {
            return readUserModel(req, resp);
        }

        BSysUser um = null;
        try{
            ShiroHttpSession se = getSession(sessionId, req, resp);
            um = readUserModelBySession(se);
        } catch (Exception e){
            logger.debug("按指定sessionId获取用户信息：readUserModel", e.getCause());
        }
        return um;
    }

    /**
     * 判断是否登录
     */
    public static boolean isLogin(HttpServletRequest request, HttpServletResponse response) {

        BSysUser um = readUserModel(request, response);
        if(um == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 登出调用
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        BSysUser um = null;
        try{
//            String sessionId = request.getHeader(Global.TOKEN);

//            if(!StringUtils.isBlank(sessionId)) {
//                SessionKey key = new WebSessionKey(sessionId, request, response);
//                Session se = SecurityUtils.getSecurityManager().getSession(key);
//                um = (UserModel) se.getAttribute(Global.CURRENT_USER);
//                if(um != null){
//                    se.removeAttribute(Global.CURRENT_USER);
//                }
            ShiroHttpSession shiroHttpSession = getSession(null, request, response);
            shiroHttpSession.removeAttribute(Global.CURRENT_USER);
            shiroHttpSession.invalidate();
            SecurityUtils.getSubject().logout();
//            }
        } catch (Exception e){
            logger.debug("清楚已登录用户信息失败：logout", e.getCause());
        }
    }

    private static BSysUser readUserModelBySession(ShiroHttpSession session) {
        BSysUser um = null;
        if(session != null) {
            um = (BSysUser) session.getAttribute(Global.CURRENT_USER);
        }
        return um;
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

}
