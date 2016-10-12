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
     * 未知账本类型
     */
    public static final String UNKNOW_BILL_TYPE = "1003";

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
     * 用户需要激活
     */
    public static final String USER_NEED_ACTIVE="2006";

    /**
     * 用户账户不存在
     */
    public static final String USER_ACCOUNT_NOT_EXITS="2007";

    /**
     * 用户账户账本不存在
     */
    public static final String USER_ACCOUNT_BILL_NOT_EXITS="2008";

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
    public static final String REPEAT_DEPOSIT_ERROR ="3003";

    /**
     * 重复扣款
     */
    public static final String REPEAT_WITHDRAW_ERROR="3004";

    /**
     * 红包充值验证码不正确
     */
    public static final String RED_RECHARGE_CODE_ERROR="3005";

    /**
     * 新用户发送短信密码失败
     */
    public static final String RED_RECHARGE_SEND_ERROR="3006";



    /**
     * 无效的绑定ID
     */
    public static final String BANK_NOT_EXITS = "4000";
    /**
     * 重复绑定
     */
    public static final String BANK_DUPLICATE_BINDING = "4001";
    /**
     * 银行提现3000以下不能超过2次
     */
    public static final String BANKWITHDRAWALS_MORE_NUMS = "4006";

    /**
     * 银行提现记录不存在
     */
    public static final String BANKWITHDRAWALS_NOT_EXITS = "4002";
    /**
     * 用户提现账户余额不足
     */
    public static final String BANKWITHDRAWALS_ACCOUT_BALANCENOTENOUGH = "4003";
    /**
     * 用户提现账本余额不足
     */
    public static final String BANKWITHDRAWALS_ACCOUTBILL_BALANCENOTENOUGH = "4004";
    /**
     * 无效的提现确认（重复）
     */
    public static final String BANKWITHDRAWALS_INVALID_CONFIRM = "4005";

    /**
     * token no validate
     */
    public static final String USER_TOKEN_NO_VAL="9999";

}
