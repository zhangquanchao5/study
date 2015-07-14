package com.study.api.bean;

/**
 * Created by Star on 2015/7/9.
 */
public class DepositAndWithdrawReq {

    private Integer userId;
    private String accountBIllType;
    private Integer amount;
    private String token;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountBIllType() {
        return accountBIllType;
    }

    public void setAccountBIllType(String accountBIllType) {
        this.accountBIllType = accountBIllType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
