package com.study.service.impl;

import com.study.dao.UserInfoMapper;
import com.study.model.UserInfo;
import com.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huichao on 2015/7/9.
 */
@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo findByUserName(String userName) {
        return userInfoMapper.findByUserName(userName);
    }

    @Override
    public UserInfo fingById(Integer userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    public UserInfo findByMobile(String mobile){
        return userInfoMapper.selectByMobile(mobile);
    }

    public void saveUserInfo(UserInfo userInfo){
        userInfoMapper.insert(userInfo);
    }

    public void updateUserInfo(UserInfo userInfo){
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public UserInfo findByEMail(String email){
       return  userInfoMapper.findByEMail(email);
    }


}
