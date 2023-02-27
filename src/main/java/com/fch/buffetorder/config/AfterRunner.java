package com.fch.buffetorder.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: BuffetOrder
 * @description: 项目启动后执行
 * @CreatedBy: fch
 * @create: 2023-02-25 16:45
 **/
@Slf4j
@Component
public class AfterRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("加载redis");
    }
}
