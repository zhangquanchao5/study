package com.study.service.impl;

import com.study.dao.UserInfoFromMapper;
import com.study.model.UserInfoFrom;
import com.study.service.IUserFromService;
import com.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public UserInfoFrom findByOpenIdAndFrom(String openId,String from){
        UserInfoFrom userInfoFrom=new UserInfoFrom();
        userInfoFrom.setFrom(Byte.parseByte(from));
        userInfoFrom.setOpenId(openId);
//        Map<String,String> map=new HashMap<String, String>();
//        map.put("openid",openId);
//        map.put("from",from);

        return userInfoFromMapper.findByOpenIdAndFrom(userInfoFrom);
    }
}
