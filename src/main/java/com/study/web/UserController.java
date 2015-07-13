package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.common.StringUtil;
import com.study.common.bean.AjaxResponseMessage;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.service.IUserFromService;
import com.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by huichao on 2015/7/13.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IUserFromService iUserFromService;

    @RequestMapping(value = "/registerUp", method = RequestMethod.POST)
    public void registerUp(UserInfo userInfoModel, HttpServletResponse response) {

        AjaxResponseMessage message = new AjaxResponseMessage();
        try {
            UserInfo userInfo = iUserService.findByUserName(userInfoModel.getUserName());
            if (userInfo != null) {
                message.setSuccess(false);
                message.setCode(ErrorCode.USER_EXITS);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }

            UserInfo userInfoMobile = iUserService.findByMobile(userInfoModel.getMobile());
            if (userInfoMobile != null) {
                message.setSuccess(false);
                message.setCode(ErrorCode.USER_EXITS);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }

            userInfoModel.setPassword(StringUtil.getMD5Str(userInfoModel.getPassword()));
            userInfoModel.setCreateTime(new Date());
            userInfoModel.setStatus(EntityCode.USER_VALIDATE);
            iUserService.saveUserInfo(userInfoModel);

            UserInfoFrom userInfoFrom = new UserInfoFrom();
            userInfoFrom.setUserId(userInfoModel.getId());
            userInfoFrom.setFrom(EntityCode.USER_FROM_MOBILE);

            iUserFromService.saveUserFrom(userInfoFrom);


        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }


}
