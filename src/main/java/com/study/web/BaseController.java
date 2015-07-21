package com.study.web;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import com.study.code.PrefixCode;
import com.study.code.SplitCode;
import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import com.study.common.oss.DESUtils;
import com.study.common.util.PropertiesUtil;
import com.study.service.IRedisService;
import org.apache.log4j.Level;
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
    protected String[] getAuthHeader(HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        if(getPlatformHeader(request).equals(PrefixCode.API_HEAD_WEB)){
            String decodedTicket = DESUtils.decrypt(auth, PropertiesUtil.getString("sso.secretKey"));
            StudyLogger.recBusinessLog("auth:["+auth+"] encode["+decodedTicket+"]");
            return decodedTicket.split(SplitCode.SPLIT_EQULE);
        }else{
            String encode= StringUtil.getFromBASE64(auth);
            StudyLogger.recBusinessLog("auth:["+auth+"] encode["+encode+"]");
            return encode.split(SplitCode.SPLIT_EQULE);
        }

    }

    /**
     * api判断token是否有效
     * 此处都有有效期，如果app没有效期，则不判断失效时间
     */
    protected  boolean isAuthToken(IRedisService iRedisService,HttpServletRequest request){
        String auth = request.getHeader("Authorization");
        String encode= StringUtil.getFromBASE64(auth);
        StudyLogger.recBusinessLog("platform:"+request.getHeader("platform"));
        StudyLogger.recBusinessLog("auth:["+auth+"] encode["+encode+"]");

        if(getPlatformHeader(request).equals(PrefixCode.API_HEAD_H5)){
            if(iRedisService.getObjectFromMap(PrefixCode.API_H5_TOKEN_MAP,encode.split(SplitCode.SPLIT_EQULE)[0])!=null){
                String code= StringUtil.getFromBASE64((String)iRedisService.getObjectFromMap(PrefixCode.API_H5_TOKEN_MAP,encode.split(SplitCode.SPLIT_EQULE)[0]));
                String[] codes=code.split(SplitCode.SPLIT_SHU);
                if(System.currentTimeMillis()>(Long.parseLong(codes[2])+Long.parseLong(codes[1])*60*1000)){
                    return false;
                }
                return true;
            }
        }else if(getPlatformHeader(request).equals(PrefixCode.API_HEAD_WEB)){
            String decodedTicket = DESUtils.decrypt(auth, PropertiesUtil.getString("sso.secretKey"));
            if(iRedisService.get(PrefixCode.API_COOKIE_PRE+decodedTicket)!=null){
                return true;
            }
            return false;
        }else{
            if(iRedisService.getObjectFromMap(PrefixCode.API_TOKEN_MAP,encode.split(SplitCode.SPLIT_EQULE)[0])!=null){
                String code= StringUtil.getFromBASE64((String)iRedisService.getObjectFromMap(PrefixCode.API_TOKEN_MAP,encode.split(SplitCode.SPLIT_EQULE)[0]));
                String[] codes=code.split(SplitCode.SPLIT_SHU);
                if(System.currentTimeMillis()>(Long.parseLong(codes[2])+Long.parseLong(codes[1])*60*1000)){
                    return false;
                }
                return true;
            }
        }

        return  false;
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
