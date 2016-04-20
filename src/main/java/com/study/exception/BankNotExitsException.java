package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class BankNotExitsException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.BANK_NOT_EXITS;


    public BankNotExitsException() {
    }

    public BankNotExitsException(String message) {
        super(message);
    }

    public BankNotExitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankNotExitsException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
