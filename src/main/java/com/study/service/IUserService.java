package com.study.service;

import com.study.model.UserInfo;

/**
 * Created by huichao on 2015/7/9.
 */
public interface IUserService {

    UserInfo findByUserName(String userName);

    UserInfo fingById(Integer userId);
}
