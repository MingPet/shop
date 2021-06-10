package com.fh.shop.api;

import com.fh.shop.api.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailTest {

    @Test
    public void test1(){
        int a = 5;
        int b = 10;
        System.out.println("----"+(a+b));
    }

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.from}")
    private String mailFrom;
    @Test
    public void test2(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("飞狐乐购平台通知");
        simpleMailMessage.setText("您本次的验证码为***");
        simpleMailMessage.setTo("2861373589@qq.com");
        simpleMailMessage.setFrom(mailFrom);
        mailSender.send(simpleMailMessage);
    }

    @Test
    public void test3() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom(mailFrom);
        mimeMessageHelper.setTo("2861373589@qq.com");
        mimeMessageHelper.setSubject("飞狐乐购平台通知");
        mimeMessageHelper.setText("<h1>您本次的验证码为***</h1>",true);
        mailSender.send(mimeMessage);
    }


    @Autowired
    private MailUtil mailUtil;
    @Test
    public void test4(){
        mailUtil.sendMailHtml("2861373589@qq.com","测试","<h1 style='color:green'>您本次的验证码为***</h1>");
    }
}
