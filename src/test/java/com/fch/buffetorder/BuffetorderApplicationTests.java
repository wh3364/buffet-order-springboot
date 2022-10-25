package com.fch.buffetorder;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.detail.MultiDetail;
import com.fch.buffetorder.entity.detail.RadioDetail;
import com.fch.buffetorder.util.RedisUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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

@SpringBootTest
class BuffetorderApplicationTests {

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(BuffetorderApplicationTests.class);

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

    @Test
    void ridesT1() throws IOException {
//        if (redisUtil.setIfAbsent("123", "测试", 50000))
//            log.info("true");
//        else
//            log.info("fleas");
//        redisUtil.setStr("123", "测试", 5000);
//        log.info("redisson的配置信息：{}", redissonClient.getConfig().toJSON());
        RBucket<String> bucket = redissonClient.getBucket("test", StringCodec.INSTANCE);
        bucket.set("测试", 5000, TimeUnit.SECONDS);
        //redisUtil.setStr("test", "测试", 10000);
    }

    @Test
    void ridesT2() {
        //redisUtil.delete("test");
        //RBucket<String> bucket = redissonClient.getBucket("test", StringCodec.INSTANCE);
        log.info(redisUtil.getStr("test"));
    }

    @Test
    public void test6() throws Exception{
        //定义存储于缓存中间件Redis的Key
        final String key="myRedissonMapCache";
        //获取映射缓存RMapCache功能组件实例
        RMapCache<Integer, RMapDto> rMap=redissonClient.getMapCache(key);

        //本地缓存-功能组件的获取方法
        //RLocalCachedMap<Integer,RMapDto> rMap=redisson.getLocalCachedMap(key, LocalCachedMapOptions.defaults());

        //构造对象实例
        RMapDto dto1=new RMapDto(1,"map1");
        RMapDto dto2=new RMapDto(2,"map2");
        RMapDto dto3=new RMapDto(3,"map3");
        RMapDto dto4=new RMapDto(4,"map4");


        //将对象元素添加进MapCache组件中
        rMap.putIfAbsent(dto1.getId(),dto1);
        //将对象元素添加进MapCache组件中-有效时间 ttl 设置为 10秒钟
        rMap.putIfAbsent(dto2.getId(),dto2,10, TimeUnit.SECONDS);
        //将对象元素添加进MapCache组件中
        rMap.putIfAbsent(dto3.getId(),dto3);
        //将对象元素添加进MapCache组件中-有效时间 ttl 设置为 5秒钟
        rMap.putIfAbsent(dto4.getId(),dto4,5,TimeUnit.SECONDS);

        //获取MapCache组件的所有Key
        Set<Integer> set=rMap.keySet();
        //获取MapCache组件存储的所有元素
        Map<Integer,RMapDto> resMap=rMap.getAll(set);
        log.info("元素列表：{} ",resMap);

        //等待5秒钟-MapCache存储的数据元素列表
        Thread.sleep(5000);
        resMap=rMap.getAll(rMap.keySet());
        log.info("等待5秒钟-元素列表：{} ",resMap);

        //等待10秒钟-MapCache存储的数据元素列表
        Thread.sleep(10000);
        resMap=rMap.getAll(rMap.keySet());
        log.info("等待10秒钟-元素列表：{} ",resMap);
    }
}
@Data
@ToString
@EqualsAndHashCode
class RMapDto implements Serializable {
    private Integer id;  //id
    private String name; //名称

    public RMapDto() {
    }

    public RMapDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}