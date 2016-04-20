package com.study.common.apibean.request;

/**
 * Created by Star on 2016/4/20.
 */
public class PreWithdrawReq {
    private Integer type;
    private Integer id;
    private Integer amount;
    private String cashType;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCashType() {
        return cashType;
    }

    public void setCashType(String cashType) {
        this.cashType = cashType;
    }
}
