package com.linkmoretech.notice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/12
 */
@Slf4j
@Component
public class RabbitConfirmCallback  implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
      log.info("生产者confirm");
      if(!ack){
          log.error("消息未成功到达mq服务器 cause:"+cause);
      }
      log.info("消息成功送达到mq服务器");
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息投递mq服务器失败原因回调"+exchange);
    }
}
