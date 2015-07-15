package com.study.web;

import com.alibaba.fastjson.JSON;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;
import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.common.StringUtil;
import com.study.common.bean.AjaxResponseMessage;
import com.study.common.oss.DESUtils;
import com.study.common.util.PropertiesUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.service.IRedisService;
import com.study.service.IUserFromService;
import com.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

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
    @Autowired
    private IRedisService iRedisService;

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


    @RequestMapping(value = "/updateUp", method = RequestMethod.POST)
    public void updateUp(UserInfo userInfoModel, HttpServletResponse response) {

        AjaxResponseMessage message = new AjaxResponseMessage();
        try {
            iUserService.saveUserInfo(userInfoModel);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value = "/qqlogin")
    public String qqlogin(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try{
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        }catch (QQConnectException e) {
            printLogger(e);
            return null;
        }catch (IOException io){
            printLogger(io);
            return null;
        }

        return null;
    }


    @RequestMapping(value = "/qqloginUp")
    public String qqloginUp(HttpServletRequest request,ModelMap model, HttpServletResponse response) {
        String accessToken   = null,
                openID        = null;
        long tokenExpireIn = 0L;
        try{
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            if (accessTokenObj.getAccessToken().equals("")) {
//            我们的网站被CSRF攻击了或者用户取消了授权, 做一些数据统计工作
                model.put("message", "QQ账号授权有问题，请账号直接登录");
                return "error";
            } else {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();

                //判断系统是否有openId
                UserInfoFrom userInfoFromTemp=iUserFromService.findByOpenIdAndFrom(openID, EntityCode.USER_FROM_QQ + "");
                if(userInfoFromTemp==null){
                    com.qq.connect.api.qzone.UserInfo qzoneUserInfo = new com.qq.connect.api.qzone.UserInfo(accessToken, openID);
                    com.qq.connect.javabeans.qzone.UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();

                    UserInfo userInfo=new UserInfo();
                    userInfo.setIcon(userInfoBean.getAvatar().getAvatarURL100());
                    userInfo.setNick(userInfoBean.getNickname());
                    //性别没保存
                    iUserService.saveUserInfo(userInfo);

                    userInfoFromTemp=new UserInfoFrom();
                    userInfoFromTemp.setUserId(userInfo.getId());
                    userInfoFromTemp.setOpenId(openID);
                    userInfoFromTemp.setEx1(accessToken);
                    userInfoFromTemp.setEx2(tokenExpireIn + "");
                    userInfoFromTemp.setFrom(EntityCode.USER_FROM_QQ);

                    iUserFromService.saveUserFrom(userInfoFromTemp);
                }

                String ticketKey = UUID.randomUUID().toString().replace("-", "");
                String encodedticketKey = DESUtils.encrypt(ticketKey, PropertiesUtil.getString("sso.secretKey"));

                iRedisService.set(PrefixCode.API_COOKIE_PRE + ticketKey, userInfoFromTemp.getUserId().toString(), Integer.parseInt(PropertiesUtil.getString("sso.ticketTimeout")) * 60);

                Cookie cookie = new Cookie(PropertiesUtil.getString("sso.cookieName"), encodedticketKey);
                cookie.setSecure(Boolean.parseBoolean(PropertiesUtil.getString("sso.secure")));// 为true时用于https
                cookie.setMaxAge(7 * 24 * 3600);
                cookie.setDomain(PropertiesUtil.getString("sso.domainName"));
                cookie.setPath("/");
                response.addCookie(cookie);


            }
        }catch (QQConnectException e) {
            printLogger(e);
            return null;
        }

        return "index";
    }



    @RequestMapping(value = "/resetPwdByEmail")
    public void resetPwdByEmail(@RequestParam String email, HttpServletResponse response) {

        AjaxResponseMessage message = new AjaxResponseMessage();
        try {

        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value = "/activeEmail")
    public void activeEmail(@RequestParam String email, HttpServletResponse response) {

        AjaxResponseMessage message = new AjaxResponseMessage();
        try {

        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value = "/updateEmail")
    public void updateEmail(@RequestParam String email, HttpServletResponse response) {

        AjaxResponseMessage message = new AjaxResponseMessage();
        try {

        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }


}
