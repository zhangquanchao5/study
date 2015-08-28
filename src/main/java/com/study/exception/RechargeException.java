package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/8/28.
 */
public class RechargeException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.RECHARGE_ERROR;

    public RechargeException() {
        super();
    }

    public RechargeException(String message) {
        super(message);
    }

    public RechargeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RechargeException(Throwable cause) {
        super(cause);
    }

    protected RechargeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getCode() {
        return code;
    }
}
