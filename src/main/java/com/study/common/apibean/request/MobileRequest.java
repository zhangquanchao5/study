package com.study.common.apibean.request;

/**
 * Created by huichao on 2015/7/13.
 */
public class MobileRequest {
    private String userPhone;
    private Integer type;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
