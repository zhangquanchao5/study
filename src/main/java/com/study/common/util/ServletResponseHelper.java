package com.study.common.util;


import com.study.common.StudyLogger;
import org.apache.log4j.Level;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zqc
 */
public final class ServletResponseHelper {
    private ServletResponseHelper() {
    }

    /**
     * Out uTF 8.
     *
     * @param response the response
     * @param resStr the res str
     */
    public static void outUTF8(HttpServletResponse response, String resStr) {
             try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            response.getWriter().write(resStr);
        } catch (IOException e) {
                 StudyLogger.recBusinessLog(Level.ERROR, e.getMessage(), e);
        }
    }

    /**
     * Out uTF 8 to xml.
     *
     * @param response the response
     * @param resStr the res str
     */
    public static void outUTF8ToXml(HttpServletResponse response, String resStr) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml; charset=utf-8");
            response.getWriter().write(resStr);
        } catch (IOException e) {
            StudyLogger.recBusinessLog(Level.ERROR, e.getMessage(), e);
        }
    }

    /**
     * Out uTF 8 to json.
     *
     * @param response the response
     * @param resStr the res str
     */
    public static void outUTF8ToJson(HttpServletResponse response, String resStr) {
        try {
            StudyLogger.recBusinessLog("result:"+resStr);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(resStr);
        } catch (IOException e) {
            StudyLogger.recBusinessLog(Level.ERROR, e.getMessage(), e);
        }
    }

    /**
     * Out gBK.
     *
     * @param response the response
     * @param resStr the res str
     */
    public static void outGBK(HttpServletResponse response, String resStr) {
        try {
            response.setCharacterEncoding("GBK");
            response.setContentType("text/html; charset=gbk");
            response.getWriter().write(resStr);
        } catch (IOException e) {
            StudyLogger.recBusinessLog(Level.ERROR, e.getMessage(), e);
        }
    }
}
