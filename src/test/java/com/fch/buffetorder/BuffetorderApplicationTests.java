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

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BuffetorderApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    AdminService adminService;
    @Test
    void contextLoads() {
        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_EXCHANGE, RabbitConfig.ORDER_ROUTING_KEY, "123");
    }

    @Test
    void regAdmin(){
        Admin admin = new Admin();
        //admin.setRole("admin");
        admin.setUsername("admin");
        admin.setUsername("admin");
        admin.setPassword("123456");
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        admin.setPassword(bcryptPasswordEncoder.encode(admin.getPassword()));
        adminService.insertAdmin(admin);
        log.info(admin.toString());
    }

}