package com.study.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.code.SplitCode;
import com.study.common.Encrypt;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.apibean.QqOpenIdBean;
import com.study.common.apibean.response.CommonResponse;
import com.study.common.bean.AjaxResponseMessage;
import com.study.common.http.HttpSendResult;
import com.study.common.http.HttpUtil;
import com.study.common.session.LoginUser;
import com.study.common.session.SessionInfo;
import com.study.common.util.PropertiesUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.model.UserInfoFrom;
import com.study.service.IApIUserService;
import com.study.service.IRedisService;
import com.study.service.IUserFromService;
import com.study.service.IUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private IApIUserService iApIUserService;

    @RequestMapping(value = "/registerUp", method = RequestMethod.POST)
    public void registerUp(UserInfo userInfoModel, @RequestParam String valCode, HttpServletResponse response) {
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

            UserInfo userInfoMail = iUserService.findByEMail(userInfoModel.getUserMail());
            if (userInfoMail != null) {
                message.setSuccess(false);
                message.setCode(ErrorCode.USER_EXITS);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }



            //判断注册码是否有效
            String code = iRedisService.get(PrefixCode.API_MOBILE_REGISTER + userInfoModel.getMobile());
            if (code != null && !"".equals(code) && code.equals(valCode)) {
                iRedisService.deleteOneKey(PrefixCode.API_MOBILE_REGISTER + userInfoModel.getMobile());
                userInfoModel.setPassword(StringUtil.getMD5Str(userInfoModel.getPassword()));
                userInfoModel.setCreateTime(new Date());
                userInfoModel.setStatus(EntityCode.USER_VALIDATE);

                UserInfoFrom userInfoFrom = new UserInfoFrom();
                userInfoFrom.setFrom(EntityCode.USER_FROM_MOBILE);

                iUserService.saveUserInfo(userInfoModel, userInfoFrom);
            } else {
                message.setSuccess(false);
                message.setCode(ErrorCode.USER_CODE_ERROR);
            }
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
            iUserService.updateUserInfo(userInfoModel);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value = "/userMail", method = RequestMethod.POST)
    public void userMail(UserInfo userInfoModel, HttpServletResponse response) {

        AjaxResponseMessage ajaxResponseMessage = new AjaxResponseMessage();
        try {
            UserInfo userInfo=iUserService.findByEMail(userInfoModel.getUserMail());
            if(userInfo!=null){
                String random = RandomStringUtils.randomAlphanumeric(32);
                String actUrl = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.VERTIFY.URL") +
                        "user/mail?uid=" + Encrypt.doEncrypt(String.valueOf(userInfo.getId())) +
                        "&mid=" + Encrypt.doEncrypt(userInfo.getUserMail()) +
                        "&sid=" + random;
                String message = messageUtil.getMessage("MSG.EMAIL.ACTIVE.SEND").replace("#acturl", actUrl);
                String subject = messageUtil.getMessage("MSG.EMAIL.ACTIVE.SEND.SUBJECT");
                String sendFrom = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.ACCOUNT");
                String mailPwd = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.PASSWORD");
                String nick = PropertiesUtil.getString("MSG.EMAIL.ACTIVE.SEND.MAINT");

                iApIUserService.sendEmail(message, subject, userInfo.getUserMail(), sendFrom, nick, mailPwd);
                iRedisService.set(PrefixCode.API_MAIL_CONNACT + userInfo.getId(), random, Integer.parseInt(PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.TIMEOUT")) * 60);

            }else{
                ajaxResponseMessage.setSuccess(false);
            }
        } catch (Exception e) {
            ajaxResponseMessage.setSuccess(false);
            ajaxResponseMessage.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(ajaxResponseMessage).toString());
    }

    @RequestMapping("/mail")
    public ModelAndView mail(@RequestParam String uid, @RequestParam String mid, @RequestParam String sid,  HttpServletResponse response, HttpServletRequest request) {
        ModelAndView modelAndView=new ModelAndView();
        try {
            Object obj = iRedisService.get(PrefixCode.API_MAIL_CONNACT + Encrypt.doDecrypt(uid));

                if (obj == null) {
                    modelAndView.addObject("code",ErrorCode.ERROR);
                    modelAndView.setViewName("email/fail");
                } else {
                    if (obj.toString().equals(sid)) {
                        UserInfo u=iApIUserService.findByEMail(Encrypt.doDecrypt(mid));
                        if(u!=null){
                            iRedisService.deleteOneKey(PrefixCode.API_MAIL_CONNACT + Encrypt.doDecrypt(uid));
                            modelAndView.addObject("mobile", u.getMobile());
                            modelAndView.addObject("code", ErrorCode.SUCCESS);
                            modelAndView.setViewName("email/success");
                        } else {
                            modelAndView.addObject("code", ErrorCode.ERROR);
                            modelAndView.setViewName("email/fail");
                        }
                    } else {
                        modelAndView.addObject("code", ErrorCode.ERROR);
                        modelAndView.setViewName("email/fail");
                    }
                }

        } catch (Exception e) {
            modelAndView.addObject("code", ErrorCode.SYS_ERROR);
            modelAndView.setViewName("email/fail");
            printLogger(e);
        }
      return modelAndView;
    }

    @RequestMapping(value = "/registerValidate")
    public void registerValidate(UserInfo userInfoModel,String type, HttpServletResponse response) {

        Map message = new HashMap();
        try {
            if (!StringUtil.isEmpty(userInfoModel.getUserName())) {

                if (iUserService.findByUserName(userInfoModel.getUserName()) != null || iUserService.findByMobile(userInfoModel.getUserName()) != null || iUserService.findByEMail(userInfoModel.getUserName()) != null) {
                    message.put("error", messageUtil.getMessage("MSG.USER_EXITS_CN"));
                    message.put("success", false);

                } else {
                    message.put("ok", messageUtil.getMessage("msg.register.success"));
                    message.put("success", true);
                }
            } else if (!StringUtil.isEmpty(userInfoModel.getMobile())) {
                if (iUserService.findByUserName(userInfoModel.getMobile()) != null || iUserService.findByMobile(userInfoModel.getMobile()) != null || iUserService.findByEMail(userInfoModel.getMobile()) != null) {
                    message.put("error", messageUtil.getMessage("MSG.USER_EXITS_CN"));
                    message.put("success", false);

                } else {
                    message.put("ok", messageUtil.getMessage("msg.register.success"));
                    message.put("success", true);

                }
            }else if (!StringUtil.isEmpty(userInfoModel.getUserMail())) {
                if(!StringUtil.isEmpty(type)){
                    if ( iUserService.findByEMail(userInfoModel.getUserMail()) != null) {
                        message.put("ok", messageUtil.getMessage("msg.mail.success"));
                        message.put("success", true);
                    } else {
                        message.put("error", messageUtil.getMessage("msg.mail.error"));
                        message.put("success", false);
                    }
                }else{
                    if ( iUserService.findByEMail(userInfoModel.getUserMail()) != null) {
                        message.put("error", messageUtil.getMessage("MSG.USER_EXITS_CN"));
                        message.put("success", false);

                    } else {
                        message.put("ok", messageUtil.getMessage("msg.register.success"));
                        message.put("success", true);

                    }
                }

            }
        } catch (Exception e) {
            message.put("error", messageUtil.getMessage("MSG.SYS_ERROR_CN"));
            message.put("success", false);

            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value = "/forgetValidate")
    public void forgetValidate(UserInfo userInfoModel, HttpServletResponse response) {

        Map message = new HashMap();
        try {
            if (!StringUtil.isEmpty(userInfoModel.getUserName())) {
                UserInfo user=iUserService.findByUserName(userInfoModel.getUserName());
                if(user==null){
                    user=iUserService.findByMobile(userInfoModel.getUserName());
                    if(user==null){
                        user=iUserService.findByEMail(userInfoModel.getUserName());
                    }
                }

                if (user != null) {
                    message.put("ok", messageUtil.getMessage("MSG.FORGET_PWD_OK_CN"));
                    message.put("success", true);
                    message.put("mobile",user.getMobile());
                } else {
                    message.put("error", messageUtil.getMessage("MSG.FORGET_PWD_CN"));
                    message.put("success", false);
                }
            }
        } catch (Exception e) {
            message.put("error", messageUtil.getMessage("MSG.SYS_ERROR_CN"));
            message.put("success", false);

            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public void code(UserInfo userInfoModel, @RequestParam String valCode, HttpServletResponse response) {
        AjaxResponseMessage message = new AjaxResponseMessage();
        try {
            //判断注册码是否有效
            String code = iRedisService.get(PrefixCode.API_MOBILE_RESET + userInfoModel.getMobile());

            if (code != null && !"".equals(code) && code.equals(valCode)) {
                iRedisService.deleteOneKey(PrefixCode.API_MOBILE_RESET + userInfoModel.getMobile());
                message.setSuccess(true);
            } else {
                message.setSuccess(false);
                message.setCode(ErrorCode.USER_CODE_ERROR);
            }
        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }


    @RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
    public void code(UserInfo userInfoModel, HttpServletResponse response) {
        AjaxResponseMessage message = new AjaxResponseMessage();
        try {
            UserInfo userInfoMobile = iUserService.findByMobile(userInfoModel.getMobile());
            userInfoMobile.setPassword(StringUtil.getMD5Str(userInfoModel.getPassword()));

            iUserService.updateUserInfo(userInfoMobile);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value = "/qqlogin")
    public String qqlogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = PropertiesUtil.getString("authorizeURL") + "?response_type=code&client_id=101240680&redirect_uri=" + PropertiesUtil.getString("redirect_URI") + "&scope=get_user_info";
            response.sendRedirect(url);
        } catch (Exception e) {
            printLogger(e);
            return "login_old";
        }

        return null;
    }


    @RequestMapping(value = "/qqloginUp")
    public String qqloginUp(HttpServletRequest request, ModelMap model, HttpServletResponse response) {
        String code = request.getParameter("code");
        try {
            if (code == null || code.equals("")) {
                return "login_old";
            } else {
                //第一步获取TOKE
                String codeUrl = PropertiesUtil.getString("accessTokenURL") + "?grant_type=authorization_code&client_id=" + PropertiesUtil.getString("app_ID") + "&client_secret=" + PropertiesUtil.getString("app_KEY") + "&code=" + code + "&redirect_uri=" + PropertiesUtil.getString("redirect_URI");
                StudyLogger.recBusinessLog("token url:" + codeUrl);
                HttpSendResult httpSendResult = HttpUtil.executeGet(codeUrl);
                String userId = null;
                if (httpSendResult.getStatusCode() == 200) {
                    StudyLogger.recBusinessLog("token response:" + httpSendResult.getResponse());
                    //第二步获取openid
                    String openUrl = PropertiesUtil.getString("getOpenIDURL") + "?" + httpSendResult.getResponse();
                    StudyLogger.recBusinessLog("openid url:" + openUrl);

                    HttpSendResult openResult = HttpUtil.executeGet(openUrl);
                    if (openResult.getStatusCode() == 200) {
                        StudyLogger.recBusinessLog("openid response:" + openResult.getResponse());
                        QqOpenIdBean qqOpenIdBean = JSON.parseObject(openResult.getResponse().replace("callback( ", "").replace(" );", "").trim(), QqOpenIdBean.class);

                        //判断系统是否有openId
                        System.out.println("----------" + qqOpenIdBean.getOpenid() + "   " + EntityCode.USER_FROM_QQ + "" + "---");
                        UserInfoFrom userInfoFromTemp = iUserFromService.findByOpenIdAndFrom(qqOpenIdBean.getOpenid(), EntityCode.USER_FROM_QQ + "");
                        if (userInfoFromTemp == null) {
                            //第三部获取个人信息
                            String userUrl = PropertiesUtil.getString("getUserInfoURL") + "?oauth_consumer_key=" + qqOpenIdBean.getClient_id() + "&openid=" + qqOpenIdBean.getOpenid() + "&" + httpSendResult.getResponse();
                            StudyLogger.recBusinessLog("user url:" + userUrl);

                            HttpSendResult userResult = HttpUtil.executeGet(userUrl);
                            if (userResult.getStatusCode() == 200) {
                                StudyLogger.recBusinessLog("user response:" + userResult.getResponse());
                                JSONObject jsonObject = JSON.parseObject(userResult.getResponse());
                                UserInfo userInfo = new UserInfo();
                                userInfo.setIcon(jsonObject.getString("figureurl_2"));
                                userInfo.setNick(jsonObject.getString("nickname"));
                                userInfo.setUserName(qqOpenIdBean.getOpenid());
                                //性别没保存
                                userInfoFromTemp = new UserInfoFrom();
                                userInfoFromTemp.setOpenId(qqOpenIdBean.getOpenid());
//                            userInfoFromTemp.setEx1(accessToken);
//                            userInfoFromTemp.setEx2(tokenExpireIn + "");
                                userInfoFromTemp.setFrom(EntityCode.USER_FROM_QQ);
                                iUserService.saveUserInfo(userInfo, userInfoFromTemp);
                                userId = userInfo.getId().toString();
                            }
                        } else {
                            userId = userInfoFromTemp.getUserId().toString();
                        }

                    }
                }

                if (userId != null) {
                    UserInfo realUser = iUserService.fingById(Integer.parseInt(userId));
                    long endTime = +Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")) * 60 * 1000 + System.currentTimeMillis();
                    String ticketKey = userId + SplitCode.SPLIT_EQULE + StringUtil.getBASE64(userId + SplitCode.SPLIT_EQULE + PrefixCode.API_HEAD_WEB + SplitCode.SPLIT_SHU + userId + SplitCode.SPLIT_SHU + endTime);
                    String encodedticketKey = StringUtil.getBASE64(ticketKey);

                    iRedisService.setObject(PrefixCode.API_COOKIE_PRE + ticketKey, changeUser(realUser), Integer.parseInt(PropertiesUtil.getString("sso.ticketTimeout")) * 60);
                    Cookie cookie = new Cookie(PropertiesUtil.getString("sso.cookieName"), encodedticketKey);
                    cookie.setSecure(Boolean.parseBoolean(PropertiesUtil.getString("sso.secure")));// 为true时用于https
                    cookie.setMaxAge(7 * 24 * 3600);
                    cookie.setDomain(PropertiesUtil.getString("sso.domainName"));
                    cookie.setPath("/");
                    response.addCookie(cookie);

                    SessionInfo sessionInfo = new SessionInfo(realUser);
                    LoginUser.getCurrentSession().setAttribute(LoginUser.USER_SESSION_INFO, sessionInfo);
                    iUserService.updateUserTime(realUser.getId());
                } else {
                    return "login_old";
                }
            }
        } catch (Exception e) {
            printLogger(e);
            return "login_old";
        }

        return "account/accountManagement";
    }


    @RequestMapping(value = "/forget")
    public String forget(HttpServletRequest request,  HttpServletResponse response) {

        return "user/forget_new";
    }
}
