package com.fch.buffetorder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-12-07 22:04
 **/
@Slf4j
@Configuration
public class RabbitConfig {

    @Resource
    private CachingConnectionFactory connectionFactory;

    public static final String ORDER_QUEUE = "order.queue";

    public static final String DEAD_QUEUE = "dead.queue";

    public static final String ORDER_EXCHANGE = "order.exchange";

    public static final String DEAD_EXCHANGE = "dead.exchange";

    public static final String ORDER_ROUTING_KEY = "order";

    public static final String DEAD_ROUTING_KEY = "dead";

    private static final long TTL = 1000 * 60 * 30;

    /**
     * 声明rabbittemplate 设置应答方式
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //设置消息发送格式为json
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        //消息发送到exchange回调 需设置：spring.rabbitmq.publisher-confirms=true
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        });
        //消息从exchange发送到queue失败回调  需设置：spring.rabbitmq.publisher-returns=true
        rabbitTemplate.setReturnsCallback((returnedMessage) -> {
            Message message = returnedMessage.getMessage();
            int replyCode = returnedMessage.getReplyCode();
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();
            log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
        });
        return rabbitTemplate;
    }

    @Bean
    public Queue getOrderQueue() {
        Map<String, Object> args = new HashMap<>();
        //声明死信交换机
        args.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //声明死信routing-key
        args.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
        //声明死信队列中的消息过期时间
        args.put("x-message-ttl", TTL);
        return new Queue(ORDER_QUEUE, true, false, false, args);
    }

    @Bean
    public Queue getDeadQueue() {
        return new Queue(DEAD_QUEUE, true);
    }

    @Bean
    public DirectExchange getOrderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange getDeadExchange() {
        return new DirectExchange(DEAD_EXCHANGE, true, false);
    }

    @Bean
    public Binding bindingOrder() {
        return BindingBuilder.bind(getOrderQueue()).to(getOrderExchange()).with(ORDER_ROUTING_KEY);
    }

    @Bean
    public Binding bindingDead() {
        return BindingBuilder.bind(getDeadQueue()).to(getDeadExchange()).with(DEAD_ROUTING_KEY);
    }

}
