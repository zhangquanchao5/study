package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.code.SplitCode;
import com.study.common.StringUtil;
import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.request.PwdResetRequest;
import com.study.common.apibean.response.UserResponse;
import com.study.common.bean.Mail;
import com.study.common.page.UserPageRequest;
import com.study.common.page.UserPageResponse;
import com.study.common.util.PropertiesUtil;
import com.study.dao.AccountMapper;
import com.study.dao.UserInfoFromMapper;
import com.study.dao.UserInfoMapper;
import com.study.dao.UserSecurityMapper;
import com.study.model.Account;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.model.UserSecurity;
import com.study.service.IApIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private UserSecurityMapper userSecurityMapper;

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

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserId(userInfo.getId());
        userSecurity.setCreateTime(new Date());
        userSecurityMapper.insert(userSecurity);
    }

    public void updateUserTime(Integer userId){
        userInfoMapper.updateUserTime(userId);
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

    @Override
    @Async
    public void sendEmail(final String mainBody, final String subject,
                          final String sendTo, final String sendFrom,
                          final String nick, final String password) throws Exception {
        Mail mail = new Mail(PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.SMTP.HOST"));
        mail.setNeedAuth(true);
        mail.setBody(mainBody);
        mail.setTo(sendTo);
        mail.setFrom(sendFrom, nick);
        mail.setNamePass(sendFrom, password);
        mail.setSubject(subject);
        mail.send();
    }

    public UserPageResponse findPageResponse(UserPageRequest userPageRequest){
//        if(userPageRequest.getId()!=null&&userPageRequest.getId().length>0){
//            StringBuffer sb=new StringBuffer();
//            for(Integer id:userPageRequest.getId()){
//                sb.append(id);
//                sb.append(SplitCode.SPLIT_DOUHAO);
//            }
//
//            userPageRequest.setIds(sb.toString().substring(0, sb.toString().length() - 1));
//            System.out.println("-------------"+userPageRequest.getIds());
//        }
        UserPageResponse userPageResponse=new UserPageResponse();
        userPageRequest.setStart((userPageRequest.getPage()-1)*userPageRequest.getSize());

        int count=userInfoMapper.findPageCount(userPageRequest);
        List<UserResponse> userResponseList=userInfoMapper.findPage(userPageRequest);

        userPageResponse.setCount(count);
        userPageResponse.setPage(userPageRequest.getPage());
        userPageResponse.setUsers(userResponseList);
        return userPageResponse;
    }
}
