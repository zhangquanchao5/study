package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class BankWithDrawalsNotExitsException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.BANKWITHDRAWALS_NOT_EXITS;


    public BankWithDrawalsNotExitsException() {
    }

    public BankWithDrawalsNotExitsException(String message) {
        super(message);
    }

    public BankWithDrawalsNotExitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankWithDrawalsNotExitsException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
