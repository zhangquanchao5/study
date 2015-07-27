package com.study.exception;

import com.study.code.ErrorCode;

/**
 * Created by Star on 2015/7/14.
 */
public class ProcessFailureException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private final String code = ErrorCode.PROCESS_FAIL;

    public ProcessFailureException() {
    }

    public ProcessFailureException(String message) {
        super(message);
    }

    public ProcessFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessFailureException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }
}
