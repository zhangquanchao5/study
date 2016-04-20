package com.study.common.apibean.response;

import com.study.common.apibean.AccountDetailBean;

import java.util.List;

/**
 * Created by Star on 2015/7/14.
 */
public class AccountDetailResp {
    private Long amount;
    private Long cashAmount;
    private Long redAmount;
    private Long giftAmount;
    private Integer total;
    private List<AccountDetailBean> accountDetailBeans;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Long cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Long getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(Long redAmount) {
        this.redAmount = redAmount;
    }

    public Long getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(Long giftAmount) {
        this.giftAmount = giftAmount;
    }

    public List<AccountDetailBean> getAccountDetailBeans() {
        return accountDetailBeans;
    }

    public void setAccountDetailBeans(List<AccountDetailBean> accountDetailBeans) {
        this.accountDetailBeans = accountDetailBeans;
    }
}
