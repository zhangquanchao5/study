package com.study.web;

import com.alibaba.fastjson.JSON;
import com.study.code.EntityCode;
import com.study.code.ErrorCode;
import com.study.code.PrefixCode;
import com.study.common.DateUtil;
import com.study.common.ImageUtil;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.apibean.ApiUserBean;
import com.study.common.apibean.MobileBean;
import com.study.common.apibean.request.LoginRequest;
import com.study.common.apibean.request.MobileRequest;
import com.study.common.apibean.request.RegisterMobileRequest;
import com.study.common.apibean.response.CommonResponse;
import com.study.common.apibean.response.LoginResponse;
import com.study.common.apibean.response.RegisterMobileResponse;
import com.study.common.util.MessageUtil;
import com.study.common.util.ServletResponseHelper;
import com.study.model.UserInfo;
import com.study.service.IApIUserService;
import com.study.service.IRedisService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Base64;

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
    @Autowired
    private MessageUtil messageUtil;
    /**
     * 1发送手机验证码
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
                    mobileBean.setMessage(messageUtil.getMessage("MSG.USER_EXITS_CN"));
                }else{
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                    String code=StringUtil.generateTextCode(0, 6, null);
                    mobileBean.setVerifyCode(code);
                    //SEND MOBILE
                    iRedisService.set(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone(), code, 300);
                }
            }else if(mobileRequest.getType()== EntityCode.MOBILE_BIND_UPDATE){
                UserInfo userInfo=iApIUserService.findByMobile(mobileRequest.getUserPhone());
                if(userInfo!=null){
                    mobileBean.setCode(ErrorCode.USER_EXITS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.USER_EXITS_CN"));
                }else{
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                    String code=StringUtil.generateTextCode(0, 6, null);
                    mobileBean.setVerifyCode(code);
                    //SEND MOBILE
                    iRedisService.set(PrefixCode.API_MOBILE_BIND+ mobileRequest.getUserPhone(), code, 300);
                }
            }else if(mobileRequest.getType()== EntityCode.MOBILE_GET_PASSWORD){
                UserInfo userInfo=iApIUserService.findByMobile(mobileRequest.getUserPhone());
                if(userInfo==null){
                    mobileBean.setCode(ErrorCode.USER_NOT_EXITS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.USER_NOT_EXITS_CN"));
                }else{
                    mobileBean.setCode(ErrorCode.SUCCESS);
                    mobileBean.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                    String code=StringUtil.generateTextCode(0, 6, null);
                    mobileBean.setVerifyCode(code);
                    //SEND MOBILE
                    iRedisService.set(PrefixCode.API_MOBILE_UPDATE  + mobileRequest.getUserPhone(), code, 300);
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
            String json=this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/reg:" + json);

            RegisterMobileRequest mobileRequest= JSON.parseObject(json, RegisterMobileRequest.class);
            ApiUserBean apiUserBean=new ApiUserBean();
          //  apiUserBean.setUserName(mobileRequest.getUserPhone());
            apiUserBean.setMobile(mobileRequest.getUserPhone());
            apiUserBean.setPassword(mobileRequest.getPasswd());
            //判断是否用户名注册
            UserInfo isExist=iApIUserService.findByUserName(mobileRequest.getUserPhone());
            if(isExist!=null){
                registerMobileResponse.setCode(ErrorCode.USER_EXITS);
                registerMobileResponse.setMessage(messageUtil.getMessage("MSG.USER_EXITS_CN"));
            }else{
                //判断注册码是否有效
                String code=iRedisService.get(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone());
                if(code!=null&&!"".equals(code)&&code.equals(mobileRequest.getVerifyCode())){
                    iApIUserService.saveUser(apiUserBean);
                    UserInfo userInfo=iApIUserService.findByMobile(apiUserBean.getMobile());
                    registerMobileResponse.setCode(ErrorCode.SUCCESS);
                    registerMobileResponse.setMessage(messageUtil.getMessage("MSG.SUCCESS_CN"));
                    registerMobileResponse.setContent(userInfo);
                    iRedisService.deleteOneKey(PrefixCode.API_MOBILE_REGISTER + mobileRequest.getUserPhone());
                }else{
                    registerMobileResponse.setCode(ErrorCode.USER_CODE_ERROR);
                    registerMobileResponse.setMessage(messageUtil.getMessage("MSG.USER_CODE_ERROR_CN"));
                }
            }

        } catch (Exception e) {
            registerMobileResponse.setCode(ErrorCode.ERROR);
            registerMobileResponse.setMessage(messageUtil.getMessage("MSG.ERROR_CN"));
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(registerMobileResponse).toString());
    }

    /**
     * login
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(HttpServletRequest request,HttpServletResponse response) {

        CommonResponse commonResponse = new CommonResponse();
        try {
            //判断mobile死否为空
            String json=this.getParameter(request);
            StudyLogger.recBusinessLog("/pub/login:" + json);
            LoginRequest loginRequest= JSON.parseObject(json, LoginRequest.class);
            UserInfo userInfo=null;
            if(!StringUtil.isEmpty(loginRequest.getUserPhone())){
                userInfo = iApIUserService.findByMobile(loginRequest.getUserPhone());
                if (userInfo == null) {
                    commonResponse.setCode(ErrorCode.ERROR);
                    commonResponse.setMsg(ErrorCode.USER_NOT_EXITS);
                }
            }else if(!StringUtil.isEmpty(loginRequest.getUserName())){
                userInfo=iApIUserService.findByUserName(loginRequest.getUserName());
                if (userInfo == null) {
                    commonResponse.setCode(ErrorCode.ERROR);
                    commonResponse.setMsg(ErrorCode.USER_NOT_EXITS);
                }
            }else if(!StringUtil.isEmpty(loginRequest.getUserEmail())){
                userInfo=iApIUserService.findByEMail(loginRequest.getUserEmail());
                if (userInfo == null) {
                    commonResponse.setCode(ErrorCode.ERROR);
                    commonResponse.setMsg(ErrorCode.USER_NOT_EXITS);
                }
            }

            if(userInfo!=null&&!StringUtil.getMD5Str(loginRequest.getUserPassword()).equals(userInfo.getPassword())) {
                commonResponse.setCode(ErrorCode.ERROR);
                commonResponse.setMsg(ErrorCode.USER_PWD_ERROR);
            }else{

                String token=Base64.getEncoder().encodeToString((System.currentTimeMillis()+"|"+64800).getBytes());
                //更新数据库token保存做备份
                UserInfo userInfoTemp=new UserInfo();
                userInfoTemp.setId(userInfo.getId());
                userInfoTemp.setToken(token);

                iApIUserService.updateUserToken(userInfoTemp);

                iRedisService.setMap(PrefixCode.API_TOKEN_MAP, userInfo.getId().toString(), token);

                commonResponse.setCode(ErrorCode.SUCCESS);
                commonResponse.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setToken(token);
                loginResponse.setInvalidTime(64800l);
                loginResponse.setUser(userInfo);

                commonResponse.setData(loginResponse);
            }

        } catch (Exception e) {
            commonResponse.setCode(ErrorCode.ERROR);
            commonResponse.setMsg(ErrorCode.SYS_ERROR);
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
            String[] auth=getAuthHeader(request);
            StudyLogger.recBusinessLog("/pub/logout:" + auth.toString());
            iRedisService.deleteObjectFromMap(PrefixCode.API_TOKEN_MAP, auth[0]);
            //更新数据库token保存做备份
            UserInfo userInfoTemp=new UserInfo();
            userInfoTemp.setId(Integer.parseInt(auth[0]));
            userInfoTemp.setToken("");

            iApIUserService.updateUserToken(userInfoTemp);
            message.setCode(ErrorCode.SUCCESS);
            message.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
            message.setData(iApIUserService.findById(userInfoTemp.getId()));
        } catch (Exception e) {
            message.setCode(ErrorCode.ERROR);
            message.setMsg(ErrorCode.SYS_ERROR);
            printLogger(e);
        }
        ServletResponseHelper.outUTF8ToJson(response, JSON.toJSON(message).toString());
    }

    /**
     * up 上传
     */
    @RequestMapping(value = "/up",method = RequestMethod.POST)
    public void up(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response) {

        CommonResponse message = new CommonResponse();
        try {
            //现在用户名和手机号一样，直接查找手机号
            String[] auth=getAuthHeader(request);
            StudyLogger.recBusinessLog("/pub/up:" + auth.toString());
            if(!file.isEmpty()){
                ServletContext sc = request.getSession().getServletContext();
                String dir = sc.getRealPath(PrefixCode.FILE_PATH);

                String filename = file.getOriginalFilename();


                long _lTime = System.nanoTime();
                String _ext = filename.substring(filename.lastIndexOf("."));
                filename = _lTime + _ext;

                FileUtils.writeByteArrayToFile(new File(dir, filename), file.getBytes());
                message.setData(dir+File.separator+filename);
                message.setCode(ErrorCode.SUCCESS);
                message.setMsg(messageUtil.getMessage("MSG.SUCCESS_CN"));
            }else{
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
    @RequestMapping(value = "/up/img/{file}")
    public void img(@PathVariable("file") String name,@RequestParam String w,@RequestParam String h, HttpServletRequest request,HttpServletResponse response) {
        try{
            StudyLogger.recBusinessLog("/up/img" +name);
            byte[] img= ImageUtil.resizeOUT(new File(request.getSession().getServletContext().getRealPath(PrefixCode.FILE_PATH), name),Integer.parseInt(w),Integer.parseInt(h),0.8f,true);
            response.getWriter().write(new String(img,"UTF-8"));
        }catch (Exception e){
            printLogger(e);
        }
    }

}
