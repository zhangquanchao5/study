package com.study.code;

/**
 * Created by huichao on 2015/7/8.
 */
public class ErrorCode {

    public static final String ERROR="000";
    public static final String SUCCESS="001";
    public static final String SYS_ERROR="999";

    /**
     * 存在此账号
     */
    public static final String USER_MOBILE_EXITS="1";
    /**
     * 不存在此账号
     */
    public static final String USER_NOT_EXITS="1";
    /**
     * 用户密码错误
     */
    public static final String USER_PWD_ERROR="2";
    /**
     * token no validate
     */
    public static final String USER_TOKEN_NO_VAL="9";
}
