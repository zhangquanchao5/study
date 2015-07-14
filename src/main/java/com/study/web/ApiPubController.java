package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.MobileBean;
import com.study.common.apibean.request.MobileRequest;
import com.study.common.apibean.request.RegisterMobileRequest;
import com.study.common.apibean.response.RegisterMobileResponse;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.service.IApIUserService;
import com.study.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 发送手机验证码
     */
    @RequestMapping(value = "/getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        MobileBean mobileBean = new MobileBean();
        try {
            String json=this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/getCode:" + json);

            MobileRequest mobileRequest= JSON.parseObject(json, MobileRequest.class);
            if(mobileRequest.getType()== EntityCode.MOBILE_REGESITER){
                UserInfo userInfo=iApIUserService.findByMobile(mobileRequest.getUserPhone());
                if(userInfo!=null){
                    mobileBean.setCode(ErrorCode.USER_EXITS);
                    mobileBean.setMessage(ErrorCode.USER_EXITS_CN);
                }else{
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(ErrorCode.SUCCESS_CN);
                    String code=StringUtil.generateTextCode(0, 6, null);
                    mobileBean.setVerifyCode(code);
                    //SEND MOBILE
                    iRedisService.set(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone(), code, 300);
                }
            }else if(mobileRequest.getType()== EntityCode.MOBILE_BIND_UPDATE){
                UserInfo userInfo=iApIUserService.findByMobile(mobileRequest.getUserPhone());
                if(userInfo!=null){
                    mobileBean.setCode(ErrorCode.USER_EXITS);
                    mobileBean.setMessage(ErrorCode.USER_EXITS_CN);
                }else{
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(ErrorCode.SUCCESS_CN);
                    String code=StringUtil.generateTextCode(0, 6, null);
                    mobileBean.setVerifyCode(code);
                    //SEND MOBILE
                    iRedisService.set(PrefixCode.API_MOBILE_BIND+ mobileRequest.getUserPhone(), code, 300);
                }
            }else if(mobileRequest.getType()== EntityCode.MOBILE_GET_PASSWORD){
                UserInfo userInfo=iApIUserService.findByMobile(mobileRequest.getUserPhone());
                if(userInfo==null){
                    mobileBean.setCode(ErrorCode.USER_NOT_EXITS);
                    mobileBean.setMessage(ErrorCode.USER_NOT_EXITS_CN);
                }else{
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(ErrorCode.SUCCESS_CN);
                    String code=StringUtil.generateTextCode(0, 6, null);
                    mobileBean.setVerifyCode(code);
                    //SEND MOBILE
                    iRedisService.set(PrefixCode.API_MOBILE_UPDATE  + mobileRequest.getUserPhone(), code, 300);
                }
            }
        } catch (Exception e) {
            mobileBean.setCode(ErrorCode.ERROR);
            mobileBean.setMessage(ErrorCode.ERROR_CN);
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
            String json=this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/reg:" + json);

            RegisterMobileRequest mobileRequest= JSON.parseObject(json, RegisterMobileRequest.class);
            ApiUserBean apiUserBean=new ApiUserBean();
            apiUserBean.setUserName(mobileRequest.getUserPhone());
            apiUserBean.setMobile(mobileRequest.getUserPhone());
            apiUserBean.setPassword(mobileRequest.getPasswd());

            //判断注册码是否有效
            String code=iRedisService.get(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone());
            if(code!=null&&!"".equals(code)&&code.equals(mobileRequest.getVerifyCode())){
                iApIUserService.saveUser(apiUserBean);
                UserInfo userInfo=iApIUserService.findByMobile(apiUserBean.getMobile());
                registerMobileResponse.setCode(ErrorCode.SUCCESS);
                registerMobileResponse.setMessage(ErrorCode.SUCCESS_CN);
                registerMobileResponse.setContent(userInfo);
                iRedisService.deleteOneKey(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone());
            }else{
                registerMobileResponse.setCode(ErrorCode.USER_CODE_ERROR);
                registerMobileResponse.setMessage(ErrorCode.USER_CODE_ERROR_CN);
            }
        } catch (Exception e) {
            registerMobileResponse.setCode(ErrorCode.ERROR);
            registerMobileResponse.setMessage(ErrorCode.ERROR_CN);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
    }

//    /**
//     * login
//     */
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    public void login(@RequestBody LoginBean loginBean,HttpServletResponse response) {
//
//        ApiResponseMessage message = new ApiResponseMessage();
//        try {
//            //现在用户名和手机号一样，直接查找手机号
//            UserInfo userInfo = iApIUserService.findByMobile(loginBean.getIdentity());
//            if (userInfo == null) {
//                message.setCode(ErrorCode.ERROR);
//                message.setMsg(ErrorCode.USER_NOT_EXITS);
//                ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
//                return;
//            }
//
//            //判断密码是否正确22
//           if(!StringUtil.getMD5Str(loginBean.getUserPassword()).equals(userInfo.getPassword())) {
//               message.setCode(ErrorCode.ERROR);
//               message.setMsg(ErrorCode.USER_PWD_ERROR);
//               ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
//               return;
//           }
//            String token= UUID.randomUUID().toString();
//            iRedisService.setMap(PrefixCode.API_TOKEN_MAP,userInfo.getId().toString(),token);
//            //更新数据库token保存做备份
//            UserInfo userInfoTemp=new UserInfo();
//            userInfoTemp.setId(userInfo.getId());
//            userInfoTemp.setToken(token);
//
//            iApIUserService.updateUserToken(userInfoTemp);
//            message.setCode(ErrorCode.SUCCESS);
//        } catch (Exception e) {
//            message.setCode(ErrorCode.ERROR);
//            message.setMsg(ErrorCode.SYS_ERROR);
//            printLogger(e);
//        }
//        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
//    }

}
