package com.linkmoretech.notice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Author: GFF
 * @Description: ${description}
 * @Date: 2019/7/12
 */
@Configuration
public class RabbitConfig {

    public static final String PUSH_FANOUT_EXCHANGE = "push_fanout_exchange";
    public static final String PUSH_FANOUT_QUEUE = "push_fanout_queue";


    @Bean
    public FanoutExchange pushFanoutExchange(){
        return new FanoutExchange(PUSH_FANOUT_EXCHANGE);
    }

    @Bean
    public Queue pushQueue(){
        return new Queue(PUSH_FANOUT_QUEUE);
    }

    @Bean
    public Binding pushFanoutBind(@Qualifier("pushQueue")Queue queue , @Qualifier("pushFanoutExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
    

    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
          RabbitTemplate template = new RabbitTemplate(connectionFactory);
          template.setMandatory(true);
          template.setMessageConverter(new SerializerMessageConverter());
          return template;
    }
}
