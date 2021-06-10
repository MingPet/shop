package com.fh.shop.api.common;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.util.MailUtil;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.mapper.IMemberMapper;
import com.fh.shop.po.Member;
import com.fh.shop.util.RedisUtil;
import com.fh.shop.util.SMSUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api(tags = "短信接口")
public class SMSController {

    @Autowired
    private IMemberMapper memberMapper;

    @Autowired
    private MailUtil mailUtil;


    @PostMapping("/sms/sendMail")
    public ServerResponse sendMail(String mail){

            //非空判断
            if(StringUtils.isEmpty(mail)){
                return ServerResponse.error(ResponseEnum.MEMBER_MAIL_IS_NULL);
            }

            //验证邮箱是否合法
            boolean mail1 = Verify.isMail(mail);
            if(mail1==false){
                return ServerResponse.error(ResponseEnum.MEMBER_MAIL_FORMAT_ERROR);
            }
            //查询邮箱是否存在
            QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
            memberQueryWrapper.eq("mail", mail);
            Member selectOne = memberMapper.selectOne(memberQueryWrapper);
            if(selectOne==null){
                return ServerResponse.error(ResponseEnum.MEMBER_MAIL_IS_NULL);
            }
            //随机生成4位数验证码
            String newCode = RandomStringUtils.random(4, "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

            //调用email工具类发送验证码邮件
//            boolean sendMail = MailUtil.sendMailHtml(mail+"飞狐乐购平台通知", "您的验证码为：" + newCode + "，请及时登录，修改密码" );
            mailUtil.sendMailHtml(mail,"飞狐乐购平台通知", "您的验证码为：" + newCode + "，请及时登录，修改密码");
            //判断邮件是否发送成功
            //if(sendMail==true){
                //获取验证码存入redis key为手机号  value为验证码 过期时间  newCode为验证码
                RedisUtil.setEx(mail,newCode,10*60);
                return ServerResponse.success();
           // }
            //return ServerResponse.error();

        }



    @PostMapping("/sms/sendCode")
    public ServerResponse sendCode(String phone){
        //验证手机号非空
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.success(ResponseEnum.MEMBER_PHONE_IS_NULL);
        }

        //验证手机号是否合法

        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        boolean matches = pattern.matcher(phone).matches();
        if(matches != true){
            return ServerResponse.error(ResponseEnum.MEMBER_PHONE_IS_ERROR);
        }
        //调用工具类发送验证码
        String result = SMSUtil.send(phone);
        System.out.println(result);
        //将json格式的字符串转换为对象
        Map map = JSON.parseObject(result, Map.class);
        int code = (int) map.get("code");
        if(code != 200){
            return ServerResponse.error(ResponseEnum.MEMBER_SMS_CODE_IS_ERROR);
        }
        String smsCode = (String) map.get("obj");

        //获取验证码存入redis中 key：value 把手机号当作key 因为是手机号唯一的
        RedisUtil.setEx(phone,smsCode,5*60);

        return ServerResponse.success();
    }
}
