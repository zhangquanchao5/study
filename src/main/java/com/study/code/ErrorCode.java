package com.study.code;

/**
 * Created by huichao on 2015/7/8.
 */
public class ErrorCode {

    public static final String ERROR="1001";
    public static final String SUCCESS="200";
    public static final String SYS_ERROR="999";

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
    /**
     * 不存在此账号
     */
    public static final String USER_NOT_EXITS="2002";
    /**
     * 用户密码错误
     */
    public static final String USER_PWD_ERROR="2001";

    /**
     * 用户密码错误
     */
    public static final String USER_NO_AUTH_ERROR="2004";

    /**
     * 验证码错误
     */
    public static final String USER_CODE_ERROR="2005";

    /**
     * 余额不足
     */
    public static final String USER_MONEY_ERROR="3001";

    /**
     * 余额不足
     */
    public static final String USER_TRADE_ERROR="3002";

    /**
     * 重复充值
     */
    public static final String RECHARGE_ERROR="3003";

    /**
     * token no validate
     */
    public static final String USER_TOKEN_NO_VAL="9999";
}
