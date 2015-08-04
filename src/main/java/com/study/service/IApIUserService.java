package com.study.service;

import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.request.PwdResetRequest;
import com.study.common.page.UserPageRequest;
import com.study.common.page.UserPageResponse;
import com.study.model.Account;
import com.study.model.UserInfo;

/**
 * Created by huichao on 2015/7/7.
 */
public interface IApIUserService {

    public UserInfo findByMobile(String mobile);

    public UserInfo findByUserName(String userName);

    public UserInfo findByEMail(String email);

    public UserInfo findById(Integer userId);

    public UserInfo findByToken(String token);

    public void updateUser(UserInfo userInfo);

    public void saveUser(ApiUserBean apiUserBean);

    public void updateUserPwd(PwdResetRequest pwdResetRequest);

    public void updateUserTime(Integer userId);

    public Account findAccountByUserId(Integer userId);

    public UserPageResponse findPageResponse(UserPageRequest userPageRequest);

    void sendEmail(final String mainBody, final String subject,
                   final String sendTo, final String sendFrom,
                   final String nick, final String password) throws Exception;
}
