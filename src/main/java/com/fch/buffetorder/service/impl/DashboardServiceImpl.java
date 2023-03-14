package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.mapper.FoodMapper;
import com.fch.buffetorder.mapper.OrderMapper;
import com.fch.buffetorder.service.DashboardService;
import com.fch.buffetorder.util.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: BuffetOrder
 * @description: 仪表盘接口实现类
 * @CreatedBy: fch
 * @create: 2023-02-25 17:21
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final OrderMapper orderMapper;

    private final FoodMapper foodMapper;

    @Override
    public ResponseBean getData(Date start, Date end) {
        List<Order> orderList = orderMapper.adminQueryFinishOrdersByCreatTime(start, end);
        Map<String, List<Order>> map = orderList.stream().collect(Collectors.groupingBy(o -> o.getOrderCreateTime().toInstant().truncatedTo(ChronoUnit.DAYS).toString().split("T")[0]));
        Map<String, BigDecimal> revenueMap = new TreeMap<>();
        List<RevenueDto> revenueDtoList = new ArrayList<>();
        Map<String, Integer> hotFoodMap = new HashMap<>();
        List<HotFoodDto> hotFoodDtoList = new ArrayList<>();
        map.forEach((key, value) -> value
                .forEach(o -> {
                    if (revenueMap.containsKey(key)){
                        revenueMap.put(key, revenueMap.get(key).add(o.getOrderRealPay()));
                    }else {
                        revenueMap.put(key, o.getOrderRealPay());
                    }
                }));
        map.forEach((key, value) -> value
                .forEach(o -> {
                    JSONArray jsonArray = JSONArray.parseArray(o.getOrderJsonBody());
                    for (int i = 0; i < jsonArray.size(); i++) {
                        Food food = JsonUtil.getFootFromOrderJsonInDb(jsonArray.getJSONObject(i));
                        food = foodMapper.queryFoodById(food);
                        log.info(String.valueOf(food));
                        if (hotFoodMap.containsKey(food.getFoodName())) {
                            hotFoodMap.put(food.getFoodName(), hotFoodMap.get(food.getFoodName()) + 1);
                        } else {
                            hotFoodMap.put(food.getFoodName(), 1);
                        }
                    }
                }));
        Map<String, Object> res = new HashMap<>();
        revenueMap.forEach((key, value) -> revenueDtoList.add(new RevenueDto(key, value)));
        hotFoodMap.forEach((key, value) -> hotFoodDtoList.add(new HotFoodDto(key, value)));
        res.put("revenue", revenueDtoList);
        res.put("hotFood", hotFoodDtoList);
        return ResponseBean.ok(res);
    }

    @Override
    public ResponseBean getOrderDataByNow() {
        Integer finishedCount = orderMapper.adminQueryFinishedByNow();
        Integer unfinishedCount = orderMapper.adminQueryUnfinishedByNow();
        Map<String, Integer> res = new HashMap<>();
        res.put("finishedCount", finishedCount);
        res.put("unfinishedCount", unfinishedCount);
        return ResponseBean.ok(res);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class RevenueDto{
        private String data;
        private BigDecimal revenue;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class HotFoodDto {
        private String name;
        private Integer num;
    }
}
