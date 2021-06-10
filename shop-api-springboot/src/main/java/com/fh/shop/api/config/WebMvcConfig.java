package com.fh.shop.api.config;

import com.fh.shop.api.interceptor.LoginInterceptor;
import com.fh.shop.api.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //通过编码方式配置拦截器
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/api/**");//拦截所有
        registry.addInterceptor(tokenInterceptor()).addPathPatterns("/api/**");//拦截所有
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Bean
    public TokenInterceptor tokenInterceptor(){

        return new TokenInterceptor();
    }
}
