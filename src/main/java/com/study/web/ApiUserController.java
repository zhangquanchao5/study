package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.common.StringUtil;
import com.study.common.apibean.ApiResponseMessage;
import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.LoginBean;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.service.IApIUserService;
import com.study.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

/**
 * Created by huichao on 2015/7/7.
 */
@Controller
@RequestMapping("/api")
public class ApiUserController extends BaseController {

    @Autowired
    private IApIUserService iApIUserService;
    @Autowired
    private IRedisService iRedisService;
    /**
     * Register save.
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public void registerSave(@RequestBody ApiUserBean apiUserBean, HttpServletResponse response) {

        ApiResponseMessage message = new ApiResponseMessage();
        try {

            UserInfo userInfo = iApIUserService.findByMobile(apiUserBean.getMobile());
            if (userInfo != null) {
                message.setCode(ErrorCode.ERROR);
                message.setMsg(ErrorCode.USER_MOBILE_EXITS);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }
            iApIUserService.saveUser(apiUserBean);

            message.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            message.setCode(ErrorCode.ERROR);
            message.setMsg(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    /**
     * login
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(@RequestBody LoginBean loginBean,HttpServletResponse response) {

        ApiResponseMessage message = new ApiResponseMessage();
        try {
            //现在用户名和手机号一样，直接查找手机号
            UserInfo userInfo = iApIUserService.findByMobile(loginBean.getIdentity());
            if (userInfo == null) {
                message.setCode(ErrorCode.ERROR);
                message.setMsg(ErrorCode.USER_NOT_EXITS);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }

            //判断密码是否正确
           if(!StringUtil.getMD5Str(loginBean.getUserPassword()).equals(userInfo.getPassword())) {
               message.setCode(ErrorCode.ERROR);
               message.setMsg(ErrorCode.USER_PWD_ERROR);
               ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
               return;
           }
            String token= UUID.randomUUID().toString();
            iRedisService.setMap(PrefixCode.API_TOKEN_MAP,userInfo.getId().toString(),token);
            //更新数据库token保存做备份
            UserInfo userInfoTemp=new UserInfo();
            userInfoTemp.setId(userInfo.getId());
            userInfoTemp.setToken(token);

            iApIUserService.updateUserToken(userInfoTemp);
            message.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            message.setCode(ErrorCode.ERROR);
            message.setMsg(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    /**
     * loginout
     */
    @RequestMapping(value = "/loginout",method = RequestMethod.POST)
    public void loginOut(@RequestBody LoginBean loginBean,HttpServletResponse response) {

        ApiResponseMessage message = new ApiResponseMessage();
        try {
            //现在用户名和手机号一样，直接查找手机号
            UserInfo userInfo = iApIUserService.findByMobile(loginBean.getIdentity());
            if (userInfo == null) {
                message.setCode(ErrorCode.ERROR);
                message.setMsg(ErrorCode.USER_NOT_EXITS);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }

            //判断密码是否正确
            if(!loginBean.getUserPassword().equals(userInfo.getPassword())) {
                message.setCode(ErrorCode.ERROR);
                message.setMsg(ErrorCode.USER_PWD_ERROR);
                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
                return;
            }

            iRedisService.deleteObjectFromMap(PrefixCode.API_TOKEN_MAP, userInfo.getId().toString());
            //更新数据库token保存做备份
            UserInfo userInfoTemp=new UserInfo();
            userInfoTemp.setId(userInfo.getId());
            userInfoTemp.setToken("");

            iApIUserService.updateUserToken(userInfoTemp);
            message.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            message.setCode(ErrorCode.ERROR);
            message.setMsg(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    @RequestMapping("/tokenVerify/{userId}/{token}")
    public void tokenVerify(@PathVariable("userId") Integer userId, @PathVariable("token") String token,HttpServletResponse response) {
        ApiResponseMessage message = new ApiResponseMessage();
        try {
            Object obj=iRedisService.getObjectFromMap(PrefixCode.API_TOKEN_MAP, userId.toString());
            if(obj==null){
                message.setCode(ErrorCode.ERROR);
            }else{
                String tokenRedis=(String)obj;
                if(!token.equals(tokenRedis)){
                    message.setCode(ErrorCode.ERROR);
                    message.setMsg(ErrorCode.USER_TOKEN_NO_VAL);
                }
            }
            message.setCode(ErrorCode.SUCCESS);
        } catch (Exception e) {
            message.setCode(ErrorCode.ERROR);
            message.setMsg(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }
}
