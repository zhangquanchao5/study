package com.study.dao;

import com.study.model.UserInfo;


public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

     int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    UserInfo selectByMobile(String mobile);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo findByUserName(String userName);
}