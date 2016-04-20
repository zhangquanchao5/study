package com.study.common.apibean.request;

import com.study.common.page.BasePageRequest;

/**
 * Created by huichao on 2016/4/20.
 */
public class BankWithdrawReq extends BasePageRequest {

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
