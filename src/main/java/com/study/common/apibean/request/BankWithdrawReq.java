package com.study.common.apibean.request;

import com.study.common.page.BasePageRequest;

/**
 * Created by huichao on 2016/4/20.
 */
public class BankWithdrawReq extends BasePageRequest {

    private Integer status;

    private Integer userId;

    private Integer start;



    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
