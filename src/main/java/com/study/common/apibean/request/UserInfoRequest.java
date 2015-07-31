package com.study.common.apibean.request;

/**
 * Created by huichao on 2015/7/14.
 */
public class UserInfoRequest {
    private Integer id;
    private String auth_token ;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
