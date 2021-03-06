package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class BankWithDrawalsMoreException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.BANKWITHDRAWALS_MORE_NUMS;


    public BankWithDrawalsMoreException() {
    }

    public BankWithDrawalsMoreException(String message) {
        super(message);
    }

    public BankWithDrawalsMoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankWithDrawalsMoreException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
