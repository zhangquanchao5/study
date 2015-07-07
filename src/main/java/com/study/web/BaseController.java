package com.study.web;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import com.study.common.StringUtil;
import com.study.common.StudyLogger;
import org.apache.logging.log4j.Level;
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
