package com.fh.shop.api;

import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.po.MsgLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMp {

    @Autowired
    private IMsgLogMapper msgLogMapper;


    @Test
    public void test(){
        MsgLog msgLog = new MsgLog();
        msgLog.setStatus(10);
        msgLog.setMessage("42s2dfds");
        msgLogMapper.insert(msgLog);
    }
}
