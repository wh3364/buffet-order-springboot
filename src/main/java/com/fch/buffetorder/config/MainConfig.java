package com.fch.buffetorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @program: BuffetOrder
 * @description: 请求配置类
 * @CreatedBy: fch
 * @create: 2022-10-14 15:59
 **/
@Configuration
public class MainConfig {

    @Autowired
    private Environment env;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //超时设置
        factory.setReadTimeout(5000);//ms
        factory.setConnectTimeout(15000);//ms
        return factory;
    }

    @Bean
    public RedissonClient config(){
        //创建配置实例
        Config config=new Config();
        //可以设置传输模式为EPOLL，也可以设置为NIO等等
        //config.setTransportMode(TransportMode.NIO);
        //设置服务节点部署模式：集群模式；单一节点模式；主从模式；哨兵模式等等
        //config.useClusterServers().addNodeAddress(env.getProperty("redisson.host.config"),env.getProperty("redisson.host.config"));
        config.useSingleServer()
                .setAddress(env.getProperty("redisson.host.config"))
                .setKeepAlive(true);
        //创建并返回操作Redisson的客户端实例
        return Redisson.create(config);
    }

}
