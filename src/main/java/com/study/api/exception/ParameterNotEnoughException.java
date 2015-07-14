package com.study.api.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class ParameterNotEnoughException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.PARAMETER_NOT_ENOUGH;

    public ParameterNotEnoughException() {
    }

    public ParameterNotEnoughException(String message) {
        super(message);
    }

    public ParameterNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterNotEnoughException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
