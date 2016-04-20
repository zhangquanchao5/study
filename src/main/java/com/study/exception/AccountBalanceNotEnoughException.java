package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class AccountBalanceNotEnoughException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.BANKWITHDRAWALS_ACCOUT_BALANCENOTENOUGH;


    public AccountBalanceNotEnoughException() {
    }

    public AccountBalanceNotEnoughException(String message) {
        super(message);
    }

    public AccountBalanceNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountBalanceNotEnoughException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
