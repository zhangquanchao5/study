package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class UnknowAccountBillTypeException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.UNKNOW_BILL_TYPE;


    public UnknowAccountBillTypeException() {
    }

    public UnknowAccountBillTypeException(String message) {
        super(message);
    }

    public UnknowAccountBillTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknowAccountBillTypeException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
