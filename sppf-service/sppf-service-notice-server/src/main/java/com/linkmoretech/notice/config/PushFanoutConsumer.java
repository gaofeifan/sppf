package com.linkmoretech.notice.config;

import com.linkmoretech.notice.controller.NoticeService;
import com.linkmoretech.notice.enums.MqMesEnum;
import com.linkmoretech.notice.vo.request.PushMesRequest;
import com.linkmoretech.notice.vo.request.PushMessageRequest;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/12
 */
@Component
@RabbitListener(queues = RabbitConfig.PUSH_FANOUT_QUEUE)
public class PushFanoutConsumer {

    @Resource
    private NoticeService noticeService;

    @RabbitHandler
    public void process(PushMesRequest messageRequest, Channel channel , Message message){
        try{
            noticeService.pushMesOne(messageRequest, MqMesEnum.YES.isFlag());
        }catch (Exception e){

        }
    }
}
