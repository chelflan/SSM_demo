package com.fortis.impwx.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述
 *
 * @author zq
 * @description 状态码
 * @date 2018/12/26
 */
public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(0, "成功"),

    INTERFACE_SEARCH_FAIL(999, "接口查询出错"),
    FAIL(-1,"系统错误"),
    INCORRECT_ACCOUNT(-2,"请输入正确的用户名和密码！"),
    FAIL_DELETE(-3,"删除失败!"),

    /*自定义状态码*/
    STATUS_NOT_ALLOWED(80001,"药品审核通过"),
    UPDATE_FAIL(80002,"更新失败"),
    OLD_PASS_ERR(80003,"原密码错误"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),
    PARAM_TIME_ERROR(10005, "时间格式错误"),
    PARAM_STARTTIME_ERROR(10006, "起始时间不能大于结束时间"),
    PARAM_RANAGE7_ERROR(10007, "请控制时间范围在7天以内"),
    PARAM_RANAGE31_ERROR(10008, "请控制时间范围在一个月以内"),
    PARAM_RANAGE93_ERROR(10009, "请控制时间范围在三个月以内"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),

    /* 业务错误：30001-39999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "某业务出现问题"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

    /* 数据错误：50001-599999 */
    OTHER_USER_NO_MENU_CONFIG(40002, "其他用户无配置目录"),
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),
    DATA_VALIDATE_FAIL(50004, "参数校验失败"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    public String toString() {
        return this.name();
    }

    //校验重复的code值
    public static void main(String[] args) {
        ResultCode[] ApiResultCodes = ResultCode.values();
        List<Integer> codeList = new ArrayList<Integer>();
        for (ResultCode ApiResultCode : ApiResultCodes) {
            if (codeList.contains(ApiResultCode.code)) {
                System.out.println(ApiResultCode.code);
            } else {
                codeList.add(ApiResultCode.code());
            }
        }
    }

}
