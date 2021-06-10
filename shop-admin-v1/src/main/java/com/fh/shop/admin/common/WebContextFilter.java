package com.fh.shop.admin.common;

import com.fh.shop.common.WebContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class WebContextFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            //强转为httpServletRequest
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            //将servlet存入ThreadLocal
            WebContext.setRequest(request);
            //执行后续代码
            filterChain.doFilter(servletRequest,response);
        } finally {
            //用完后将将ThreadLocal的reque删除
            WebContext.remove();
        }


    }

    @Override
    public void destroy() {

    }
}
