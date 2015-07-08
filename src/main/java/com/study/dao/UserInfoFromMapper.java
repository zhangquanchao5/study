package com.study.dao;

import com.study.model.UserInfoFrom;

public interface UserInfoFromMapper {
    int insert(UserInfoFrom record);

    int insertSelective(UserInfoFrom record);
}