package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class BankDuplicateBindingException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.BANK_DUPLICATE_BINDING;


    public BankDuplicateBindingException() {
    }

    public BankDuplicateBindingException(String message) {
        super(message);
    }

    public BankDuplicateBindingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankDuplicateBindingException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
