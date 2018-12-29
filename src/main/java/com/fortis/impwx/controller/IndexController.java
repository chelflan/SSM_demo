package com.fortis.impwx.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述
 *
 * @author zq
 * @description 首页跳转
 * @date 2018/12/26
 */
@Controller
public class IndexController {
    private static final Logger LOGGER = Logger.getLogger(IndexController.class);

    @RequestMapping(value="/")
    public String index(){
        return "index";
    }
}
