package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class AccountBillNotExitsException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.USER_ACCOUNT_BILL_NOT_EXITS;


    public AccountBillNotExitsException() {
    }

    public AccountBillNotExitsException(String message) {
        super(message);
    }

    public AccountBillNotExitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountBillNotExitsException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
