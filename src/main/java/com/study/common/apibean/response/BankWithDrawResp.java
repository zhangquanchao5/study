package com.study.common.apibean.response;

import java.util.Date;

/**
 * Created by huichao on 2016/4/20.
 */
public class BankWithDrawResp {
    private Integer withdrawNo;
    private String bankNo;
    private String depositBank;
    private Integer type;
    private String accountName;
    private Integer amount;
    private Integer status;
    private Date withdrawTime;
    private Date receiveTime;

    public Integer getWithdrawNo() {
        return withdrawNo;
    }

    public void setWithdrawNo(Integer withdrawNo) {
        this.withdrawNo = withdrawNo;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
