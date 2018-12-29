package com.fortis.impwx.utils;

import java.io.Serializable;

/**
 * 功能描述
 *
 * @author zq
 * @description 返回结果
 * @date 2018/12/26
 */
public class Result implements Serializable {


    private static final long serialVersionUID = -5842558377634782294L;

    private String impWxid;

    private Integer code;

    private String msg;

    private Object data;

    public Result() {}

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Result success() {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }


    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setResultCode(resultCode);
        return result;
    }

    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.msg = code.message();
    }

    public void setResultData(ResultCode code,Object data){
        this.code = code.code();
        this.msg = code.message();
        this.data = data;
    }

    private void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public String getImpWxid() {
        return impWxid;
    }

    public void setImpWxid(String impWxid) {
        this.impWxid = impWxid;
    }
}
