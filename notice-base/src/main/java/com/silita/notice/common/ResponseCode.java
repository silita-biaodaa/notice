package com.silita.notice.common;

/**
 * Created by zhushuai on 2019/6/10.
 */
public class ResponseCode {

    /**
     * 成功
     */
    public static Integer SUCCESS_CODE = 1;
    public static String SUCCESS_MSG = "操作成功";

    /**
     * 错误code
     */
    public static Integer ERROR_CODE = 500;

    /**
     * 爬虫
     */
    public static Integer WARN_CODE_502 = 502;
    public static String WARN_MSG_502 = "您的账号疑似非法爬虫，现已冻结，如有疑问，请联系标大大客服(0731-85076077)";

    /**
     * 登录失效
     */
    public static Integer WARN_CODE_503 = 503;
    public static String WARN_MSG_503 = "登录信息已过期，请重新登录！";

    /**
     * 非法token
     */
    public static Integer WARN_CODE_504 = 504;
    public static String WARN_MSG_504 = "非法用户信息！";
}
