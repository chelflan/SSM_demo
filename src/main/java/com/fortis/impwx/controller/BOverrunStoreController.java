package com.fortis.impwx.controller;

import com.fortis.impwx.model.BOverrunStore;
import com.fortis.impwx.service.BOverrunStoreService;
import com.fortis.impwx.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author zq
 * @description controller
 * @date 2018/12/25
 */
@Controller
@RequestMapping("/api/bos")
public class BOverrunStoreController {
    private static final Logger LOGGER = Logger.getLogger(BOverrunStoreController.class);

   @Autowired
    private BOverrunStoreService bOverrunStoreService;

    @RequestMapping(value = "/getList",method = RequestMethod.POST)
    @ResponseBody
    public Result getBOverrunStoreList(HttpSession session,@RequestBody Map<String, Object> param,
                                       HttpServletRequest request,
                                       HttpServletResponse response){
        Result result = new Result();
        //登录验证逻辑
        boolean isLogin = ShiroUtils.isLogin(request, response);
        if(!isLogin) {
            result.setResultData(ResultCode.USER_NOT_LOGGED_IN, null);
            return result;
        }
        Integer currentPage = (Integer) param.get("currentPage");
        Integer pageSize = (Integer) param.get("pageSize");
        String mac = (String)param.get("mac");
        String storeName = (String)param.get("storeName");

        BOverrunStore bOverrunStore = new BOverrunStore();
        bOverrunStore.setMac(mac);
        bOverrunStore.setStoreName(storeName);
        int start = (currentPage-1 >0 ? currentPage-1 : 0)*pageSize;
        bOverrunStore.setStart(start);
        bOverrunStore.setLength(pageSize);
        List<BOverrunStore> bOverrunStoreList = null;
        try{
            bOverrunStoreList = bOverrunStoreService.getBosModelList(bOverrunStore);
            Long total = bOverrunStoreService.getBosModelTotal(bOverrunStore);
            PageUtils pageUtils = new PageUtils(bOverrunStoreList,total,pageSize,currentPage);
            result.setResultData(ResultCode.SUCCESS,pageUtils);
        }catch (Exception e){
            result.setResultCode(ResultCode.FAIL);
            LOGGER.error("getBOverrunStoreList请求错误:"+ Log4jUtils.getTrace(e));
        }
        return result;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteByMac(HttpSession session,@RequestBody Map<String, Object> param,
                                       HttpServletRequest request,
                                       HttpServletResponse response){
        Result result = new Result();
        //登录验证逻辑
        boolean isLogin = ShiroUtils.isLogin(request, response);
        if(!isLogin) {
            result.setResultData(ResultCode.USER_NOT_LOGGED_IN, null);
            return result;
        }

        String mac = (String)param.get("mac");

        BOverrunStore bOverrunStore = new BOverrunStore();
        bOverrunStore.setMac(mac);
        int flag = 0;
        try{
            flag = bOverrunStoreService.deleteByMac(bOverrunStore);
            if(flag > 0){
                result.setResultCode(ResultCode.SUCCESS);
            }else{
                result.setResultCode(ResultCode.FAIL_DELETE);
            }
        }catch (Exception e){
            result.setResultCode(ResultCode.FAIL);
            LOGGER.error("deleteByMac请求错误:"+ Log4jUtils.getTrace(e));
        }
        return result;
    }

    @RequestMapping(value = "/queryForUpdate",method = RequestMethod.POST)
    @ResponseBody
    public Result queryForUpdate(HttpSession session,@RequestBody Map<String, Object> param,
                                       HttpServletRequest request,
                                       HttpServletResponse response){
        Result result = new Result();
        //登录验证逻辑
        boolean isLogin = ShiroUtils.isLogin(request, response);
        if(!isLogin) {
            result.setResultData(ResultCode.USER_NOT_LOGGED_IN, null);
            return result;
        }

        String mac = (String)param.get("mac");

        BOverrunStore bOverrunStore = new BOverrunStore();
        bOverrunStore.setMac(mac);
        BOverrunStore bOverrunStoreModel = null;
        try{
            bOverrunStoreModel = bOverrunStoreService.queryForUpdate(bOverrunStore);
            result.setResultData(ResultCode.SUCCESS,bOverrunStoreModel);
        }catch (Exception e){
            result.setResultCode(ResultCode.FAIL);
            LOGGER.error("queryForUpdate请求错误:"+ Log4jUtils.getTrace(e));
        }
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Result update(HttpSession session,@RequestBody Map<String, Object> param,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        Result result = new Result();
        //登录验证逻辑
        boolean isLogin = ShiroUtils.isLogin(request, response);
        if(!isLogin) {
            result.setResultData(ResultCode.USER_NOT_LOGGED_IN, null);
            return result;
        }

        String mac = (String)param.get("mac");
        String storeName = (String)param.get("storeName");
        String storeAddr = (String)param.get("storeAddr");
        String limitTime = (String)param.get("limitTime");
        Boolean isScan = Global.SUCC.equals((String)param.get("isScan")) ? true:false;
        Boolean isVip = Global.SUCC.equals((String)param.get("isVip")) ? true:false;
        Boolean isException = Global.SUCC.equals((String)param.get("isException")) ? true:false;
        Boolean useChainDoctor = Global.SUCC.equals((String)param.get("useChainDoctor")) ? true:false;


        BOverrunStore bOverrunStore = new BOverrunStore();
        bOverrunStore.setMac(mac);
        bOverrunStore.setStoreName(storeName);
        bOverrunStore.setStoreAddr(storeAddr);
        bOverrunStore.setLimitTime(limitTime);
        bOverrunStore.setIsScan(isScan);
        bOverrunStore.setIsVip(isVip);
        bOverrunStore.setIsException(isException);
        bOverrunStore.setUseChainDoctor(useChainDoctor);
        int flag = 0;
        try{
            flag = bOverrunStoreService.update(bOverrunStore);
            if(flag > 0){
                result.setResultCode(ResultCode.SUCCESS);
            }else{
                result.setResultCode(ResultCode.FAIL);
            }
        }catch (Exception e){
            result.setResultCode(ResultCode.FAIL);
            LOGGER.error("queryForUpdate请求错误:"+ Log4jUtils.getTrace(e));
        }
        return result;
    }





}
