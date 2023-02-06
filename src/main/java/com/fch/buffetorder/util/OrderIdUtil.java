package com.fch.buffetorder.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: gotrip
 * @description:
 * @CreatedBy: fch
 * @create: 2023-01-24 13:21
 **/
@Component
public class OrderIdUtil {

    @Autowired
    private RedissonClient redissonClient;

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddhhmmssSSS");

    private static int tempTravelOrderId = 999;

    public String creatOrderIdLength21(Date now) {
        final RLock lock = redissonClient.getLock("redissonLock-creatOrderId");
        try {
            lock.lock(10000, TimeUnit.MILLISECONDS);
            tempTravelOrderId++;
            if (tempTravelOrderId > 9999) {
                tempTravelOrderId = 1000;
            }
            return getOrderId(tempTravelOrderId, now);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private String getOrderId(int temp, Date date) {
        return SIMPLE_DATE_FORMAT.format(date) + temp;
    }
}
