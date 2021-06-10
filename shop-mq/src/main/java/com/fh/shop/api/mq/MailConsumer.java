package com.fh.shop.api.mq;

import com.alibaba.fastjson.JSON;
import com.fh.shop.api.util.MailUtil;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.mapper.IMsgLogMapper;
import com.fh.shop.mq.MQConstants;
import com.fh.shop.mq.MailMessage;
import com.fh.shop.po.MsgLog;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class MailConsumer {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private IMsgLogMapper msgLogMapper;


    @RabbitListener(queues = MQConstants.MAIL_QUEUE)
    public void handleMailMessage(String mailJson, Message message, Channel channel) throws IOException {
        MessageProperties messageProperties = message.getMessageProperties();
        MailMessage mailMessage = JSON.parseObject(mailJson, MailMessage.class);
        String msgId = mailMessage.getMsgId();
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            //进行幂等性处理
            MsgLog msgLog1 = msgLogMapper.selectById(msgId);
            if(msgLog1 != null){
                if(msgLog1.getStatus() == SystemConstant.MESSAGE_LOG_STATUS.SENDING || msgLog1.getStatus() == SystemConstant.MESSAGE_LOG_STATUS.EXCHANGE_QUEUE_FALL){
                    //如果状态为0 或者状态为4 但能在消息队列中获取这条消息 证明它就投递成功了 所以更改数据库的状态为投递成功
                    MsgLog msgLog = new MsgLog();
                    msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.SEND_SUCCESS);
                    msgLog.setUpdateTime(new Date());
                    msgLog.setMsgId(msgId);
                    msgLogMapper.updateById(msgLog);

                }
            }
            //随着数据越累越多 要做一个删除的功能 要么"人工删除" 要么"定时删除"
            //每天凌晨4点 定时将表中的状态为消费成功的记录删除
            if(msgLog1 != null || msgLog1.getStatus() == SystemConstant.MESSAGE_LOG_STATUS.CONSUME_SUCCESS){
                //响应 手工确认 删除消息队列中的消息
                channel.basicAck(deliveryTag,false);
                return;
            }
            mailUtil.sendMailHtml(mailMessage.getTo(),mailMessage.getTitle(),mailMessage.getConent());
            //更新数据库表中消息的状态为 消费成功

            MsgLog msgLog = new MsgLog();
            msgLog.setStatus(SystemConstant.MESSAGE_LOG_STATUS.CONSUME_SUCCESS);
            msgLog.setUpdateTime(new Date());
            msgLog.setMsgId(msgId);
            msgLogMapper.updateById(msgLog);
            //响应，删除队列中的消息

            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            e.printStackTrace();
            //要从队列中删除 是否重新进入队列 false 不进入队列 true 重新放入队列
            //因为这条消息已经在表中了 然后我们可以后台做一个功能
            //查询t_msg_log表中，状态为 "投递失败(重新投递)" "投递成功(人工处理)"的记录 让人工进行处理

            channel.basicNack(deliveryTag,false,false);
        }
    }


}
