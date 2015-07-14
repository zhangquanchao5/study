package com.study.code;

/**
 * Created by huichao on 2015/7/8.
 */
public class ErrorCode {

    public static final String ERROR="000";
    public static final String SUCCESS="001";
    public static final String SYS_ERROR="999";

    public static final String SUCCESS_CN="处理成功";
    public static final String ERROR_CN="处理失败";
    public static final String SYS_ERROR_CN="系统未知错误";

    /**
     * 处理成功
     */
    public static final String PROCESS_SUCC="200";
    /**
     * 处理失败
     */
    public static final String PROCESS_FAIL="1001";
    /**
     * 参数不完整
     */
    public static final String PARAMETER_NOT_ENOUGH="1002";

    /**
     * 存在此账号
     */
    public static final String USER_EXITS="2003";
    public static final String USER_EXITS_CN="用户已存在";
    /**
     * 不存在此账号
     */
    public static final String USER_NOT_EXITS="2002";
    public static final String USER_NOT_EXITS_CN="用户不存在";
    /**
     * 用户密码错误
     */
    public static final String USER_PWD_ERROR="2001";
    public static final String USER_PWD_ERROR_CN="用户密码错误";

    /**
     * 用户密码错误
     */
    public static final String USER_NO_AUTH_ERROR="2004";
    public static final String UUSER_NO_AUTH_CN="用户无权限";

    /**
     * 验证码错误
     */
    public static final String USER_CODE_ERROR="2005";
    public static final String USER_CODE_ERROR_CN="验证码错误";

    /**
     * 余额不足
     */
    public static final String USER_MONEY_ERROR="3001";
    public static final String USER_MONEY_ERROR_CN="余额不足";

    /**
     * 余额不足
     */
    public static final String USER_TRADE_ERROR="3002";
    public static final String USER_TRADE_ERROR_CN="交易重复扣款";

    /**
     * token no validate
     */
    public static final String USER_TOKEN_NO_VAL="9999";
    public static final String USER_TOKEN_NO_VAL_CN="token无效";
}
