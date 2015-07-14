package com.study.common.apibean;

/**
 * Created by huichao on 2015/7/13.
 */
public class MobileBean {
    private String verifyCode;
    private String code;
    private String message;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
