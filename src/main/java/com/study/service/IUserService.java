package com.study.service;

import com.study.common.EmailException;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;

/**
 * Created by huichao on 2015/7/9.
 */
public interface IUserService {

    UserInfo findByUserName(String userName);

    UserInfo findByEMail(String email);

    UserInfo findByIdcard(String card);

    UserInfo findByMobile(String mobile);

    UserInfo findByMobile(String mobile,String domain);

    UserInfo fingById(Integer userId);



    void saveUserInfo(UserInfo userInfo,UserInfoFrom from);

    void updateUserInfo(UserInfo userInfo);

    public void updateUserTime(Integer userId);




}
