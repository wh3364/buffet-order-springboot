package com.fch.buffetorder;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.entity.detail.MultiDetail;
import com.fch.buffetorder.entity.detail.RadioDetail;
import com.fch.buffetorder.service.AdminService;
import com.fch.buffetorder.util.RedisUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.redisson.api.RBucket;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class BuffetorderApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
//        MultiDetail multiDetail = new MultiDetail();
//        MultiDetail.M m1 = multiDetail.new M("这是配料A", BigDecimal.valueOf(1), 0);
//        MultiDetail.M m2 = multiDetail.new M("这是配料B", BigDecimal.valueOf(2), 0);
//        MultiDetail.M m3 = multiDetail.new M("这是配料C", BigDecimal.valueOf(3), 0);
//        List<MultiDetail.M> multis = new ArrayList<>();
//        multis.add(m1);
//        multis.add(m2);
//        multis.add(m3);
//        multiDetail.setMI("选择配料");
//        multiDetail.setMS(multis);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("dM", multiDetail);
//        log.info(jsonObject.toJSONString().length() + "");
//        log.info(jsonObject.toJSONString());
//
//
//        RadioDetail radioDetail = new RadioDetail();
//        RadioDetail.R r1 = radioDetail.new R("大份", BigDecimal.valueOf(2), 0);
//        RadioDetail.R r2 = radioDetail.new R("中份", BigDecimal.valueOf(1), 0);
//        RadioDetail.R r3 = radioDetail.new R("小份", BigDecimal.valueOf(0), 1);
//        List<RadioDetail.R> radios = new ArrayList<>();
//        radios.add(r3);
//        radios.add(r2);
//        radios.add(r1);
//        radioDetail.setRS(radios);
//        radioDetail.setRI("选择大小");
//        RadioDetail radioDetail2 = new RadioDetail();
//        RadioDetail.R r4 = radioDetail2.new R("不辣", BigDecimal.valueOf(0), 1);
//        RadioDetail.R r5 = radioDetail2.new R("微辣", BigDecimal.valueOf(0), 0);
//        RadioDetail.R r6 = radioDetail2.new R("特辣", BigDecimal.valueOf(0), 0);
//        List<RadioDetail.R> radios2 = new ArrayList<>();
//        radios2.add(r4);
//        radios2.add(r5);
//        radios2.add(r6);
//        radioDetail2.setRS(radios2);
//        radioDetail2.setRI("选择辣");
//        List<RadioDetail> radioDetails = new ArrayList<>();
//        radioDetails.add(radioDetail);
//        radioDetails.add(radioDetail2);
//        jsonObject.put("dR", radioDetails);
//        log.info(jsonObject.toJSONString().length() + "");
//        log.info(jsonObject.toJSONString());


//        JSONObject req = new JSONObject();
//        ReqFoot foot1 = new ReqFoot();
//        foot1.id = 1;
//        foot1.hD = 1;
//        ReqDm reqDm = new ReqDm();
//        ReqDr reqDr = new ReqDr();
//        List<Integer> indS = new ArrayList<>();
//        indS.add(0);
//        indS.add(1);
//        reqDm.mS = indS;
//        reqDr.rS = indS;
//        foot1.m = reqDm;
//        foot1.r = reqDr;
//        List<ReqFoot> foots = new ArrayList<>();
//        foots.add(foot1);
//        foots.add(foot1);
//        req.put("req", foots);
//        log.info(req.toJSONString().length() + "");
//        log.info(req.toJSONString());
    }

    @Autowired
    AdminService adminService;

    @Test
    void regAdmin(){
        Admin admin = new Admin();
        admin.setRole("admin");
        admin.setUsername("admin");
        admin.setUsername("admin");
        admin.setPassword("123456");
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        admin.setPassword(bcryptPasswordEncoder.encode(admin.getPassword()));
        adminService.insertAdmin(admin);
        log.info(admin.toString());
    }

}