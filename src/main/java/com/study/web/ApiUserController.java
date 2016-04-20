package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.code.SplitCode;
import com.study.common.Encrypt;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.apibean.AuthHeaderBean;
import com.study.common.apibean.request.*;
import com.study.common.apibean.response.CommonResponse;
import com.study.common.apibean.response.UserInfoUpdateResponse;
import com.study.common.apibean.response.UserResponse;
import com.study.common.apibean.response.ValidateResponse;
import com.study.common.oss.DESUtils;
import com.study.common.page.UserPageRequest;
import com.study.common.page.UserPageResponse;
import com.study.common.util.MessageUtil;
import com.study.common.util.PropertiesUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.service.IApIUserService;
import com.study.service.IRedisService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huichao on 2015/7/7.
 */
@Controller
@RequestMapping("/api/user")
public class ApiUserController extends BaseController {

    @Autowired
    private IApIUserService iApIUserService;
    @Autowired
    private IRedisService iRedisService;
    @Autowired
    private MessageUtil messageUtil;

//    /**
//     * Register save.
//     */
//    @RequestMapping(value = "/user",method = RequestMethod.POST)
//    public void registerSave(@RequestBody ApiUserBean apiUserBean, HttpServletResponse response) {
//
//        ApiResponseMessage message = new ApiResponseMessage();
//        try {
//
//            UserInfo userInfo = iApIUserService.findByMobile(apiUserBean.getMobile());
//            if (userInfo != null) {
//                message.setCode(ErrorCode.ERROR);
//                message.setMsg(ErrorCode.USER_EXITS);
//                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
//                return;
//            }
//            iApIUserService.saveUser(apiUserBean);
//
//            message.setCode(ErrorCode.SUCCESS);
//        } catch (Exception e) {
//            message.setCode(ErrorCode.ERROR);
//            message.setMsg(ErrorCode.SYS_ERROR);
//            printLogger(e);
//        }
//        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
//    }


//
//    @RequestMapping("/tokenVerify/{userId}/{token}")
//    public void tokenVerify(@PathVariable("userId") Integer userId, @PathVariable("token") String token,HttpServletResponse response) {
//        ApiResponseMessage message = new ApiResponseMessage();
//        try {
//            Object obj=iRedisService.getObjectFromMap(PrefixCode.API_TOKEN_MAP, userId.toString());
//            if(obj==null){
//                message.setCode(ErrorCode.ERROR);
//            }else{
//                String tokenRedis=(String)obj;
//                if(!token.equals(tokenRedis)){
//                    message.setCode(ErrorCode.ERROR);
//                    message.setMsg(ErrorCode.USER_TOKEN_NO_VAL);
//                }
//            }
//            message.setCode(ErrorCode.SUCCESS);
//        } catch (Exception e) {
//            message.setCode(ErrorCode.ERROR);
//            message.setMsg(ErrorCode.SYS_ERROR);
//            printLogger(e);
//        }
//        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
//    }

