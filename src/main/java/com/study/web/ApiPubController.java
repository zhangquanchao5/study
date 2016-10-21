package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.code.SplitCode;
import com.study.common.ImageUtil;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.AuthHeaderBean;
import com.study.common.apibean.MobileBean;
import com.study.common.apibean.request.LoginRequest;
import com.study.common.apibean.request.MobileRequest;
import com.study.common.apibean.request.RegisterMobileRequest;
import com.study.common.apibean.request.RemoteRegReq;
import com.study.common.apibean.response.*;
import com.study.common.http.ApiHttpUtil;
import com.study.common.sms.SendSm;
import com.study.common.sms.SmsResponse;
import com.study.common.util.PropertiesUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.service.IApIUserService;
import com.study.service.IRedisService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by huichao on 2015/7/13.
 */
@Controller
@RequestMapping("/pub")
public class ApiPubController extends BaseController {

    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private IApIUserService iApIUserService;

    /**
     * 1发送手机验证码
     */
    @RequestMapping(value = "/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        MobileBean mobileBean = new MobileBean();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/getCode:" + json);

            MobileRequest mobileRequest = JSON.parseObject(json, MobileRequest.class);
            //判断是否签名正确
            if (StringUtils.isEmpty(mobileRequest.getSignature()) || StringUtils.isEmpty(mobileRequest.getTimeStamp())) {
                mobileBean.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(mobileBean).toString());
                return;
            }
            if (!validatePubSingature(mobileRequest.getTimeStamp(), mobileRequest.getSignature())) {
                mobileBean.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(mobileBean).toString());
                return;
            }
            if (mobileRequest.getType() == EntityCode.MOBILE_REGESITER) {
                UserInfo userInfo = iApIUserService.findByMobile(mobileRequest.getUserPhone(), mobileRequest.getDomain());
                if (userInfo != null) {
                    mobileBean.setCode(ErrorCode.USER_EXITS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.USER_EXITS_CN"));
                } else {
                    String code = StringUtil.generateTextCode(0, 6, null);
                    //SEND MOBILE
                    SmsResponse smsResponse = SendSm.sendSms(mobileRequest.getUserPhone(), messageUtil.getMessage("MSG.SMSSEND.CONTENT").replace("#CODE", code));
                    System.out.println(smsResponse.getCode() + ":" + smsResponse.getMsg() + ":" + smsResponse.getSmsid());
                    if (smsResponse.getCode().equals(SendSm.SUCCE_CODE)) {
                        mobileBean.setCode(ErrorCode.SUCCESS);
                        mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                        mobileBean.setVerifyCode(code);
                        iRedisService.set(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone(), code, 300);
                    } else {
                        mobileBean.setCode(ErrorCode.ERROR);
                        mobileBean.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
                    }
                }
            } else if (mobileRequest.getType() == EntityCode.MOBILE_BIND_UPDATE) {
                UserInfo userInfo = iApIUserService.findByMobile(mobileRequest.getUserPhone(), mobileRequest.getDomain());
                if (userInfo != null) {
                    mobileBean.setCode(ErrorCode.USER_EXITS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.USER_EXITS_CN"));
                } else {
                    String code = StringUtil.generateTextCode(0, 6, null);
                    //SEND MOBILE
                    SmsResponse smsResponse = SendSm.sendSms(mobileRequest.getUserPhone(), messageUtil.getMessage("MSG.SMSSEND.CONTENT").replace("#CODE", code));
                    if (smsResponse.getCode().equals(SendSm.SUCCE_CODE)) {
                        mobileBean.setCode(ErrorCode.SUCCESS);
                        mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                        mobileBean.setVerifyCode(code);
                        iRedisService.set(PrefixCode.API_MOBILE_BIND + mobileRequest.getUserPhone(), code, 300);
                    } else {
                        mobileBean.setCode(ErrorCode.ERROR);
                        mobileBean.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
                    }
                }
            } else if (mobileRequest.getType() == EntityCode.MOBILE_GET_PASSWORD) {
                UserInfo userInfo = iApIUserService.findByMobile(mobileRequest.getUserPhone(), mobileRequest.getDomain());
                if (userInfo == null) {
                    mobileBean.setCode(ErrorCode.USER_NOT_EXITS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.USER_NOT_EXITS_CN"));
                } else {
                    String code = StringUtil.generateTextCode(0, 6, null);
                    //SEND MOBILE
                    SmsResponse smsResponse = SendSm.sendSms(mobileRequest.getUserPhone(), messageUtil.getMessage("MSG.SMSSEND.CONTENT").replace("#CODE", code));
                    if (smsResponse.getCode().equals(SendSm.SUCCE_CODE)) {
                        mobileBean.setCode(ErrorCode.SUCCESS);
                        mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                        mobileBean.setVerifyCode(code);
                        iRedisService.set(PrefixCode.API_MOBILE_UPDATE + mobileRequest.getUserPhone(), code, 300);
                    } else {
                        mobileBean.setCode(ErrorCode.ERROR);
                        mobileBean.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
                    }
                }
            } else if (mobileRequest.getType() == EntityCode.MOBILE_YU_YUE) {
                String code = StringUtil.generateTextCode(0, 6, null);
                //SEND MOBILE
                SmsResponse smsResponse = SendSm.sendSms(mobileRequest.getUserPhone(), messageUtil.getMessage("MSG.SMSSEND.CONTENT").replace("#CODE", code));
                if (smsResponse.getCode().equals(SendSm.SUCCE_CODE)) {
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                    mobileBean.setVerifyCode(code);
                    iRedisService.set(PrefixCode.API_MOBILE_UPDATE + mobileRequest.getUserPhone(), code, 300);
                } else {
                    mobileBean.setCode(ErrorCode.ERROR);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
                }
            } else if (mobileRequest.getType() == EntityCode.MOBILE_RESET_PWD) {
                String code = StringUtil.generateTextCode(0, 6, null);
                //SEND MOBILE
                SmsResponse smsResponse = SendSm.sendSms(mobileRequest.getUserPhone(), messageUtil.getMessage("MSG.SMSSEND.CONTENT").replace("#CODE", code));
                if (smsResponse.getCode().equals(SendSm.SUCCE_CODE)) {
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                    mobileBean.setVerifyCode(code);
                    System.out.println("验证码：" + PrefixCode.API_MOBILE_RESET + mobileRequest.getUserPhone());
                    iRedisService.set(PrefixCode.API_MOBILE_RESET + mobileRequest.getUserPhone(), code, 900);
                } else {
                    mobileBean.setCode(ErrorCode.ERROR);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
                }
            } else if (mobileRequest.getType() == EntityCode.MOBILE_LOGIN_CODE) {
                String code = StringUtil.generateTextCode(0, 6, null);
                //SEND MOBILE
                SmsResponse smsResponse = SendSm.sendSms(mobileRequest.getUserPhone(), messageUtil.getMessage("MSG.SMSSEND.CONTENT").replace("#CODE", code));
                if (smsResponse.getCode().equals(SendSm.SUCCE_CODE)) {
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                    mobileBean.setVerifyCode(code);
                    System.out.println("验证码：" + PrefixCode.API_MOBILE_RESET + mobileRequest.getUserPhone());
                    iRedisService.set(PrefixCode.API_MOBILE_LOGIN_ON + mobileRequest.getUserPhone(), code, 900);
                } else {
                    mobileBean.setCode(ErrorCode.ERROR);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
                }
            } else if (mobileRequest.getType() == EntityCode.MOBILE_BANK_CODE) {
                String code = StringUtil.generateTextCode(0, 6, null);
                //SEND MOBILE
                SmsResponse smsResponse = SendSm.sendSms(mobileRequest.getUserPhone(), messageUtil.getMessage("MSG.SMSSEND.CONTENT").replace("#CODE", code));
                if (smsResponse.getCode().equals(SendSm.SUCCE_CODE)) {
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                    mobileBean.setVerifyCode(code);
                    iRedisService.set(PrefixCode.API_MOBILE_BANK + mobileRequest.getUserPhone(), code, 900);
                } else {
                    mobileBean.setCode(ErrorCode.ERROR);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
                }
            }
        } catch (Exception e) {
            mobileBean.setCode(ErrorCode.ERROR);
            mobileBean.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(mobileBean).toString());
    }

    /**
     * 手机号码方式注册
     */
    @RequestMapping(value = "/reg")
    public void reg(HttpServletRequest request, HttpServletResponse response) {

        RegisterMobileResponse registerMobileResponse = new RegisterMobileResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/reg:" + json);
            ApiHttpUtil.addLog(request.getHeader("user-agent"), null, "UserReg", request.getRequestURL().toString(), json);

            RegisterMobileRequest mobileRequest = JSON.parseObject(json, RegisterMobileRequest.class);
            ApiUserBean apiUserBean = new ApiUserBean();
            //  apiUserBean.setUserName(mobileRequest.getUserPhone());
            apiUserBean.setMobile(mobileRequest.getUserPhone());
            apiUserBean.setPassword(mobileRequest.getPasswd());
            apiUserBean.setDomain(mobileRequest.getDomain());
            //HEADER是否存在
            String header = getPlatformHeader(request);
            if (StringUtil.isEmpty(header)) {
                registerMobileResponse.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                registerMobileResponse.setMsg(messageUtil.getMessage("msg.parameter.notEnough"));
            } else {
//                //判断是否用户名注册
//                UserInfo isExist=iApIUserService.findByUserName(mobileRequest.getUserPhone(),mobileRequest.getDomain());
                //ADD 手机号
                UserInfo userMobile = iApIUserService.findLoad(mobileRequest.getUserPhone(), mobileRequest.getDomain());
                if (userMobile != null) {
                    registerMobileResponse.setCode(ErrorCode.USER_EXITS);
                    registerMobileResponse.setMsg(messageUtil.getMessage("MSG.USER_EXITS_CN"));
                } else {
                    //判断注册码是否有效
                    String code = iRedisService.get(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone());
                    if (code != null && !"".equals(code) && code.equals(mobileRequest.getVerifyCode())) {
                        iApIUserService.saveUser(apiUserBean);
                        UserInfo userInfo = iApIUserService.findLoad(apiUserBean.getMobile(), apiUserBean.getDomain());
                        registerMobileResponse.setCode(ErrorCode.SUCCESS);
                        registerMobileResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));


                        long endTime = +Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")) * 60 * 1000 + System.currentTimeMillis();
                        String token = StringUtil.getBASE64(userInfo.getId() + SplitCode.SPLIT_EQULE + header + SplitCode.SPLIT_SHU + userInfo.getId() + SplitCode.SPLIT_SHU + endTime);

                        //update
                        LoginResponse loginResponse = new LoginResponse();
                        loginResponse.setToken(token);
                        loginResponse.setInvalidTime(Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")));
                        UserResponse userResponse = changeUser(userInfo);
                        loginResponse.setUser(userResponse);

                        registerMobileResponse.setData(loginResponse);
                        if (!StringUtil.isEmpty(header) && header.equals(PrefixCode.API_HEAD_H5)) {
                            iRedisService.setMap(PrefixCode.API_H5_TOKEN_MAP, userInfo.getId().toString(), userResponse);
                        } else {
                            iRedisService.setMap(PrefixCode.API_TOKEN_MAP, userInfo.getId().toString(), userResponse);
                        }

                        iRedisService.deleteOneKey(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone());
                    } else {
                        registerMobileResponse.setCode(ErrorCode.USER_CODE_ERROR);
                        registerMobileResponse.setMsg(messageUtil.getMessage("MSG.USER_CODE_ERROR_CN"));
                    }
                }
            }


        } catch (Exception e) {
            registerMobileResponse.setCode(ErrorCode.ERROR);
            registerMobileResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
    }

    /**
     * 用户名方式注册
     */
    @RequestMapping(value = "/usernameReg")
    public void usernameReg(HttpServletRequest request, HttpServletResponse response) {

        RegisterMobileResponse registerMobileResponse = new RegisterMobileResponse();
        try {

            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/usernameReg:" + json);
            ApiHttpUtil.addLog(request.getHeader("user-agent"), null, "UserReg", request.getRequestURL().toString(), json);

            RegisterMobileRequest mobileRequest = JSON.parseObject(json, RegisterMobileRequest.class);
            ApiUserBean apiUserBean = new ApiUserBean();
            apiUserBean.setMobile(mobileRequest.getUserPhone());
            apiUserBean.setUserName(mobileRequest.getUsername());
            apiUserBean.setPassword(mobileRequest.getPasswd());
            apiUserBean.setDomain(mobileRequest.getDomain());
            //HEADER是否存在
            String header = getPlatformHeader(request);
            if (StringUtil.isEmpty(header)) {
                registerMobileResponse.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                registerMobileResponse.setMsg(messageUtil.getMessage("msg.parameter.notEnough"));
            } else {
//                //判断是否用户名注册
                UserInfo isExist = iApIUserService.findLoad(mobileRequest.getUsername(), mobileRequest.getDomain());
                UserInfo userMobile = iApIUserService.findLoad(mobileRequest.getUserPhone(), mobileRequest.getDomain());
                if (isExist != null||userMobile!=null) {
                    registerMobileResponse.setCode(ErrorCode.USER_EXITS);
                    registerMobileResponse.setMsg(messageUtil.getMessage("MSG.USER_EXITS_CN"));
                } else {
                    iApIUserService.saveUser(apiUserBean);
                    UserInfo userInfo = iApIUserService.findLoad(apiUserBean.getUserName(), apiUserBean.getDomain());
                    registerMobileResponse.setCode(ErrorCode.SUCCESS);
                    registerMobileResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));

                    long endTime = +Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")) * 60 * 1000 + System.currentTimeMillis();
                    String token = StringUtil.getBASE64(userInfo.getId() + SplitCode.SPLIT_EQULE + header + SplitCode.SPLIT_SHU + userInfo.getId() + SplitCode.SPLIT_SHU + endTime);

                    //update
                    LoginResponse loginResponse = new LoginResponse();
                    loginResponse.setToken(token);
                    loginResponse.setInvalidTime(Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")));
                    UserResponse userResponse = changeUser(userInfo);
                    loginResponse.setUser(userResponse);

                    registerMobileResponse.setData(loginResponse);
                    if (!StringUtil.isEmpty(header) && header.equals(PrefixCode.API_HEAD_H5)) {
                        iRedisService.setMap(PrefixCode.API_H5_TOKEN_MAP, userInfo.getId().toString(), userResponse);
                    } else {
                        iRedisService.setMap(PrefixCode.API_TOKEN_MAP, userInfo.getId().toString(), userResponse);
                    }

                }

            }


        } catch (Exception e) {
            registerMobileResponse.setCode(ErrorCode.ERROR);
            registerMobileResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
    }

    /**
     * 业务系统远程帮助用户系统注册
     */
    @RequestMapping(value = "/remoteReg")
    public void remoteReg(HttpServletRequest request, HttpServletResponse response) {

        RegisterMobileResponse registerMobileResponse = new RegisterMobileResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/remoteReg:" + json);
            ApiHttpUtil.addLog(request.getHeader("user-agent"), null, "UserReg", request.getRequestURL().toString(), json);

            RemoteRegReq remoteRegReq = JSON.parseObject(json, RemoteRegReq.class);
            ApiUserBean apiUserBean = new ApiUserBean();
            apiUserBean.setMobile(remoteRegReq.getUserPhone());
            apiUserBean.setPassword(remoteRegReq.getPasswd());
            apiUserBean.setIdCard(remoteRegReq.getIdCard());
            apiUserBean.setDomain(remoteRegReq.getDomain());
            //HEADER是否存在
            String header = getPlatformHeader(request);
            if (StringUtil.isEmpty(header)) {
                registerMobileResponse.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                registerMobileResponse.setMsg(messageUtil.getMessage("msg.parameter.notEnough"));
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
                return;
            }
            if (StringUtil.isEmpty(remoteRegReq.getPasswd()) || (StringUtil.isEmpty(remoteRegReq.getIdCard()) && StringUtil.isEmpty(remoteRegReq.getUserPhone()))) {
                registerMobileResponse.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                registerMobileResponse.setMsg(messageUtil.getMessage("msg.parameter.notEnough"));
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
                return;
            }

            //如果手机号不为空
            if (!StringUtil.isEmpty(remoteRegReq.getUserPhone()) && iApIUserService.findLoad(remoteRegReq.getUserPhone(), remoteRegReq.getDomain()) != null) {
                registerMobileResponse.setCode(ErrorCode.USER_EXITS);
                registerMobileResponse.setMsg(messageUtil.getMessage("MSG.USER_EXITS_CN"));
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
                return;
            }
            //证件号不为空
            if (!StringUtil.isEmpty(remoteRegReq.getIdCard()) && iApIUserService.findLoad(remoteRegReq.getIdCard(), remoteRegReq.getDomain()) != null) {
                registerMobileResponse.setCode(ErrorCode.USER_EXITS);
                registerMobileResponse.setMsg(messageUtil.getMessage("MSG.USER_EXITS_CN"));
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
                return;
            }

            Integer userID = iApIUserService.saveUser(apiUserBean);

            registerMobileResponse.setCode(ErrorCode.SUCCESS);
            registerMobileResponse.setData(userID);
            registerMobileResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
        } catch (Exception e) {
            registerMobileResponse.setCode(ErrorCode.ERROR);
            registerMobileResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
    }

    /**
     * 业务系统远程修改轻校网账号--教师专用
     */
    @RequestMapping(value = "/remoteReg/{id}")
    public void remoteReg(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {

        RegisterMobileResponse registerMobileResponse = new RegisterMobileResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/remoteReg/{id}:" + id);

            RemoteRegReq remoteRegReq = JSON.parseObject(json, RemoteRegReq.class);
            ApiUserBean apiUserBean = new ApiUserBean();
            apiUserBean.setDomain(remoteRegReq.getDomain());
            //HEADER是否存在
            String header = getPlatformHeader(request);
            if (StringUtil.isEmpty(header)) {
                registerMobileResponse.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                registerMobileResponse.setMsg(messageUtil.getMessage("msg.parameter.notEnough"));
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
                return;
            }
            if (StringUtil.isEmpty(remoteRegReq.getDomain())) {
                registerMobileResponse.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                registerMobileResponse.setMsg(messageUtil.getMessage("msg.parameter.notEnough"));
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
                return;
            }

            Integer code = iApIUserService.updateRemoteUser(apiUserBean, id);

            registerMobileResponse.setCode(ErrorCode.SUCCESS);
            registerMobileResponse.setData(code);
            registerMobileResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
        } catch (Exception e) {
            registerMobileResponse.setCode(ErrorCode.ERROR);
            registerMobileResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
    }

    /**
     * 业务系统远程修改轻校网账号--教师专用
     */
    @RequestMapping(value = "/user/{domain}/statistics")
    public void userStatistics(@PathVariable("domain") String domain, @RequestParam("dateStr") String dateStr, HttpServletRequest request, HttpServletResponse response) {

        RegisterMobileResponse registerMobileResponse = new RegisterMobileResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/user/{domain}/statistics:" + domain);

            //HEADER是否存在
            String header = getPlatformHeader(request);
            if (StringUtil.isEmpty(header)) {
                registerMobileResponse.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                registerMobileResponse.setMsg(messageUtil.getMessage("msg.parameter.notEnough"));
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
                return;
            }

            UserStatisticsResponse userStatisticsResponse = iApIUserService.staticUserDomain(domain, dateStr);

            registerMobileResponse.setCode(ErrorCode.SUCCESS);
            registerMobileResponse.setData(userStatisticsResponse);
            registerMobileResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
        } catch (Exception e) {
            registerMobileResponse.setCode(ErrorCode.ERROR);
            registerMobileResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
    }


    /**
     * login
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            //判断mobile死否为空
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/login:" + json);
            ApiHttpUtil.addLog(request.getHeader("user-agent"), null, "UserLogin", request.getRequestURL().toString(), json);
            LoginRequest loginRequest = JSON.parseObject(json, LoginRequest.class);
            UserInfo userInfo = null;
            //兼容旧的app api请求
            if (!StringUtil.isEmpty(loginRequest.getUserPhone())) {
                userInfo = iApIUserService.findLoad(loginRequest.getUserPhone(), loginRequest.getDomain());
            } else if (!StringUtil.isEmpty(loginRequest.getUserName())) {
                userInfo = iApIUserService.findLoad(loginRequest.getUserName(), loginRequest.getDomain());
            } else if (!StringUtil.isEmpty(loginRequest.getUserEmail())) {
                userInfo = iApIUserService.findLoad(loginRequest.getUserEmail(), loginRequest.getDomain());
            } else if (!StringUtil.isEmpty(loginRequest.getIdCard())) {
                userInfo = iApIUserService.findLoad(loginRequest.getIdCard(), loginRequest.getDomain());
            }

            //获取请求头
            String header = getPlatformHeader(request);
            if (userInfo != null) {
                //用户登录支持验证码登录,默认是密码登录
                if (!StringUtil.isEmpty(loginRequest.getUserPassword())) {
                    if (StringUtil.getMD5Str(loginRequest.getUserPassword()).equals(userInfo.getPassword())) {
                        long endTime = +Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")) * 60 * 1000 + System.currentTimeMillis();
                        // System.out.println("-------"+userInfo.getId()+SplitCode.SPLIT_EQULE+header+SplitCode.SPLIT_SHU+userInfo.getId() + SplitCode.SPLIT_SHU + endTime);
                        String token = StringUtil.getBASE64(userInfo.getId() + SplitCode.SPLIT_EQULE + header + SplitCode.SPLIT_SHU + userInfo.getId() + SplitCode.SPLIT_SHU + endTime);
                        //更新数据库token保存做备份,不做数据库备份
                        iApIUserService.updateUserTime(userInfo.getId());

                        LoginResponse loginResponse = new LoginResponse();
                        loginResponse.setToken(token);
                        loginResponse.setInvalidTime(Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")));
                        UserResponse userResponse = changeUser(userInfo);
                        loginResponse.setUser(userResponse);

                        commonResponse.setCode(ErrorCode.SUCCESS);
                        commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                        commonResponse.setData(loginResponse);

                        if (!StringUtil.isEmpty(header) && header.equals(PrefixCode.API_HEAD_H5)) {
                            iRedisService.setMap(PrefixCode.API_H5_TOKEN_MAP, userInfo.getId().toString(), userResponse);
                        } else if (!StringUtil.isEmpty(header) && header.equals(PrefixCode.API_HEAD_WEB)) {
                            iRedisService.setObject(PrefixCode.API_COOKIE_PRE + userInfo.getId() + SplitCode.SPLIT_EQULE + token, changeUser(userInfo), Integer.parseInt(PropertiesUtil.getString("sso.ticketTimeout")) * 60);
                        } else {
                            iRedisService.setMap(PrefixCode.API_TOKEN_MAP, userInfo.getId().toString(), userResponse);
                        }
                    } else {
                        commonResponse.setCode(ErrorCode.USER_PWD_ERROR);
                        commonResponse.setMsg(messageUtil.getMessage("MSG.USER_PWD_ERROR_CN"));
                    }
                } else if (!StringUtil.isEmpty(loginRequest.getCode())) {
                    if (iRedisService.get(PrefixCode.API_MOBILE_LOGIN_ON + userInfo.getMobile()) == null || !iRedisService.get(PrefixCode.API_MOBILE_LOGIN_ON + userInfo.getMobile()).equals(loginRequest.getCode())) {
                        commonResponse.setCode(ErrorCode.USER_CODE_ERROR);
                        commonResponse.setMsg(messageUtil.getMessage("MSG.USER_CODE_ERROR_CN"));
                    } else {
                        if (userInfo.getStatus() == EntityCode.USER_NEED_ACTIVE) {
                            commonResponse.setCode(ErrorCode.USER_NEED_ACTIVE);
                            commonResponse.setMsg(messageUtil.getMessage("MSG.USER_ACTIVE_ERROR_CN"));
                        } else {
                            iRedisService.deleteOneKey(PrefixCode.API_MOBILE_LOGIN_ON + userInfo.getMobile());

                            long endTime = +Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")) * 60 * 1000 + System.currentTimeMillis();
                            String token = StringUtil.getBASE64(userInfo.getId() + SplitCode.SPLIT_EQULE + header + SplitCode.SPLIT_SHU + userInfo.getId() + SplitCode.SPLIT_SHU + endTime);
                            //更新数据库token保存做备份,不做数据库备份
                            iApIUserService.updateUserTime(userInfo.getId());

                            LoginResponse loginResponse = new LoginResponse();
                            loginResponse.setToken(token);
                            loginResponse.setInvalidTime(Long.parseLong(PropertiesUtil.getString("TOKEN.TIME")));
                            UserResponse userResponse = changeUser(userInfo);
                            loginResponse.setUser(userResponse);

                            commonResponse.setCode(ErrorCode.SUCCESS);
                            commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                            commonResponse.setData(loginResponse);

                            if (!StringUtil.isEmpty(header) && header.equals(PrefixCode.API_HEAD_H5)) {
                                iRedisService.setMap(PrefixCode.API_H5_TOKEN_MAP, userInfo.getId().toString(), userResponse);
                            } else if (!StringUtil.isEmpty(header) && header.equals(PrefixCode.API_HEAD_WEB)) {
                                iRedisService.setObject(PrefixCode.API_COOKIE_PRE + StringUtil.getBASE64(userInfo.getId() + SplitCode.SPLIT_EQULE + token), changeUser(userInfo), Integer.parseInt(PropertiesUtil.getString("sso.ticketTimeout")) * 60);
                            } else {
                                iRedisService.setMap(PrefixCode.API_TOKEN_MAP, userInfo.getId().toString(), userResponse);
                            }
                        }

                    }
                } else {
                    commonResponse.setCode(ErrorCode.PARAMETER_NOT_ENOUGH);
                    commonResponse.setMsg(messageUtil.getMessage("msg.parameter.notEnough"));
                }
            } else {
                commonResponse.setCode(ErrorCode.USER_NOT_EXITS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.USER_NOT_EXITS_CN"));
            }
        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(messageUtil.getMessage("msg.process.fail"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }


    /**
     * up 上传
     */
    @RequestMapping(value = "/up", method = RequestMethod.POST)
    public void up(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        CommonResponse message = new CommonResponse();
        try {
            //现在用户名和手机号一样，直接查找手机号
            //  String[] auth=getAuthHeader(request);
            //  StudyLogger.recBusinessLog("/pub/up:" + auth.toString());
            if (!file.isEmpty()) {
                ServletContext sc = request.getSession().getServletContext();
                String dir = sc.getRealPath(PrefixCode.FILE_PATH);

                String filename = file.getOriginalFilename();


                long _lTime = System.nanoTime();
                String _ext = filename.substring(filename.lastIndexOf("."));
                filename = _lTime + _ext;

                FileUtils.writeByteArrayToFile(new File(dir, filename), file.getBytes());
                message.setData(dir + File.separator + filename);
                message.setCode(ErrorCode.SUCCESS);
                message.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
            } else {
                message.setCode(ErrorCode.ERROR);
                message.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            }

        } catch (Exception e) {
            message.setCode(ErrorCode.ERROR);
            message.setMsg(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    /**
     * 图片处理 上传
     */
    @RequestMapping(value = "/up/img/{file:.*}")
    public void img(@PathVariable("file") String name, @RequestParam String w, @RequestParam String h, HttpServletRequest request, HttpServletResponse response) {
        try {
            StudyLogger.recBusinessLog("/up/img/" + name);
            byte[] img = ImageUtil.resizeOUT(new File(request.getSession().getServletContext().getRealPath(PrefixCode.FILE_PATH), name), Integer.parseInt(w), Integer.parseInt(h), 1f, true);

            response.setContentType("image/jpeg");
            OutputStream stream = response.getOutputStream();
            stream.write(img);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            printLogger(e);
        }
    }

}
