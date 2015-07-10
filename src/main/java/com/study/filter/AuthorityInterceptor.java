package com.study.filter;


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
        String currentURL = request.getRequestURI();
        return true;
    }

}
