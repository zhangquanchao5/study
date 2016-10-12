package com.study.service.impl;

import com.study.code.EntityCode;
import com.study.code.SplitCode;
import com.study.common.StringUtil;
import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.request.OrgRemarkReq;
import com.study.common.apibean.request.PwdResetRequest;
import com.study.common.apibean.response.UserResponse;
import com.study.common.apibean.response.UserStatisticsResponse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public UserInfo findByMobile(String mobile,String domain){
        if(StringUtil.isEmpty(domain)){
            return userInfoMapper.selectByMobile(mobile);
        }else{
            Map<String,String> map=new HashMap<String, String>();
            map.put("mobile",mobile);
            map.put("domain", domain);

            return userInfoMapper.selectByDomainMobile(map);
        }
    }

    public UserInfo findByUserName(String userName,String domain){
        if(StringUtil.isEmpty(domain)){
            return userInfoMapper.findByUserName(userName);
        }else{
            Map<String,String> map=new HashMap<String, String>();
            map.put("userName",userName);
            map.put("domain", domain);

            return userInfoMapper.selectByDomainUserName(map);
        }
    }

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
//
//    public UserInfo findByUserName(String userName){
//        return  userInfoMapper.findByUserName(userName);
//    }
//
//    public UserInfo findByEMail(String email){
//       return userInfoMapper.findByEMail(email);
//    }

