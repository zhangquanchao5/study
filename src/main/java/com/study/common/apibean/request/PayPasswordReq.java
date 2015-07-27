package com.study.common.apibean.request;

/**
 * Created by Star on 2015/7/20.
 */
public class PayPasswordReq {
    private String oldPasswd;
    private String newPasswd;

    public PayPasswordReq() {
    }

    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        this.oldPasswd = oldPasswd;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        this.newPasswd = newPasswd;
    }
}
