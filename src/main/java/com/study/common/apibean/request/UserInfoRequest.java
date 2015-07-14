package com.study.common.apibean.request;

/**
 * Created by huichao on 2015/7/14.
 */
public class UserInfoRequest {
    private Integer id;
    private String token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
