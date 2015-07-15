package com.study.filter;


import com.study.common.StudyLogger;
import com.study.common.session.LoginUser;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * The type Authority interceptor.
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    /**
     * Instantiates a new Authority interceptor.
     */
    public AuthorityInterceptor() {
    }

    /**
     * preHandle
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws IOException
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        System.out.println(request.getRequestURI());
        if (null == LoginUser.getSessionInfo()) {
            String type = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equalsIgnoreCase(type)) {
                StudyLogger.recSysLog("ajax login timeout!");
                response.setHeader("sessionstatus", "loginTimeOut");
            } else {
                StudyLogger.recSysLog("login timeout!");
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return false;
        }
        return true;
    }

}
