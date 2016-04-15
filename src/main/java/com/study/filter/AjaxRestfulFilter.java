package com.study.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by huichao on 2015/10/8.
 */
public class AjaxRestfulFilter implements Filter {
    /*
	 * <p>Title: destroy</p>
	 * <p>Description: </p>
	 * @see javax.servlet.Filter#destroy()
	 */

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

	/*
	 * <p>Title: doFilter</p>
	 * <p>Description: </p>
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */

    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException ,ServletException {
        HttpServletResponse response = (HttpServletResponse) arg1;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,x-user-session,origin, content-type, accept,Authorization,platform");
        response.setHeader("Access-Control-Request-Headers","Authorization,platform");
        response.setHeader("Access-Control-Allow-Credentials","true");
        arg2.doFilter(arg0, arg1);

    };

	/*
	 * <p>Title: init</p>
	 * <p>Description: </p>
	 * @param rg0
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }
}
