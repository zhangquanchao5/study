package com.study.service.impl;

import com.study.dao.UserInfoFromMapper;
import com.study.model.UserInfoFrom;
import com.study.service.IUserFromService;
import com.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huichao on 2015/7/13.
 */
@Service
public class UserFromServiceImpl implements IUserFromService{
    @Autowired
    private UserInfoFromMapper userInfoFromMapper;

    @Override
    public void saveUserFrom(UserInfoFrom userInfoFrom) {
        userInfoFromMapper.insert(userInfoFrom);
    }
}
