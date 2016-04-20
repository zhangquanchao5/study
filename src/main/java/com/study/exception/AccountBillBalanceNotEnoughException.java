package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class AccountBillBalanceNotEnoughException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.BANKWITHDRAWALS_ACCOUTBILL_BALANCENOTENOUGH;


    public AccountBillBalanceNotEnoughException() {
    }

    public AccountBillBalanceNotEnoughException(String message) {
        super(message);
    }

    public AccountBillBalanceNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountBillBalanceNotEnoughException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
