package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/8/28.
 */
public class RepeatDepositException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.REPEAT_DEPOSIT_ERROR;

    /**
     * Instantiates a new Repeat deposit exception.
     */
    public RepeatDepositException() {
        super();
    }

    /**
     * Instantiates a new Repeat deposit exception.
     *
     * @param message the message
     */
    public RepeatDepositException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Repeat deposit exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public RepeatDepositException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Repeat deposit exception.
     *
     * @param cause the cause
     */
    public RepeatDepositException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Repeat deposit exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected RepeatDepositException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }
}
