package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.WebNotify;
import com.fch.buffetorder.config.RabbitConfig;
import com.fch.buffetorder.service.AdminService;
import com.fch.buffetorder.websocket.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-11-01 14:21
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("Test")
public class TestController {

    @Autowired
    WebSocket webSocket;

    @Autowired
    AdminService adminService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("Test")
    public ResponseEntity test() {
        WebNotify webNotify = new WebNotify("0", "这是标题", "这是信息", WebNotify.Type.SUCCESS, 1500, false);
        webSocket.sendMessage(JSONObject.toJSONString(webNotify));
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_EXCHANGE, RabbitConfig.ORDER_ROUTING_KEY, "123");
        log.info("发送123");
        return new ResponseEntity(HttpStatus.OK);
    }
}
