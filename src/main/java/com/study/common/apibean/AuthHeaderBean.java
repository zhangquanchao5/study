package com.study.common.apibean;

/**
 * Created by Star on 2015/7/21.
 */
public class AuthHeaderBean {
    private Integer userId;

    public AuthHeaderBean() {
    }

    public AuthHeaderBean(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
