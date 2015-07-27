package com.study.service;

import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;

/**
 * Created by huichao on 2015/7/9.
 */
public interface IUserService {

    UserInfo findByUserName(String userName);

    UserInfo findByEMail(String email);

    UserInfo fingById(Integer userId);

    public UserInfo findByMobile(String mobile);

    void saveUserInfo(UserInfo userInfo,UserInfoFrom from);

    void updateUserInfo(UserInfo userInfo);




}
