package com.study.service.impl;

import com.alibaba.fastjson.JSON;
import com.study.code.EntityCode;
import com.study.common.EmailException;
import com.study.common.Encrypt;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.bean.Mail;
import com.study.common.http.HttpSendResult;
import com.study.common.http.HttpUtil;
import com.study.common.util.PropertiesUtil;
import com.study.dao.UserInfoFromMapper;
import com.study.dao.UserInfoMapper;
import com.study.dao.UserSecurityMapper;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.model.UserSecurity;
import com.study.service.IUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

//    @Override
//    public UserInfo findByUserName(String userName) {
//        return userInfoMapper.findByUserName(userName);
//    }
//
//    public UserInfo findByUserName(String userName,String domain){
//        if(StringUtil.isEmpty(domain)){
//            return userInfoMapper.findByUserName(userName);
//        }else{
//            Map<String,String> map=new HashMap<String, String>();
//            map.put("userName",userName);
//            map.put("domain", domain);
//
//            return userInfoMapper.selectByDomainUserName(map);
//        }
//    }

    public UserInfo findLoad(String login,String domain){
        if(StringUtil.isEmpty(domain)){
            return userInfoMapper.findLoad(login);
        }else{
            Map<String,String> map=new HashMap<String, String>();
            map.put("login",login);
            map.put("domain", domain);

            return userInfoMapper.findDomainLoad(map);
        }
    }

    @Override
    public UserInfo fingById(Integer userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

//    public UserInfo findByMobile(String mobile){
//        return userInfoMapper.selectByMobile(mobile);
//    }
//
//    public UserInfo findByMobile(String mobile,String domain){
//        if(StringUtil.isEmpty(domain)){
//            return userInfoMapper.selectByMobile(mobile);
//        }else{
//            Map<String,String> map=new HashMap<String, String>();
//            map.put("mobile",mobile);
//            map.put("domain", domain);
//
//            return userInfoMapper.selectByDomainMobile(map);
//        }
//    }

    public void saveUserInfo(UserInfo userInfo,UserInfoFrom from){

        //判断如果是轻校网注册
        if(!StringUtil.isEmpty(userInfo.getDomain())){
            //判断主用户是否存在

            UserInfo userInfoTemp=userInfoMapper.selectByMobile(userInfo.getMobile());
            if(userInfoTemp!=null){
                userInfo.setParentId(userInfoTemp.getId());
                userInfo.setSource(EntityCode.USER_SOURCE_DOMAIN);
            }else{
                userInfoTemp=new UserInfo();
                BeanUtils.copyProperties(userInfo,userInfoTemp);
                userInfoTemp.setDomain(null);
                userInfoTemp.setSource(EntityCode.USER_SOURCE_APP);
                userInfoMapper.insert(userInfoTemp);

                userInfo.setParentId(userInfoTemp.getId());
                userInfo.setSource(EntityCode.USER_SOURCE_DOMAIN);
            }
        }
        userInfoMapper.insert(userInfo);

        from.setUserId(userInfo.getId());
        userInfoFromMapper.insert(from);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserId(userInfo.getId());
        userSecurity.setCreateTime(new Date());
        userSecurityMapper.insert(userSecurity);

        //调用接口同步到业务系统
        if(userInfo.getSource()==1){
            Map<String,String> map=new HashMap<String,String>();
            map.put("user_id", userInfo.getId().toString());
            map.put("org_name", userInfo.getName());
            HttpSendResult httpSendResult=HttpUtil.formPostUrl(PropertiesUtil.getString("ORG.SYN.URL"), map);
            StudyLogger.recBusinessLog("syn org result:"+ JSON.toJSONString(httpSendResult));
        }

    }

    public void updateUserInfo(UserInfo userInfo){
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }



//    public UserInfo findByEMail(String email){
//       return  userInfoMapper.findByEMail(email);
//    }

    public UserInfo findByIdcard(String card){
        return  userInfoMapper.findByIdCard(card);
    }


    public void updateUserTime(Integer userId){
        userInfoMapper.updateUserTime(userId);
    }


}
