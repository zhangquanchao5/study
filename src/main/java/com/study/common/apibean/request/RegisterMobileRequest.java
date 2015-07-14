package com.study.common.apibean.request;

/**
 * Created by huichao on 2015/7/13.
 */
public class RegisterMobileRequest {
    private String userPhone;
    private String passwd;
    private String verifyCode;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
