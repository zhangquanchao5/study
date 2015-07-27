package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class BalanceNotEnoughException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.USER_MONEY_ERROR;

    public BalanceNotEnoughException() {
    }

    public BalanceNotEnoughException(String message) {
        super(message);
    }

    public BalanceNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public BalanceNotEnoughException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
