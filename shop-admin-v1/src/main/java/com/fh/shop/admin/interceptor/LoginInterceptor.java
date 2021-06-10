package com.fh.shop.admin.interceptor;

import com.fh.shop.common.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取session的用户信息
        Object user = request.getSession().getAttribute(SystemConstant.CURR_USER);
        if (user == null) {

            //获取请求头信息
            String header = request.getHeader("X-Requested-With");
            if(StringUtils.isNotEmpty(header) && header.equalsIgnoreCase("XMLHttpRequest")){
                //如果是ajax请求 服务端向客户端相应一个自定义的头信息
                response.addHeader("LL-Session-Timeout","sessionTimeout");
            }else {
                // 如果用户信息为空则证明用户未登录，跳转到登录页面
                response.sendRedirect(SystemConstant.LOGIN_URL);
            }



            return false;
        } else {
            // 否则证明用户成功登录，则放行
            return true;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
