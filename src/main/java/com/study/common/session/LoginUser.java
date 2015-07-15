package com.study.common.session;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: zqc
 */
public final class LoginUser {
    private LoginUser() {
    }

    /**
     * The constant USER_SESSION_INFO.
     */
    public static final String USER_SESSION_INFO = "user_session_info";     //登录用户seesion名称


    /**
     * Gets current session.
     *
     * @return the current session
     */
    public static HttpSession getCurrentSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }

    /**
     * Gets current request.
     *
     * @return the current request
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }


    /**
     * Gets session info.
     *
     * @return the session info
     */
//得到当前用户信息
    public static SessionInfo getSessionInfo() {
        return (SessionInfo) getCurrentSession().getAttribute(USER_SESSION_INFO);
    }




}
