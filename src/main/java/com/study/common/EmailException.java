package com.study.common;

/**
 * Created by Star on 2015/4/21.
 */
public class EmailException extends Exception {
    /**
     * Instantiates a new Email exception.
     */
    public EmailException() {
    }

    /**
     * Instantiates a new Email exception.
     *
     * @param message the message
     */
    public EmailException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Email exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Email exception.
     *
     * @param cause the cause
     */
    public EmailException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Email exception.
     *
     * @param message the message
     * @param cause the cause
     * @param enableSuppression the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public EmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
