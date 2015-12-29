package com.study.common.apibean.request;

/**
 * Created by huichao on 2015/12/24.
 */
public class RechargeReq {

    private String mobile;
    private String money;
    private String accountBIllType;
    private String authKey;

    public String getAccountBIllType() {
        return accountBIllType;
    }

    public void setAccountBIllType(String accountBIllType) {
        this.accountBIllType = accountBIllType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
}
