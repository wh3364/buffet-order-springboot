package com.fch.buffetorder.service;

import com.fch.buffetorder.entity.Food;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物服务类
 * @CreatedBy: fch
 * @create: 2022-10-16 23:21
 **/
public interface FoodService {
    List<Food> queryAllFoods();
}
