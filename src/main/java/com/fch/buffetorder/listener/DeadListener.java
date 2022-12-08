package com.fch.buffetorder.listener;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.config.RabbitConfig;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.service.OrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-12-07 23:37
 **/
@Component
public class DeadListener {

    @Autowired
    OrderService orderService;

    @RabbitListener(queues = RabbitConfig.DEAD_QUEUE)
    public void onMessage(Message message, Channel channel) {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            Order order = JSONObject.parseObject(json, Order.class);
            orderService.confirmPay(order);
        } finally {
            try {
                channel.basicAck(tag, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
