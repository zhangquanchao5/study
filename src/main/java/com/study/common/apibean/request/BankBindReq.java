package com.study.common.apibean.request;

/**
 * Created by Star on 2016/4/20.
 */
public class BankBindReq {
    private Integer type;
    private String bankNO;
    private String accountName;
    private String depositBank;
    private String depositBankAddress;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBankNO() {
        return bankNO;
    }

    public void setBankNO(String bankNO) {
        this.bankNO = bankNO;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public String getDepositBankAddress() {
        return depositBankAddress;
    }

    public void setDepositBankAddress(String depositBankAddress) {
        this.depositBankAddress = depositBankAddress;
    }
}
