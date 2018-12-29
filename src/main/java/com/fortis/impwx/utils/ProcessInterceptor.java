package com.fortis.impwx.utils;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger(ProcessInterceptor.class);

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

//        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //配置支持跨域 服务器server端要配置
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));

        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                "Content-Type," +
                "Content-Length," +
                "Authorization," +
                "Accept,X-Requested-With," +
                "Cache-Control,");

        httpServletResponse.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");

        httpServletResponse.setHeader("X-Powered-By","Jetty");

        //配置支持跨域
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");


        String method= httpServletRequest.getMethod();

        if (method.equals("OPTIONS")){
            httpServletResponse.setStatus(200);
            return false;
        }

        return true;
    }


    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }


    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
