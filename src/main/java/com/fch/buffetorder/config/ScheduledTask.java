package com.fch.buffetorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @program: BuffetOrder
 * @description: 定时任务
 * @CreatedBy: fch
 * @create: 2023-02-25 19:04
 **/
@Configuration
@EnableScheduling
public class ScheduledTask {

    /**
     * 每天0点执行
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void getData(){

    }
}