//    public UserInfo findByIdCard(String idCard){
//        return userInfoMapper.findByIdCard(idCard);
//    }

    @Override
    public UserInfo findById(Integer userId) {
        return userInfoMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateUser(UserInfo userInfo) {
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }



    public   Integer saveUser(ApiUserBean apiUserBean){
        UserInfo userInfo=new UserInfo();
        userInfo.setCreateTime(new Date());
        if(!StringUtil.isEmpty(apiUserBean.getMobile())){
            userInfo.setMobile(apiUserBean.getMobile());
        }
        if(!StringUtil.isEmpty(apiUserBean.getUserName())){
            userInfo.setUserName(apiUserBean.getUserName());
        }
        userInfo.setPassword(StringUtil.getMD5Str(apiUserBean.getPassword()));
        userInfo.setIdCard(apiUserBean.getIdCard());
        userInfo.setStatus(EntityCode.USER_VALIDATE);

        if(StringUtil.isEmpty(apiUserBean.getDomain())){
            userInfo.setSource(EntityCode.USER_SOURCE_APP);
            userInfoMapper.insert(userInfo);
        }else{
            //判断是否存在主用户
            UserInfo userInfoParent=null;
            //兼容用户名和手机一块注册
            if(!StringUtil.isEmpty(apiUserBean.getMobile())&&!StringUtil.isEmpty(apiUserBean.getUserName())){
                userInfoParent=userInfoMapper.selectByMobile(apiUserBean.getMobile());
                if(userInfoParent==null){
                    userInfoParent=userInfoMapper.findByUserName(apiUserBean.getUserName());
                }
            }else if(!StringUtil.isEmpty(apiUserBean.getMobile())) {
                userInfoParent=userInfoMapper.selectByMobile(apiUserBean.getMobile());
            }else if(!StringUtil.isEmpty(apiUserBean.getIdCard())) {
                userInfoParent=userInfoMapper.findByIdCard(apiUserBean.getIdCard());
            }else if(!StringUtil.isEmpty(apiUserBean.getUserName())){
                userInfoParent=userInfoMapper.findByUserName(apiUserBean.getUserName());
            }
            if(userInfoParent!=null){
                userInfo.setSource(EntityCode.USER_SOURCE_DOMAIN);
                userInfo.setDomain(apiUserBean.getDomain());
                userInfo.setParentId(userInfoParent.getId());

                userInfoMapper.insert(userInfo);
            }else{
                userInfoParent=new UserInfo();
                userInfoParent.setCreateTime(new Date());
                if(!StringUtil.isEmpty(apiUserBean.getMobile())){
                    userInfoParent.setMobile(apiUserBean.getMobile());
                }
                if(!StringUtil.isEmpty(apiUserBean.getUserName())){
                    userInfoParent.setUserName(apiUserBean.getUserName());
                }
                userInfoParent.setSource(EntityCode.USER_SOURCE_APP);
                userInfoParent.setPassword(StringUtil.getMD5Str(apiUserBean.getPassword()));
                userInfoParent.setIdCard(apiUserBean.getIdCard());
                userInfoParent.setStatus(EntityCode.USER_VALIDATE);
                userInfoMapper.insert(userInfoParent);

                userInfo.setSource(EntityCode.USER_SOURCE_DOMAIN);
                userInfo.setDomain(apiUserBean.getDomain());
                userInfo.setParentId(userInfoParent.getId());

                userInfoMapper.insert(userInfo);

            }
        }


        UserInfoFrom userInfoFrom=new UserInfoFrom();
        userInfoFrom.setUserId(userInfo.getId());
        userInfoFrom.setFrom(EntityCode.USER_FROM_MOBILE);

        userInfoFromMapper.insert(userInfoFrom);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserId(userInfo.getId());
        userSecurity.setCreateTime(new Date());
        userSecurityMapper.insert(userSecurity);

        return userInfo.getId();
    }

    public Integer updateRemoteUser(ApiUserBean apiUserBean,Integer userId){
         UserInfo userInfo=userInfoMapper.selectByPrimaryKey(userId);
        if(userInfo==null){
            return 0;//用户不存在
        }
        if(!StringUtil.isEmpty(apiUserBean.getDomain())){
            //判断是否存在手机和域名的账号
            Map<String,String> map=new HashMap<String, String>();
            map.put("mobile",userInfo.getMobile());
            map.put("domain", apiUserBean.getDomain());

            UserInfo temp=userInfoMapper.selectByDomainMobile(map);
            if(temp==null){
                //增加新的主用户
                UserInfo userInfoParent=new UserInfo();
                userInfoParent.setCreateTime(new Date());
                userInfoParent.setMobile(userInfo.getMobile());
                userInfoParent.setSource(EntityCode.USER_SOURCE_APP);
                userInfoParent.setPassword(userInfo.getPassword());
                userInfoParent.setStatus(EntityCode.USER_VALIDATE);

                userInfoMapper.insert(userInfoParent);

                //更新userInfo
                userInfo.setDomain(apiUserBean.getDomain());
                if(StringUtil.isEmpty(userInfo.getIdCard())){
                    userInfo.setIdCard("U"+System.currentTimeMillis());
                }
                userInfo.setSource(EntityCode.USER_SOURCE_DOMAIN);
                userInfo.setParentId(userInfoParent.getId());

                userInfoMapper.updateByPrimaryKeySelective(userInfo);
                return 3;
            }else{
                //更新userInfo
                userInfo.setDomain(apiUserBean.getDomain());
                if(StringUtil.isEmpty(userInfo.getIdCard())){
                    userInfo.setIdCard("S"+System.currentTimeMillis());
                }

                userInfoMapper.updateByPrimaryKeySelective(userInfo);
                return 4;//已经存在
            }

        }

        return 2;
    }

    public UserInfo updateRemarkUser(OrgRemarkReq orgRemarkReq,Integer orgId){
        UserInfo userInfo=userInfoMapper.selectByPrimaryKey(orgRemarkReq.getUserId());
        userInfo.setRemark(orgRemarkReq.getRemark());

        userInfoMapper.updateByPrimaryKeySelective(userInfo);

        return userInfo;
    }

    public void updateUserType(Integer userId,Integer type){
        UserInfo userInfo=userInfoMapper.selectByPrimaryKey(userId);
        userInfo.setSource((byte)type.intValue());

        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public void updateUserTime(Integer userId){
        userInfoMapper.updateUserTime(userId);
    }

    public void updateUserPwd(PwdResetRequest pwdResetRequest){
        UserInfo userInfo=new UserInfo();
        userInfo.setPassword(StringUtil.getMD5Str(pwdResetRequest.getNewPassword()));
        userInfo.setMobile(pwdResetRequest.getUserPhone());
        userInfo.setDomain(pwdResetRequest.getDomain());

        userInfoMapper.updatePwd(userInfo);
    }

    public UserInfo findByToken(String token){
        return userInfoMapper.findByToken(token);
    }

    public Account findAccountByUserId(Integer userId){
       return accountMapper.selectByUserId(userId);
    }

    public UserStatisticsResponse staticUserDomain(String domain,String dateStr){
        UserStatisticsResponse userStatisticsResponse=new UserStatisticsResponse();
        Map<String,String> map=new HashMap<String, String>();
        map.put("date",dateStr);
        map.put("domain", domain);
        int month=userInfoMapper.countMonthByDomain(map);
        int all=userInfoMapper.countAllByDomain(domain);
        userStatisticsResponse.setCurrentMonth(month);
        userStatisticsResponse.setUserAll(all);
        userStatisticsResponse.setAddRate(StringUtil.numberFormat(all,month));
        return userStatisticsResponse;
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

        UserPageResponse userPageResponse=new UserPageResponse();
        userPageRequest.setStart(((userPageRequest.getPage()==null?0:userPageRequest.getPage()-1))*(userPageRequest.getSize()==null?20:userPageRequest.getSize()));
        userPageRequest.setSize(userPageRequest.getSize() == null ? 20 : userPageRequest.getSize());

        int count=userInfoMapper.findPageCount(userPageRequest);
        List<UserResponse> userResponseList=userInfoMapper.findPage(userPageRequest);

        userPageResponse.setCount(count);
        userPageResponse.setPage(userPageRequest.getPage());
        userPageResponse.setUsers(userResponseList);
        return userPageResponse;
    }
}
