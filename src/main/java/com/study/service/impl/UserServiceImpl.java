package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.dao.UserInfoFromMapper;
import com.study.dao.UserInfoMapper;
import com.study.dao.UserSecurityMapper;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.model.UserSecurity;
import com.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by huichao on 2015/7/9.
 */
@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoFromMapper userInfoFromMapper;
    @Autowired
    private UserSecurityMapper userSecurityMapper;

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

    public void saveUserInfo(UserInfo userInfo,UserInfoFrom from){

        userInfoMapper.insert(userInfo);

        from.setUserId(userInfo.getId());
        userInfoFromMapper.insert(from);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserId(userInfo.getId());
        userSecurity.setCreateTime(new Date());
        userSecurityMapper.insert(userSecurity);
    }

    public void updateUserInfo(UserInfo userInfo){
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public UserInfo findByEMail(String email){
       return  userInfoMapper.findByEMail(email);
    }


}
