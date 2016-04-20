package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class AccountNotExitsException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.USER_ACCOUNT_NOT_EXITS;


    public AccountNotExitsException() {
    }

    public AccountNotExitsException(String message) {
        super(message);
    }

    public AccountNotExitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotExitsException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
