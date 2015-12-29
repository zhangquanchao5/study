package com.study.common.apibean.request;

/**
 * Created by huichao on 2015/12/24.
 */
public class OrgRechargeReq {

    private String orgId;
    private String money;
    private String accountBIllType;
    private String authKey;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public String getAccountBIllType() {
        return accountBIllType;
    }

    public void setAccountBIllType(String accountBIllType) {
        this.accountBIllType = accountBIllType;
    }


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
