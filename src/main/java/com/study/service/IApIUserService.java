package com.study.service;

import com.study.common.apibean.ApiUserBean;
import com.study.model.UserInfo;

/**
 * Created by huichao on 2015/7/7.
 */
public interface IApIUserService {

    public UserInfo findByMobile(String mobile);

    public void saveUser(ApiUserBean apiUserBean);

    public void updateUserToken(UserInfo userInfo);
}
