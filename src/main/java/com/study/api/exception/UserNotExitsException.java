package com.study.api.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class UserNotExitsException extends Exception{

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.USER_NOT_EXITS;


    public UserNotExitsException() {
    }

    public UserNotExitsException(String message) {
        super(message);
    }

    public UserNotExitsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExitsException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
