package com.study.dao.xml;

import com.study.model.UserInfoFrom;

public interface UserInfoFromMapper {
    int insert(UserInfoFrom record);

    int insertSelective(UserInfoFrom record);
}