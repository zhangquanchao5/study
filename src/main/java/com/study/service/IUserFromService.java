package com.study.service;

import com.study.model.UserInfoFrom;

import java.util.Map;

/**
 * Created by huichao on 2015/7/13.
 */
public interface IUserFromService {

    void saveUserFrom(UserInfoFrom userInfoFrom);

    UserInfoFrom findByOpenIdAndFrom(String openId,String from);
}
