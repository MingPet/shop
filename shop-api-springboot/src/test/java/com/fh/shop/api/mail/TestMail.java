package com.fh.shop.api.mail;

import com.fh.shop.api.mq.MailProducer;
import com.fh.shop.mq.MailMessage;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMail {

    @Autowired
    private MailProducer mailProducer;


    @Test
    public void test1(){
        MailMessage mailMessage = new MailMessage();
        mailMessage.setConent("1111sdsds");
        mailMessage.setTitle("123");
        mailMessage.setTo("2861373589@qq.com");
        mailProducer.sendMailMessage(mailMessage);
        try {
            Thread.sleep(30*60*1000);//睡眠
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
