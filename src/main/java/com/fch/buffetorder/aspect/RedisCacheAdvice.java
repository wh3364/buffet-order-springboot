package com.fch.buffetorder.aspect;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.service.impl.CateServiceImpl;
import com.fch.buffetorder.service.impl.FoodServiceImpl;
import com.fch.buffetorder.util.RedisUtil;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: BuffetOrder
 * @description: redis缓存增强方法
 * @CreatedBy: fch
 * @create: 2023-02-02 18:47
 **/
@Aspect
@Component
public class RedisCacheAdvice {

    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("execution(* com.fch.buffetorder.service.impl.CateServiceImpl.add*(..))" +
            " || execution(* com.fch.buffetorder.service.impl.CateServiceImpl.update*(..))")
    private void clearAllCatesPoint() {
    }

    @AfterReturning(pointcut = "clearAllCatesPoint()", returning = "res")
    public void clearAllCates(JSONObject res) {
        if (res.getIntValue("code") == 200)
            redisUtil.delete(CateServiceImpl.ALL_CATES_KEY);
    }

    @Pointcut("execution(* com.fch.buffetorder.service.impl.FoodServiceImpl.add*(..))" +
            " || execution(* com.fch.buffetorder.service.impl.FoodServiceImpl.update*(..)) " +
            " || execution(* com.fch.buffetorder.service.impl.FoodServiceImpl.delete*(..))")
    private void clearAllFoodsPoint() {
    }

    @AfterReturning(pointcut = "clearAllFoodsPoint()", returning = "res")
    public void clearAllFoods(JSONObject res) {
        if (res.getIntValue("code") == 200)
            redisUtil.delete(FoodServiceImpl.ALL_FOODS_KEY);
    }
}
