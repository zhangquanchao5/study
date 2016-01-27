package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.code.SplitCode;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.bean.AjaxResponseMessage;
import com.study.common.session.LoginUser;
import com.study.common.session.SessionInfo;
import com.study.common.util.PropertiesUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.service.IRedisService;
import com.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huichao on 2015/7/9.
 */
@Controller
@RequestMapping
public class LoginController extends BaseController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRedisService iRedisService;

    @RequestMapping(value ="/loginUp",method = RequestMethod.POST)
    public void login(UserInfo userInfoModel,HttpServletRequest request, HttpServletResponse response) {

        AjaxResponseMessage message = new AjaxResponseMessage();
        try {
            UserInfo userInfo = iUserService.findByUserName(userInfoModel.getUserName());
            if (userInfo == null) {
                userInfo=iUserService.findByMobile(userInfoModel.getUserName());
                if(userInfo==null){
                    userInfo=iUserService.findByEMail(userInfoModel.getUserName());
                    if(userInfo==null){
                        message.setSuccess(false);
                        message.setCode(ErrorCode.USER_NOT_EXITS);
                        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                        return;
                    }
                }
            }

            //判断密码是否正确
            if(!StringUtil.getMD5Str(userInfoModel.getPassword()).equals(userInfo.getPassword())) {
                message.setSuccess(false);
                message.setCode(ErrorCode.USER_PWD_ERROR);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }
            long endTime=+Long.parseLong(PropertiesUtil.getString("TOKEN.TIME"))*60*1000+System.currentTimeMillis();
            String ticketKey=userInfo.getId()+SplitCode.SPLIT_EQULE+StringUtil.getBASE64(userInfo.getId()+SplitCode.SPLIT_EQULE+PrefixCode.API_HEAD_WEB+SplitCode.SPLIT_SHU +userInfo.getId()+  SplitCode.SPLIT_SHU+endTime);
        //    String encodedticketKey = DESUtils.encrypt(ticketKey, PropertiesUtil.getString("sso.secretKey"));
            String encodedticketKey=StringUtil.getBASE64(ticketKey);

            iRedisService.setObject(PrefixCode.API_COOKIE_PRE + ticketKey, changeUser(userInfo), Integer.parseInt(PropertiesUtil.getString("sso.ticketTimeout")) * 60);

            Cookie cookie = new Cookie(PropertiesUtil.getString("sso.cookieName"), encodedticketKey);
            StudyLogger.recSysLog("sso.cookieName[Header-Authorization]:" + encodedticketKey);
            cookie.setSecure(Boolean.parseBoolean(PropertiesUtil.getString("sso.secure")));// 为true时用于https
            cookie.setMaxAge(7 * 24 * 3600);
            cookie.setDomain(PropertiesUtil.getString("sso.domainName"));
            cookie.setPath("/");
            response.addCookie(cookie);

            if(StringUtil.isEmpty(userInfo.getUserName())){
                userInfo.setUserName(userInfo.getMobile());
            }
            SessionInfo sessionInfo=new SessionInfo(userInfo);
            LoginUser.getCurrentSession().setAttribute(LoginUser.USER_SESSION_INFO, sessionInfo);

            iUserService.updateUserTime(userInfo.getId());
            if(userInfo.getSource()==1){
                message.setCode(ErrorCode.SUCCESS);
                message.setData(PropertiesUtil.getString("ORG.LOGIN.REDIRECT"));
            }
            String gotoURL = request.getParameter("gotoURL");
            if (!StringUtil.isEmpty(gotoURL))
            {
               message.setCode(StringUtil.getFromBASE64(gotoURL));
            }
        } catch (Exception e) {
            message.setSuccess(false);
            message.setCode(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        StudyLogger.recSysLog("login.json"+ JSON.toJSON(message).toString());
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping(value ="/logout")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        try{
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(PropertiesUtil.getString("sso.cookieName"))) {
                        String decodedTicket =StringUtil.getFromBASE64(cookie.getValue());
                        iRedisService.deleteOneKey(PrefixCode.API_COOKIE_PRE + decodedTicket);
                        LoginUser.getCurrentSession().invalidate();
                        Cookie cookied = new Cookie(PropertiesUtil.getString("sso.cookieName"), cookie.getValue());
                        StudyLogger.recSysLog("sso.cookieName[Header-Authorization]:" + cookie.getValue());
                        cookied.setSecure(Boolean.parseBoolean(PropertiesUtil.getString("sso.secure")));// 为true时用于https
                        cookied.setMaxAge(0);
                        cookied.setDomain(PropertiesUtil.getString("sso.domainName"));
                        cookied.setPath("/");
                        response.addCookie(cookied);

                       // LoginUser.getCurrentSession().invalidate();
                        break;
                    }
                }

        }catch (Exception e){
            printLogger(e);
        }
        return "login";
    }



}
