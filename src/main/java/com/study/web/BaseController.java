package com.study.web;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import com.study.code.PrefixCode;
import com.study.code.SplitCode;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.apibean.AuthHeaderBean;
import com.study.common.apibean.request.MobileRequest;
import com.study.common.apibean.response.UserResponse;
import com.study.common.util.MessageUtil;
import com.study.common.oss.DESUtils;
import com.study.common.util.PropertiesUtil;
import com.study.model.UserInfo;
import com.study.service.IRedisService;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zqc on 2015/1/21.
 */
public class BaseController {

    @Autowired
    public MessageUtil messageUtil;

    /**
     * The constant MAPPING.
     */
    public static final SerializeConfig MAPPING;
    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        MAPPING = new SerializeConfig();
        MAPPING.put(Date.class, new SimpleDateFormatSerializer(FORMAT));
    }

    /**
     * Init binder.
     *
     * @param binder the binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    public UserResponse changeUser(UserInfo userInfo){
        UserResponse userResponse=new UserResponse();
        userResponse.setId(userInfo.getId());
        userResponse.setGender(userInfo.getGender() == null ? 0 : new Integer(userInfo.getGender()));
        userResponse.setNick(userInfo.getNick());
        userResponse.setAddress(userInfo.getAddress());
        userResponse.setCt(userInfo.getCreateTime());
        userResponse.setMobile(userInfo.getMobile());
        userResponse.setIcon(userInfo.getIcon());
        userResponse.setName(userInfo.getName());
        userResponse.setUserName(userInfo.getUserName());
        userResponse.setIdCard(userInfo.getIdCard());
        userResponse.setRemark(userInfo.getRemark());
        userResponse.setDomain(userInfo.getDomain()==null?"":userInfo.getDomain());
        userResponse.setLastLoginTime(userInfo.getUpdateTime()==null?userInfo.getCreateTime():userInfo.getUpdateTime());

        return  userResponse;
    }
    /**
     * Gets parameter.
     *
     * @param request the request
     * @return the parameter
     */
    protected String getParameter(HttpServletRequest request){
        String p = "";
        try {
            request.setCharacterEncoding("UTF-8");
            BufferedReader buff = null;
            StringBuffer str = null;
            str = new StringBuffer();
            String s = null;
            buff = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
            while ((s = buff.readLine()) != null){
                str.append(s);
            }
            p = str.toString();
            if(StringUtil.isEmpty(p)){
                p = "{}";
            }
        } catch (IOException e) {
            printLogger(e);
        }

        return p;
    }

    /**
     * 获取认证
     * @param request
     * @return
     */
    protected AuthHeaderBean getAuthHeader(HttpServletRequest request){
        AuthHeaderBean authHeaderBean = new AuthHeaderBean();
        String auth = request.getHeader("Authorization");
//        if(getPlatformHeader(request).equals(PrefixCode.API_HEAD_WEB)){
//            String decodedTicket = DESUtils.decrypt(auth, PropertiesUtil.getString("sso.secretKey"));
//            StudyLogger.recBusinessLog("auth:[" + auth + "] encode[" + decodedTicket + "]");
//            String[] webs = decodedTicket.split(SplitCode.SPLIT_EQULE);
//            authHeaderBean.setUserId(Integer.parseInt(webs[0]));
//        }else{
            String encode= StringUtil.getFromBASE64(auth);
            StudyLogger.recBusinessLog("auth:[" + auth + "] encode[" + encode + "]");
            String[] strs = encode.split(SplitCode.SPLIT_EQULE);
            authHeaderBean.setUserId(Integer.parseInt(strs[0]));
            authHeaderBean.setEncode(encode);

//        }
        return authHeaderBean;
    }

    /**
     * api判断token是否有效
     * 此处都有有效期，如果app没有效期，则不判断失效时间
     */
    protected  boolean isAuthToken(IRedisService iRedisService,HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        String encode= StringUtil.getFromBASE64(auth);
        StudyLogger.recBusinessLog("platform:" + request.getHeader("platform"));
        StudyLogger.recBusinessLog("auth:[" + auth + "] encode[" + encode + "]");

        if(getPlatformHeader(request).equals(PrefixCode.API_HEAD_H5)){
            StudyLogger.recBusinessLog((iRedisService.getObjectFromMap(PrefixCode.API_H5_TOKEN_MAP,encode.split(SplitCode.SPLIT_EQULE)[0])!=null)+"");
            if(iRedisService.getObjectFromMap(PrefixCode.API_H5_TOKEN_MAP,encode.split(SplitCode.SPLIT_EQULE)[0])!=null){
                String code= StringUtil.getFromBASE64(encode.substring(encode.split(SplitCode.SPLIT_EQULE)[0].length() + 1, encode.length()));
                String[] codes=code.split(SplitCode.SPLIT_ZHUANYI);
                if(System.currentTimeMillis()>Long.parseLong(codes[2])){
                    return false;
                }
                return true;
            }
        }else if(getPlatformHeader(request).equals(PrefixCode.API_HEAD_WEB)){
           // String decodedTicket = DESUtils.decrypt(auth, PropertiesUtil.getString("sso.secretKey"));
            if(iRedisService.getObject(PrefixCode.API_COOKIE_PRE+encode)!=null){
                return true;
            }
            return false;
        }else{
            if(iRedisService.getObjectFromMap(PrefixCode.API_TOKEN_MAP,encode.split(SplitCode.SPLIT_EQULE)[0])!=null){
                String code= StringUtil.getFromBASE64(encode.substring(encode.split(SplitCode.SPLIT_EQULE)[0].length()+1,encode.length()));
                String[] codes=code.split(SplitCode.SPLIT_ZHUANYI);
                if(System.currentTimeMillis()>Long.parseLong(codes[2])){
                    return false;
                }
                return true;
            }
        }

        return  false;
    }

    public boolean validatePubSingature(String timeStamp,String signature){
        String []encodes=signature.split(SplitCode.SPLIT_ZHUANYI);
        if(StringUtil.getBASE64(PrefixCode.API_TOKEN_SIGN+timeStamp).equals(encodes[0])){
            return true;
        }

       return false;
    }

    /**
     * 获取platform
     * @param request
     * @return
     */
    protected String getPlatformHeader(HttpServletRequest request) {
        StudyLogger.recBusinessLog("platform:"+request.getHeader("platform"));
        return request.getHeader("platform");
    }
    /**
     * Print logger.
     *
     * @param message the message
     */
    public void printLogger(String message) {
        StudyLogger.recBusinessLog(Level.INFO, message);
    }

    /**
     * Print logger.
     *
     * @param e the e
     */
    public void printLogger(Exception e) {
        StudyLogger.recBusinessLog(Level.ERROR, e.getMessage(), e);
    }
}
