package com.study.common.session;

import com.study.model.UserInfo;

/**
 * Created by huichao on 2015/7/15.
 */
public class SessionInfo {

    private UserInfo userInfo;

    public SessionInfo(UserInfo userInfo){
        this.userInfo=userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
