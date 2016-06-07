package com.study.filter;

import com.study.common.StringUtil;
import com.study.common.util.MyConfig;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by huichao on 2016/6/2.
 */
public class ConfigInterceptor extends HandlerInterceptorAdapter  {
    @Resource
    private MyConfig myConfig;
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        HttpSession session = request.getSession();
       // System.out.println("===============in ru");
        if(StringUtil.isEmpty(session.getAttribute("basePath"))){
            System.out.println("===============config==============");
            session.setAttribute("basePath", myConfig.getFullUrl()); // 每个页面，都设置一下基础路径
            session.setAttribute("shortPath", myConfig.getShortUrl()); // 每个页面，都设置一下基础路径
        }

        return true;
    }
}
