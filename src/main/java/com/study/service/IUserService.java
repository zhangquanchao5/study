package com.study.service;

import com.study.common.EmailException;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;

/**
 * Created by huichao on 2015/7/9.
 */
public interface IUserService {

//    UserInfo findByUserName(String userName);
//
//    UserInfo findByUserName(String userName,String mobile);
//
//    UserInfo findByEMail(String email);
//
//    UserInfo findByIdcard(String card);
//
//    UserInfo findByMobile(String mobile);
//
//    UserInfo findByMobile(String mobile,String domain);

    UserInfo fingById(Integer userId);



    void saveUserInfo(UserInfo userInfo,UserInfoFrom from);

    void updateUserInfo(UserInfo userInfo);

     void updateUserTime(Integer userId);

    UserInfo findLoad(String login,String domain);



}
