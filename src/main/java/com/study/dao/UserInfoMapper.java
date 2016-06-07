package com.study.dao;

import com.study.common.apibean.response.UserResponse;
import com.study.common.page.UserPageRequest;
import com.study.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo findByUserName(String userName);

    UserInfo selectByMobile(String mobile);

    UserInfo selectByDomainMobile(Map map);

    UserInfo selectByDomainUserName(Map map);

    int updatePwd(UserInfo record);

     UserInfo findByToken(String token);


     UserInfo findByEMail(String email);

     UserInfo findByIdCard(String idCard);

     void updateUserTime(Integer userId);

    int findPageCount(UserPageRequest userPageRequest);

    List<UserResponse> findPage(UserPageRequest userPageRequest);
}