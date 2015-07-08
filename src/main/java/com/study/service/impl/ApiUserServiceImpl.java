package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.common.StringUtil;
import com.study.common.apibean.ApiUserBean;
import com.study.dao.UserInfoFromMapper;
import com.study.dao.UserInfoMapper;
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

    public UserInfo findByMobile(String mobile){
        return userInfoMapper.selectByMobile(mobile);
    }

    public   void saveUser(ApiUserBean apiUserBean){
        UserInfo userInfo=new UserInfo();
        userInfo.setCreateTime(new Date());
        userInfo.setMobile(apiUserBean.getMobile());
        userInfo.setSource(EntityCode.USER_SOURCE_APP);
        userInfo.setPassword(StringUtil.getMD5Str(apiUserBean.getPassword()));
        userInfo.setUserName(apiUserBean.getMobile());
        userInfo.setStatus(EntityCode.USER_VALIDATE);
        userInfoMapper.insert(userInfo);

        UserInfoFrom userInfoFrom=new UserInfoFrom();
        userInfoFrom.setUserId(userInfo.getId());
        userInfoFrom.setFrom(EntityCode.USER_FROM_MOBILE);

        userInfoFromMapper.insert(userInfoFrom);
    }
}
