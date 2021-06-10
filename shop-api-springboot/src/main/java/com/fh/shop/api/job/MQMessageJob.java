package com.fh.shop.api.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.mq.MQConstants;
import com.fh.shop.po.MsgLog;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.util.DateUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MQMessageJob {

    @Autowired
    private IMsgLogMapper msgLogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void doJob(){
        //查询状态在“投递中”并且到了重试时间（重试时间<=当前时间）
        QueryWrapper<MsgLog> queryWrapper = new QueryWrapper<>();
        //0 4
        queryWrapper.in("status",SystemConstant.MESSAGE_LOG_STATUS.SENDING,SystemConstant.MESSAGE_LOG_STATUS.EXCHANGE_QUEUE_FALL);
        queryWrapper.le("retryTime",new Date());
        List<MsgLog> msgLogs = msgLogMapper.selectList(queryWrapper);
        msgLogs.forEach(x ->{
            Integer retryCount = x.getRetryCount();
            String msgId = x.getMsgId();
            if(retryCount >= SystemConstant.RETRY_COUNT){
                //更新状态“投递失败”

                MsgLog msgLog = new MsgLog();
                msgLog.setMsgId(msgId);
                msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.SEND_FALL);
                msgLog.setUpdateTime(new Date());
                msgLogMapper.updateById(msgLog);
            }else{
                //否则，那么更新“重试时间=当前时间+1分钟”,“重试次数=重试次数+1”,“更新时间”
                MsgLog msgLog = new MsgLog();
                msgLog.setMsgId(msgId);
                Date date = new Date();
                msgLog.setRetryTime(DateUtil.addMinute(new Date(),1));
                msgLog.setRetryCount(retryCount+1);
                msgLog.setUpdateTime(date);
                msgLogMapper.updateById(msgLog);
                //重投
                CorrelationData correlationData = new CorrelationData(msgId);
                //发送消息
                rabbitTemplate.convertAndSend(x.getExchangeName(),x.getRountKey(),x.getMessage(),correlationData);

            }
        });
    }
}
