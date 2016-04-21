package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class BankWithdrawInvalidConfirmException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.BANKWITHDRAWALS_INVALID_CONFIRM;


    public BankWithdrawInvalidConfirmException() {
    }

    public BankWithdrawInvalidConfirmException(String message) {
        super(message);
    }

    public BankWithdrawInvalidConfirmException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankWithdrawInvalidConfirmException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
