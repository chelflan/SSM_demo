package com.fortis.impwx.shiro;

import com.alibaba.fastjson.JSONObject;
//import com.fortis.csm.model.UserModel;
//import com.fortis.csm.utils.ShiroUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class JSessionIdInterceptorAdapter implements HandlerInterceptor {

    private static Logger logger = Logger.getLogger(JSessionIdInterceptorAdapter.class);

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        try {

            //系统处理的请求
            if(handler instanceof DefaultServletHttpRequestHandler) {
                return true;
            }

            if(handler instanceof ResourceHttpRequestHandler) {
                return true;
            }

            HttpServletRequest req = httpServletRequest;

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String controller = handlerMethod.getBeanType().getName();
            String method = handlerMethod.getMethod().getName();
            //登录请求不做拦截
            if("com.fortis.impwx.controller.IndexController".equals(controller) && "login".equals(method)) {
                return true;
            }
//            System.out.println("拦截的Controller：" + controller);
//            System.out.println("拦截的action:" + method);

//            String body = IOUtils.toString(req.getInputStream(), req.getCharacterEncoding());
//            JSONObject requestBody = JSONObject.parseObject(body);
//            if(requestBody != null) {
//                String jsid = httpServletRequest.getHeader("Authorization");
//                if(jsid != null) {
//
//                    System.out.println("jsessionId = "+ jsid);
//
//                    Session se = ShiroUtils.getSession(jsid, req, httpServletResponse);
//                    System.out.println("orugin:" + req.getHeader("Origin"));
//                    System.out.println("SESSION ID = " + se.getId());
//                    System.out.println("HOST：" + se.getHost());
//                    System.out.println("TIMEOUT ：" +se.getTimeout());
//                    System.out.println("START：" + se.getStartTimestamp());
//                    System.out.println("LAST：" + se.getLastAccessTime());
//
//                    UserModel user = ShiroUtils.readUserModel(jsid, httpServletRequest, httpServletResponse);
//
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理请求", e.getCause());
        }

        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
