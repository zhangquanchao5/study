package com.study.exception;

/**
 * Created by zqc on 2016/8/15.
 */
public class BusinessException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    private String code;

    public BusinessException(String code){
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
