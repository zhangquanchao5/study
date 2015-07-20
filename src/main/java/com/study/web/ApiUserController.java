package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.LoginBean;
import com.study.common.apibean.request.*;
import com.study.common.apibean.response.CommonResponse;
import com.study.common.apibean.response.UserInfoUpdateResponse;
import com.study.common.util.MessageUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.model.Account;
import com.study.model.UserInfo;
import com.study.service.IApIUserService;
import com.study.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

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
            String json=this.getParameter(request);
            StudyLogger.recBusinessLog("/user/resetPwd:" + json);

            PwdResetRequest pwdResetRequest= JSON.parseObject(json, PwdResetRequest.class);

            //判断注册码是否有效
            String code=iRedisService.get(PrefixCode.API_MOBILE_UPDATE + pwdResetRequest.getUserPhone());
            if(code!=null&&!"".equals(code)&&code.equals(pwdResetRequest.getVerifyCode())){
                iApIUserService.updateUserPwd(pwdResetRequest);
                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                iRedisService.deleteOneKey(PrefixCode.API_MOBILE_UPDATE + pwdResetRequest.getUserPhone());
            }else{
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
            if(isAuthToken(iRedisService,request)){
                String json=this.getParameter(request);
                StudyLogger.recBusinessLog("/user/updateInfo:" + json);

                UserInfoUpdateRequest userInfoUpdateRequest= JSON.parseObject(json, UserInfoUpdateRequest.class);
                UserInfo updateUser=new UserInfo();
                updateUser.setNick(userInfoUpdateRequest.getNickname());
                updateUser.setIcon(userInfoUpdateRequest.getIcon());
                updateUser.setAddress(userInfoUpdateRequest.getAddress());
                updateUser.setName(userInfoUpdateRequest.getRealName());
                if(userInfoUpdateRequest.getGender()!=null&&!"".equals(userInfoUpdateRequest.getGender())){
                    updateUser.setGender(Byte.parseByte(userInfoUpdateRequest.getGender()));
                }
                updateUser.setId(userInfoUpdateRequest.getId());

                iApIUserService.updateUser(updateUser);

                UserInfo userInfo=iApIUserService.findById(updateUser.getId());
                userInfoUpdateResponse.setCode(ErrorCode.SUCCESS);
                userInfoUpdateResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                userInfoUpdateResponse.setData(userInfo);
            }else{
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
     * 获取用户信息
     */
    @RequestMapping(value = "/info")
    public void info(HttpServletRequest request, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            if(isAuthToken(iRedisService,request)){
                String json=this.getParameter(request);
                StudyLogger.recBusinessLog("/user/info:" + json);

                UserInfoRequest userInfoRequest= JSON.parseObject(json, UserInfoRequest.class);
                UserInfo userInfo=null;
                if(userInfoRequest.getId()!=null){
                    userInfo=iApIUserService.findById(userInfoRequest.getId());
                }else{
                    userInfo=iApIUserService.findByToken(userInfoRequest.getToken());
                }
                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                commonResponse.setData(userInfo);
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
            if(isAuthToken(iRedisService, request)){
                String json=this.getParameter(request);
                StudyLogger.recBusinessLog("/user/modifyPwd:" + json);

                UserPwdChangeRequest userPwdChangeRequest= JSON.parseObject(json, UserPwdChangeRequest.class);
                UserInfo userInfo=iApIUserService.findById(userPwdChangeRequest.getId());
                if(userInfo.getPassword().equals(StringUtil.getMD5Str(userPwdChangeRequest.getOldPassword()))){
                    UserInfo userInfoUpdate=new UserInfo();
                    userInfoUpdate.setId(userInfo.getId());
                    userInfoUpdate.setPassword(StringUtil.getMD5Str(userPwdChangeRequest.getNewPassword()));

                    iApIUserService.updateUser(userInfoUpdate);
                    commonResponse.setCode(ErrorCode.SUCCESS);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                }else{
                    commonResponse.setCode(ErrorCode.USER_PWD_ERROR);
                    commonResponse.setMsg(messageUtil.getMessage("MSG.USER_PWD_ERROR_CN"));
                }
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
     * 用户绑定手机
     */
    @RequestMapping(value = "/changeMobile")
    public void changeMobile(HttpServletRequest request, HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            String json=this.getParameter(request);
            StudyLogger.recBusinessLog("/user/changeMobile:" + json);

            UserBindRequest userBindRequest= JSON.parseObject(json, UserBindRequest.class);

            //判断注册码是否有效
            String code=iRedisService.get(PrefixCode.API_MOBILE_BIND + userBindRequest.getUserPhone());
            if(code!=null&&!"".equals(code)&&code.equals(userBindRequest.getVerifyCode())){
                UserInfo userUpdate=new UserInfo();
                userUpdate.setId(userBindRequest.getId());
                userUpdate.setMobile(userBindRequest.getUserPhone());
                userUpdate.setPassword(StringUtil.getMD5Str(userBindRequest.getNewPassword()));

                iApIUserService.updateUser(userUpdate);
                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                iRedisService.deleteOneKey(PrefixCode.API_MOBILE_UPDATE + userBindRequest.getUserPhone());
            }else{
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

}
