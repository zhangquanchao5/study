package com.study.common.apibean.response;

/**
 * Created by huichao on 2015/7/14.
 */
public class LoginResponse {
    private Object user;
    private String token;
    private Long invalidTime;

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Long invalidTime) {
        this.invalidTime = invalidTime;
    }
}
