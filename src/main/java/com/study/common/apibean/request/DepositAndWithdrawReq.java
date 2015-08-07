package com.study.common.apibean.request;

/**
 * Created by Star on 2015/7/9.
 */
public class DepositAndWithdrawReq {

    private String accountBIllType;
    private Integer amount;
    private String tradeNO;
    private String accountbook;

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

    public String getTradeNO() {
        return tradeNO;
    }

    public void setTradeNO(String tradeNO) {
        this.tradeNO = tradeNO;
    }

    public String getAccountbook() {
        return accountbook;
    }

    public void setAccountbook(String accountbook) {
        this.accountbook = accountbook;
    }
}
