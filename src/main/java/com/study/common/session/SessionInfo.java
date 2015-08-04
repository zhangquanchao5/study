package com.study.common.session;

import com.study.common.DateUtil;
import com.study.model.UserInfo;

/**
 * Created by huichao on 2015/7/15.
 */
public class SessionInfo {

    private UserInfo userInfo;

    private String updateTime;

    public SessionInfo(UserInfo userInfo){
        this.userInfo=userInfo;
        this.updateTime= DateUtil.dateToString(userInfo.getUpdateTime(),DateUtil.DEFAULT_TIME_FORMAT);
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