    /**
     * 手机号码方式重置密码
     */
    @RequestMapping(value = "/resetPwd")
    public void resetPwd(HttpServletRequest request, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/user/resetPwd:" + json);

            PwdResetRequest pwdResetRequest = JSON.parseObject(json, PwdResetRequest.class);

            //判断注册码是否有效
            String code = iRedisService.get(PrefixCode.API_MOBILE_UPDATE + pwdResetRequest.getUserPhone());
            if (code != null && !"".equals(code) && code.equals(pwdResetRequest.getVerifyCode())) {
                iApIUserService.updateUserPwd(pwdResetRequest);
                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                iRedisService.deleteOneKey(PrefixCode.API_MOBILE_UPDATE + pwdResetRequest.getUserPhone());
            } else {
                commonResponse.setCode(ErrorCode.USER_CODE_ERROR);
                commonResponse.setMsg(messageUtil.getMessage("MSG.USER_CODE_ERROR_CN"));
            }
        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

    /**
     * 用户信息修改
     */
    @RequestMapping(value = "/updateInfo")
    public void updateInfo(HttpServletRequest request, HttpServletResponse response) {

        UserInfoUpdateResponse userInfoUpdateResponse = new UserInfoUpdateResponse();
        try {
            if (isAuthToken(iRedisService, request)) {
                String json = this.getParameter(request);
                StudyLogger.recBusinessLog("/user/updateInfo:" + json);

                UserInfoUpdateRequest userInfoUpdateRequest = JSON.parseObject(json, UserInfoUpdateRequest.class);
                UserInfo updateUser = new UserInfo();
                updateUser.setNick(userInfoUpdateRequest.getNickname());
                updateUser.setIcon(userInfoUpdateRequest.getIcon());
                updateUser.setAddress(userInfoUpdateRequest.getAddress());
                updateUser.setName(userInfoUpdateRequest.getRealName());
                if (userInfoUpdateRequest.getGender() != null && !"".equals(userInfoUpdateRequest.getGender())) {
                    updateUser.setGender(Byte.parseByte(userInfoUpdateRequest.getGender()));
                }
                updateUser.setId(userInfoUpdateRequest.getId());

                iApIUserService.updateUser(updateUser);

                UserInfo userInfo = iApIUserService.findById(updateUser.getId());
                userInfoUpdateResponse.setCode(ErrorCode.SUCCESS);
                userInfoUpdateResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                userInfoUpdateResponse.setData(changeUser(userInfo));

                //获取请求头
                String header=getPlatformHeader(request);
                String encode= StringUtil.getFromBASE64( request.getHeader("Authorization"));

                //System.out.println("go:"+header+"["+encode+"]");
                if(!StringUtil.isEmpty(header)&&header.equals(PrefixCode.API_HEAD_H5)){
                    iRedisService.setMap(PrefixCode.API_H5_TOKEN_MAP, userInfo.getId().toString(), userInfoUpdateResponse.getData());
                } else if(!StringUtil.isEmpty(header)&&header.equals(PrefixCode.API_HEAD_WEB)) {
                    //System.out.println("go:2");
                    iRedisService.setObject(PrefixCode.API_COOKIE_PRE + encode,  userInfoUpdateResponse.getData(), Integer.parseInt(PropertiesUtil.getString("sso.ticketTimeout")) * 60);
                } else{
                    iRedisService.setMap(PrefixCode.API_TOKEN_MAP, userInfo.getId().toString(),  userInfoUpdateResponse.getData());
                }

            } else {
                userInfoUpdateResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                userInfoUpdateResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }

        } catch (Exception e) {
            userInfoUpdateResponse.setCode(ErrorCode.ERROR);
            userInfoUpdateResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(userInfoUpdateResponse).toString());
    }

    /**
     * 用户信息校验
     */
    @RequestMapping(value = "/validate")
    public void validate(HttpServletRequest request, HttpServletResponse response) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/user/validate:" + json);
            UserResponse userInfo = null;

            UserInfoRequest userInfoRequest = JSON.parseObject(json, UserInfoRequest.class);
            if(isAuthToken(iRedisService, request)){
                String encode=StringUtil.getFromBASE64(userInfoRequest.getAuth_token());
                String token=StringUtil.getFromBASE64(encode.substring(encode.split(SplitCode.SPLIT_EQULE)[0].length() + 1, encode.length()));
                String []head=token.substring(token.split(SplitCode.SPLIT_EQULE)[0].length()+1,token.length()).split(SplitCode.SPLIT_ZHUANYI);
                //String []head=StringUtil.getFromBASE64(encode.split(SplitCode.SPLIT_EQULE)[1]).split(SplitCode.SPLIT_EQULE)[1].split(SplitCode.SPLIT_ZHUANYI);
                StudyLogger.recBusinessLog("/user/validate params:" + head[0]+"#######"+token);
                if(head[0].equals(PrefixCode.API_HEAD_H5)){
                    userInfo= (UserResponse)iRedisService.getObjectFromMap(PrefixCode.API_H5_TOKEN_MAP, encode.split(SplitCode.SPLIT_EQULE)[0]);
                }else if(head[0].equals(PrefixCode.API_HEAD_WEB)){
                    userInfo=(UserResponse)iRedisService.getObject(PrefixCode.API_COOKIE_PRE+encode);
                }else{
                    userInfo= (UserResponse)iRedisService.getObjectFromMap(PrefixCode.API_TOKEN_MAP, encode.split(SplitCode.SPLIT_EQULE)[0]);
                }

                ValidateResponse validateResponse = new ValidateResponse();
                if (userInfo != null) {
                    validateResponse.setResult(true);
                    validateResponse.setInfo(userInfo);
                } else {
                    validateResponse.setResult(false);
                }

                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                commonResponse.setData(validateResponse);
            }else{
                commonResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                commonResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }
        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/info")
    public void info(HttpServletRequest request, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            if (isAuthToken(iRedisService, request)) {
                String json = this.getParameter(request);
                StudyLogger.recBusinessLog("/user/info:" + json);

                UserInfoRequest userInfoRequest = JSON.parseObject(json, UserInfoRequest.class);
                UserInfo userInfo = null;
                if (userInfoRequest.getId() != null) {
                    userInfo = iApIUserService.findById(userInfoRequest.getId());
                }else{
                    String code=StringUtil.getFromBASE64(userInfoRequest.getToken());
                    userInfo = iApIUserService.findById(Integer.parseInt(code.split(SplitCode.SPLIT_EQULE)[0]));
                }

                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                commonResponse.setData(changeUser(userInfo));
            } else {
                commonResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                commonResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }

        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

//    /**
//     * 获取账户信息
//     */
//    @RequestMapping(value = "/accountinfo")
//    public void accountinfo(HttpServletRequest request, HttpServletResponse response) {
//
//        CommonResponse commonResponse = new CommonResponse();
//        try {
//            if(isAuthToken(iRedisService,request)){
//                String json=this.getParameter(request);
//                StudyLogger.recBusinessLog("/user/accountinfo:" + json);
//
//                UserInfoRequest userInfoRequest= JSON.parseObject(json, UserInfoRequest.class);
//
//                Account account=iApIUserService.findAccountByUserId(userInfoRequest.getId());
//                commonResponse.setCode(ErrorCode.SUCCESS);
//                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
//                commonResponse.setData(account);
//            }else{
//                commonResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
//                commonResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
//            }
//
//        } catch (Exception e) {
//            commonResponse.setCode(ErrorCode.ERROR);
//            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
//            printLogger(e);
//        }
//        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
//    }

    /**
     * 用户修改密码
     */
    @RequestMapping(value = "/modifyPwd")
    public void modifyPwd(HttpServletRequest request, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            if (isAuthToken(iRedisService, request)) {
                String json = this.getParameter(request);
                StudyLogger.recBusinessLog("/user/modifyPwd:" + json);

                UserPwdChangeRequest userPwdChangeRequest = JSON.parseObject(json, UserPwdChangeRequest.class);
                UserInfo userInfo = iApIUserService.findById(userPwdChangeRequest.getId());
                if (userInfo.getPassword().equals(StringUtil.getMD5Str(userPwdChangeRequest.getOldPassword()))) {
                    UserInfo userInfoUpdate = new UserInfo();
                    userInfoUpdate.setId(userInfo.getId());
                    userInfoUpdate.setPassword(StringUtil.getMD5Str(userPwdChangeRequest.getNewPassword()));

                    iApIUserService.updateUser(userInfoUpdate);
                    commonResponse.setCode(ErrorCode.SUCCESS);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                } else {
                    commonResponse.setCode(ErrorCode.USER_PWD_ERROR);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.USER_PWD_ERROR_CN"));
                }
            } else {
                commonResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                commonResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }


        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

    /**
     * 用户信息查询
     * @param request
     * @param response
     */
    @RequestMapping(value = "/search" ,method = RequestMethod.GET)
    public void search ( UserPageRequest userPageRequest,HttpServletRequest request, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            if (isAuthToken(iRedisService, request)) {
                String json = this.getParameter(request);
                StudyLogger.recBusinessLog("/user/search:" + JSON.toJSONString(userPageRequest));

                //UserPageRequest userPageRequest = JSON.parseObject(json, UserPageRequest.class);

                UserPageResponse userPageResponse = iApIUserService.findPageResponse(userPageRequest);
                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                commonResponse.setData(userPageResponse);
            }else {
                commonResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                commonResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
            }
        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }


    /**
     * 用户绑定手机
     */
    @RequestMapping(value = "/changeMobile")
    public void changeMobile(HttpServletRequest request, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/user/changeMobile:" + json);

            UserBindRequest userBindRequest = JSON.parseObject(json, UserBindRequest.class);

            //判断注册码是否有效
            String code = iRedisService.get(PrefixCode.API_MOBILE_BIND + userBindRequest.getUserPhone());
            if (code != null && !"".equals(code) && code.equals(userBindRequest.getVerifyCode())) {
                UserInfo userUpdate = new UserInfo();
                userUpdate.setId(userBindRequest.getId());
                userUpdate.setMobile(userBindRequest.getUserPhone());
                if(!StringUtil.isEmpty(userBindRequest.getNewPassword())){
                    userUpdate.setPassword(StringUtil.getMD5Str(userBindRequest.getNewPassword()));
                }

                iApIUserService.updateUser(userUpdate);
                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                iRedisService.deleteOneKey(PrefixCode.API_MOBILE_UPDATE + userBindRequest.getUserPhone());
            } else {
                commonResponse.setCode(ErrorCode.USER_CODE_ERROR);
                commonResponse.setMsg(messageUtil.getMessage("MSG.USER_CODE_ERROR_CN"));
            }

        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

    @RequestMapping("/mail")
    public void mail(@RequestParam String uid, @RequestParam String mid, @RequestParam String sid, @RequestParam Integer sit, HttpServletResponse response, HttpServletRequest request) {
        CommonResponse commonResponse = new CommonResponse();
        try {
                Object obj = iRedisService.get(PrefixCode.API_MAIL_CONNACT + Encrypt.doDecrypt(uid));
                if (sit == EntityCode.EMAIL_ACTIVE) {
                    if (obj == null) {
                        commonResponse.setCode(ErrorCode.ERROR);
                        commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
                    } else {
                        if (obj.toString().equals(sid)) {
                            UserInfo userInfo = new UserInfo();
                            userInfo.setId(Integer.parseInt(Encrypt.doDecrypt(uid)));
                            userInfo.setUserMail(Encrypt.doDecrypt(mid));

                            iApIUserService.updateUser(userInfo);
                            commonResponse.setCode(ErrorCode.SUCCESS);
                            commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));

                            iRedisService.deleteOneKey(PrefixCode.API_MAIL_CONNACT + Encrypt.doDecrypt(uid));
                        } else {
                            commonResponse.setCode(ErrorCode.ERROR);
                            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
                        }
                    }
                } else if (sit == EntityCode.EMAIL_PWD) {
                    if (obj == null) {
                        commonResponse.setCode(ErrorCode.ERROR);
                        commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
                    } else {
                        if (obj.toString().equals(sid)) {
                            UserInfo u=iApIUserService.findByEMail(Encrypt.doDecrypt(mid));
                            if(u!=null){
                                UserInfo userInfo = new UserInfo();
                                userInfo.setId(Integer.parseInt(Encrypt.doDecrypt(uid)));
                                userInfo.setPassword(StringUtil.getMD5Str("000000"));

                                iApIUserService.updateUser(userInfo);
                                commonResponse.setCode(ErrorCode.SUCCESS);
                                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                                iRedisService.deleteOneKey(PrefixCode.API_MAIL_CONNACT + Encrypt.doDecrypt(uid));
                            } else {
                                commonResponse.setCode(ErrorCode.ERROR);
                                commonResponse.setMsg(ErrorCode.USER_NOT_EXITS);
                            }
                        } else {
                            commonResponse.setCode(ErrorCode.ERROR);
                            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
                        }
                    }
                } else {
                    commonResponse.setCode(ErrorCode.ERROR);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
                }

        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.SYS_ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.SYS_ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

    /**
     * sit=1 重置密码 2激活邮箱
     * 重置密码
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/resetPwdByEmail")
    public void resetPwdByEmail(HttpServletResponse response, HttpServletRequest request) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/user/resetPwdByEmail:" + json);

            EmailRequest emailRequest = JSON.parseObject(json, EmailRequest.class);

            if (getPlatformHeader(request).equals(PrefixCode.API_HEAD_WEB)) {
                if (isAuthToken(iRedisService, request)) {
                    String auth = request.getHeader("Authorization");
                    String decodedTicket = DESUtils.decrypt(auth, PropertiesUtil.getString("sso.secretKey"));
                    Integer userId = ((UserResponse)iRedisService.getObject(PrefixCode.API_COOKIE_PRE + decodedTicket)).getId();

                    UserInfo userInfo = iApIUserService.findByEMail(emailRequest.getEmail());
                    if (userInfo != null) {
                        if (userInfo.getId().intValue() == userId.intValue()) {
                            String random = RandomStringUtils.randomAlphanumeric(32);
                            String actUrl = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.VERTIFY.URL") +
                                    "api/user/mail?uid=" + Encrypt.doEncrypt(String.valueOf(userId)) +
                                    "&mid=" + Encrypt.doEncrypt(emailRequest.getEmail()) +
                                    "&sid=" + random +
                                    "&sit=" + EntityCode.EMAIL_PWD;
                            String message = messageUtil.getMessage("MSG.EMAIL.PWD.SEND").replace("#acturl", actUrl);
                            String subject = messageUtil.getMessage("MSG.EMAIL.PWD.SEND.SUBJECT");
                            String sendFrom = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.ACCOUNT");
                            String mailPwd = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.PASSWORD");
                            String nick = "SYS USER ADMIN";

                            iApIUserService.sendEmail(message, subject, emailRequest.getEmail(), sendFrom, nick, mailPwd);

                            iRedisService.set(PrefixCode.API_MAIL_CONNACT + userId, random, 30 * 60);
                            commonResponse.setCode(ErrorCode.SUCCESS);
                            commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                        } else {
                            commonResponse.setCode(ErrorCode.ERROR);
                            commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
                        }
                    } else {
                        commonResponse.setCode(ErrorCode.ERROR);
                        commonResponse.setMsg(ErrorCode.USER_NOT_EXITS);
                    }
                } else {
                    commonResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
                }
            } else {
                commonResponse.setCode(ErrorCode.ERROR);
                commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            }
        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.SYS_ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.SYS_ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

    /**
     * 激活邮箱
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/activeEmail")
    public void activeEmail( HttpServletResponse response, HttpServletRequest request) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/user/resetPwdByEmail:" + json);

            EmailRequest emailRequest = JSON.parseObject(json, EmailRequest.class);

            if (getPlatformHeader(request).equals(PrefixCode.API_HEAD_WEB)) {
                if (isAuthToken(iRedisService, request)) {
                    String auth = request.getHeader("Authorization");
                    String decodedTicket = DESUtils.decrypt(auth, PropertiesUtil.getString("sso.secretKey"));
                    Integer userId = ((UserResponse)iRedisService.getObject(PrefixCode.API_COOKIE_PRE + decodedTicket)).getId();

                    String random = RandomStringUtils.randomAlphanumeric(32);
                    String actUrl = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.VERTIFY.URL") +
                            "api/user/mail?uid=" + Encrypt.doEncrypt(String.valueOf(userId)) +
                            "&mid=" + Encrypt.doEncrypt(emailRequest.getEmail()) +
                            "&sid=" + random +
                            "&sit=" + EntityCode.EMAIL_ACTIVE;
                    String message = messageUtil.getMessage("MSG.EMAIL.ACTIVE.SEND").replace("#acturl", actUrl);
                    String subject = messageUtil.getMessage("MSG.EMAIL.ACTIVE.SEND.SUBJECT");
                    String sendFrom = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.ACCOUNT");
                    String mailPwd = PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.PASSWORD");
                    String nick = "SYS USER ADMIN";

                    iApIUserService.sendEmail(message, subject, emailRequest.getEmail(), sendFrom, nick, mailPwd);
                    iRedisService.set(PrefixCode.API_MAIL_CONNACT + userId, random, Integer.parseInt(PropertiesUtil.getString("MAIL.PASSWORD.RECOVER.TIMEOUT")) * 60);

                    commonResponse.setCode(ErrorCode.SUCCESS);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                } else {
                    commonResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
                }
            } else {
                commonResponse.setCode(ErrorCode.ERROR);
                commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            }
        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.SYS_ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.SYS_ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }

    /**
     * 修改邮箱
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/changeEmail")
    public void updateEmail(HttpServletResponse response, HttpServletRequest request) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            String json = this.getParameter(request);
            StudyLogger.recBusinessLog("/user/resetPwdByEmail:" + json);

            EmailRequest emailRequest = JSON.parseObject(json, EmailRequest.class);
            if (getPlatformHeader(request).equals(PrefixCode.API_HEAD_WEB)) {
                if (isAuthToken(iRedisService, request)) {
                    String auth = request.getHeader("Authorization");
                    String decodedTicket = DESUtils.decrypt(auth, PropertiesUtil.getString("sso.secretKey"));
                    Integer userId = ((UserResponse)iRedisService.getObject(PrefixCode.API_COOKIE_PRE + decodedTicket)).getId();
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(userId);
                    userInfo.setUserMail(emailRequest.getEmail());

                    iApIUserService.updateUser(userInfo);

                    commonResponse.setCode(ErrorCode.SUCCESS);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                } else {
                    commonResponse.setCode(ErrorCode.USER_TOKEN_NO_VAL);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.USER_TOKEN_NO_VAL_CN"));
                }
            } else {
                commonResponse.setCode(ErrorCode.ERROR);
                commonResponse.setMsg(messageUtil.getMessage("MSG.ERROR_CN"));
            }
        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.SYS_ERROR);
            commonResponse.setMsg(messageUtil.getMessage("MSG.SYS_ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(commonResponse).toString());
    }


    /**
     * loginout
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public void logout(HttpServletRequest request,HttpServletResponse response) {

        CommonResponse message = new CommonResponse();
        try {
            //现在用户名和手机号一样，直接查找手机号
            AuthHeaderBean authHeaderBean = getAuthHeader(request);
            StudyLogger.recBusinessLog("/user/logout:" + authHeaderBean.toString());

            if(getPlatformHeader(request).equals(PrefixCode.API_HEAD_H5)){
                iRedisService.deleteObjectFromMap(PrefixCode.API_H5_TOKEN_MAP, authHeaderBean.getUserId().toString());
            }else  if(getPlatformHeader(request).equals(PrefixCode.API_HEAD_WEB)){
                iRedisService.deleteOneKey(PrefixCode.API_COOKIE_PRE+authHeaderBean.getEncode());
                Cookie cookied = new Cookie(PropertiesUtil.getString("sso.cookieName"), authHeaderBean.getEncode());
                cookied.setSecure(Boolean.parseBoolean(PropertiesUtil.getString("sso.secure")));// 为true时用于https
                cookied.setMaxAge(0);
                cookied.setDomain(PropertiesUtil.getString("sso.domainName"));
                cookied.setPath("/");
                response.addCookie(cookied);
            }else{
                iRedisService.deleteObjectFromMap(PrefixCode.API_TOKEN_MAP, authHeaderBean.getUserId().toString());
            }

            message.setCode(ErrorCode.SUCCESS);
            message.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
           // message.setData(changeUser(iApIUserService.findById(authHeaderBean.getUserId())));
        } catch (Exception e) {
            message.setCode(ErrorCode.ERROR);
            message.setMsg(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }



}
