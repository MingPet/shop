package com.fh.shop.admin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注解要在什么地方用
@Target(ElementType.METHOD)
//在运行时注解生效
@Retention(RetentionPolicy.RUNTIME)

public @interface LogInfo {
    //注解
    String info(); //定义注解中的属性信息

}
