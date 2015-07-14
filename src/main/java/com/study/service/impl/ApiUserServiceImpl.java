package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.common.StringUtil;
import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.request.PwdResetRequest;
import com.study.dao.AccountMapper;
import com.study.dao.UserInfoFromMapper;
import com.study.dao.UserInfoMapper;
import com.study.model.Account;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.service.IApIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by huichao on 2015/7/7.
 */
@Service
public class ApiUserServiceImpl implements IApIUserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoFromMapper userInfoFromMapper;

    @Autowired
    private AccountMapper accountMapper;

    public UserInfo findByMobile(String mobile){
        return userInfoMapper.selectByMobile(mobile);
    }

    public UserInfo findByUserName(String userName){
        return  userInfoMapper.findByUserName(userName);
    }

    public UserInfo findByEMail(String email){
       return userInfoMapper.findByEMail(email);
    }

    @Override
    public UserInfo findById(Integer userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }



    public   void saveUser(ApiUserBean apiUserBean){
        UserInfo userInfo=new UserInfo();
        userInfo.setCreateTime(new Date());
        userInfo.setMobile(apiUserBean.getMobile());
        userInfo.setSource(EntityCode.USER_SOURCE_APP);
        userInfo.setPassword(StringUtil.getMD5Str(apiUserBean.getPassword()));
       // userInfo.setUserName(apiUserBean.getMobile());
        userInfo.setStatus(EntityCode.USER_VALIDATE);
        userInfoMapper.insert(userInfo);

        UserInfoFrom userInfoFrom=new UserInfoFrom();
        userInfoFrom.setUserId(userInfo.getId());
        userInfoFrom.setFrom(EntityCode.USER_FROM_MOBILE);

        userInfoFromMapper.insert(userInfoFrom);
    }

    public void updateUserToken(UserInfo userInfo){
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public void updateUserPwd(PwdResetRequest pwdResetRequest){
        UserInfo userInfo=new UserInfo();
        userInfo.setPassword(StringUtil.getMD5Str(pwdResetRequest.getNewPassword()));
        userInfo.setMobile(pwdResetRequest.getUserPhone());

        userInfoMapper.updatePwd(userInfo);
    }

    public UserInfo findByToken(String token){
        return userInfoMapper.findByToken(token);
    }

    public Account findAccountByUserId(Integer userId){
       return accountMapper.selectByUserId(userId);
    }
}
