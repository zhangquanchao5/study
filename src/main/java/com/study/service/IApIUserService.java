package com.study.service;

import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.request.OrgRemarkReq;
import com.study.common.apibean.request.PwdResetRequest;
import com.study.common.apibean.response.UserResponse;
import com.study.common.apibean.response.UserStatisticsResponse;
import com.study.common.page.UserPageRequest;
import com.study.common.page.UserPageResponse;
import com.study.model.Account;
import com.study.model.UserInfo;

/**
 * Created by huichao on 2015/7/7.
 */
public interface IApIUserService {

    public UserInfo findByMobile(String mobile);

    public UserInfo findByMobile(String mobile,String domain);
//
//    public UserInfo findByUserName(String userName);
//
    public UserInfo findByUserName(String userName,String domain);
//
//
//    public UserInfo findByEMail(String email);
//
//    public UserInfo findByIdCard(String idCard);

    public UserInfo findById(Integer userId);

    public UserInfo findByToken(String token);

    public void updateUser(UserInfo userInfo);

    public Integer saveUser(ApiUserBean apiUserBean);

    public Integer updateRemoteUser(ApiUserBean apiUserBean,Integer userId);

    public UserInfo updateRemarkUser(OrgRemarkReq orgRemarkReq,Integer orgId);

    public void updateUserType(Integer userId,Integer type);

    public void updateUserPwd(PwdResetRequest pwdResetRequest);

    public void updateUserTime(Integer userId);

    public Account findAccountByUserId(Integer userId);

    public UserStatisticsResponse staticUserDomain(String domain,String dateStr );

    UserInfo findLoad(String login,String domain);


    public UserPageResponse findPageResponse(UserPageRequest userPageRequest);

    void sendEmail(final String mainBody, final String subject,
                   final String sendTo, final String sendFrom,
                   final String nick, final String password) throws Exception;
}
