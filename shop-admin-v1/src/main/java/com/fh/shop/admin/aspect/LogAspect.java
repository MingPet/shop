package com.fh.shop.admin.aspect;

import com.fh.shop.admin.annotation.LogInfo;
import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.admin.po.log.Log;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.common.WebContext;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class LogAspect {

    @Resource(name = "logService")
    private ILogService logService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);


    // 横切逻辑【非核心】
    public Object doLog(ProceedingJoinPoint pjp) throws Throwable {
        // 类的全称
       String canonicalName = pjp.getTarget().getClass().getCanonicalName();
       MethodSignature signature = (MethodSignature) pjp.getSignature();

        // 获取方法名
        String methodName = signature.getName();



        //获取当前正在执行的方法
        Method method = signature.getMethod();

        //判断方法上是否有指定的自定义注解
        //将info提取成全局变量
        String info = "";
        if(method.isAnnotationPresent(LogInfo.class)){
            //获取方法上的自定义注解
            LogInfo annotation = method.getAnnotation(LogInfo.class);
            //获取注解中的属性的值
             info = annotation.info();

        }
        //向数据库插入日志信息
        HttpServletRequest request = WebContext.getRequest();
        //获取参数信息

        //声明全局变量
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String[]> parameterMap = request.getParameterMap();
        //循环遍历Map
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        while (iterator.hasNext()){
            //如果头=有下一个值。就继续循
            Map.Entry<String, String[]> entry = iterator.next();
            String key = entry.getKey();
            String[] value = entry.getValue();

            //我们想要得结果 key=value;key=value1,value2;
           stringBuilder.append(";").append(key).append("=").append(StringUtils.join(value, ","));

        }
        String sub = "";
        if(stringBuilder.length()>0){
            sub = stringBuilder.substring(1);
        }


        LOGGER.info("开始执行了"+canonicalName+"的"+methodName+"开始---------");
        // 最重要[执行真正的业务]
        Object result = pjp.proceed();
        LOGGER.info("执行了"+canonicalName+"的指定"+methodName+"结束=========");



        //获取用户信息
        User user = (User) request.getSession().getAttribute(SystemConstant.CURR_USER);

        //如果用户为null说明没有登陆成功

        if(user == null){
            return  result;
        }
        Log log = new Log();
        log.setUserName(user.getUserName());
        log.setRealName(user.getRealName());
        log.setInfo(info);
        log.setInsertTime(new Date());
        log.setParamInfo(sub);
        //插入日志
        logService.addLog(log);
        return result;

    }
}
