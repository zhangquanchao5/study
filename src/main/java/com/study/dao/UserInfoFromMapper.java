package com.study.dao;

import com.study.model.UserInfoFrom;

import java.util.Map;

public interface UserInfoFromMapper {
    int insert(UserInfoFrom record);

    int insertSelective(UserInfoFrom record);

    UserInfoFrom findByOpenIdAndFrom(Map<String,String> map);
}