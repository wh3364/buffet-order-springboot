package com.fch.buffetorder.listener;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.config.RabbitConfig;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class DeadListener {

    private final OrderService orderService;

    @RabbitListener(queues = RabbitConfig.DEAD_QUEUE)
    public void onMessage(Message message, Channel channel) {
        try {
            long tag = message.getMessageProperties().getDeliveryTag();
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            Order order = JSONObject.parseObject(json, Order.class);
            orderService.confirmPay(order);
            channel.basicAck(tag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
