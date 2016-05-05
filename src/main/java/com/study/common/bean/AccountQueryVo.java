package com.study.common.bean;

/**
 * Created by huichao on 2016/4/20.
 */
public class AccountQueryVo {
    private Long amount=0l;
    private Long outAmount=0l;
    private Long inAmount=0l;
    private Long withdramAmount=0l;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(Long outAmount) {
        this.outAmount = outAmount;
    }

    public Long getInAmount() {
        return inAmount;
    }

    public void setInAmount(Long inAmount) {
        this.inAmount = inAmount;
    }

    public Long getWithdramAmount() {
        return withdramAmount;
    }

    public void setWithdramAmount(Long withdramAmount) {
        this.withdramAmount = withdramAmount;
    }
}
