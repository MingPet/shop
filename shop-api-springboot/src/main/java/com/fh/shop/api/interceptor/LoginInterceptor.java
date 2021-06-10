package com.fh.shop.api.interceptor;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.Constants;
import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.api.exception.ShopException;
import com.fh.shop.api.vo.MemberVo;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.util.Md5Util;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //处理跨域 [@CrossOrigin的本质]
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
        //处理自定义的请求头
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,content-type,x-token");

        //处理特殊的请求 DELETE,PUT,POST,GET
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"DELETE,PUT,POST,GET");
        //处理options请求

        String requestMethod = request.getMethod();//获取请求方式 get post put delete options

        if(requestMethod.equalsIgnoreCase("options")){//忽略大小写
            //拦截 不进行处理
            return false;
        }

        HandlerMethod handlerMethod= (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if(!method.isAnnotationPresent(Check.class)){
            //放行
            return true;
        }
        //验证 x-auth:fygfd23...
        //判断是否有头信息
        String header = request.getHeader("x-auth");
        if(StringUtils.isEmpty(header)){
            //不仅拦截  还给前台提示信息
            //头信息丢失
            throw new ShopException(ResponseEnum.MEMBER_LOGIN_TOKEN_IS_MISS);
        }
        //验证头信息是否完整
        String[] headerArr = header.split(",");
        if(headerArr.length != 2){
            throw new ShopException(ResponseEnum.MEMBER_LOGIN_TOKEN_IS_NOT_FULL);
        }
        //验签
        String memberVoJsonBase64 = headerArr[0];
        String signBase64 = headerArr[1];
        //解码
        String memberVoJson = new String(Base64.getDecoder().decode(memberVoJsonBase64),"utf-8");
        String sign = new String(Base64.getDecoder().decode(signBase64),"utf-8");

        String newSign = Md5Util.sign(memberVoJson, Constants.SECRET);
        if(!newSign.equals(sign)){
            //验签失败
            throw new ShopException(ResponseEnum.MEMBER_LOGIN_TOKEN_IS_FALL);
        }
        //将json转为java对象
        MemberVo memberVo = com.alibaba.fastjson.JSON.parseObject(memberVoJson, MemberVo.class);
        Long id = memberVo.getId();

        //判断是否过期
        if(!RedisUtil.exist(KeyUtil.buildMemberKey(id))){
            throw new ShopException(ResponseEnum.MEMBER_LOGIN_TOKEN_IS_TIME_OUT);
        }
        //续命
        RedisUtil.expire(KeyUtil.buildMemberKey(id),Constants.TOKEN_EXPIRE);
        //将memberVo存入requent中
        request.setAttribute(Constants.CURR_MEMBER,memberVo);

        return true;
    }


}
