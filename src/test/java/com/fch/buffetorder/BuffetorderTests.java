package com.fch.buffetorder;

import com.fch.buffetorder.config.RabbitConfig;
import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.service.AdminService;
import com.fch.buffetorder.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j
class BuffetorderTests {

    @Test
    void contextLoads() {
        log.info(UUID.randomUUID().toString().replace("-", ""));
    }

}