package com.study.common.apibean.request;

/**
 * Created by Star on 2016/4/20.
 */
public class WithdrawConfirmReq {
    private Integer withdrawNo;
    private Integer status;

    public Integer getWithdrawNo() {
        return withdrawNo;
    }

    public void setWithdrawNo(Integer withdrawNo) {
        this.withdrawNo = withdrawNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
