package com.fch.buffetorder.service;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Detail;
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

    JSONObject adminQueryAllFoods();

    boolean isExistsByFoodId(Food food);

    JSONObject updateFoodImg(Integer foodId, String imgPath);

    JSONObject updateFood(Food food);

    JSONObject addFood(Food food);

    JSONObject queryAllDefault();

    JSONObject updateDetail(Detail detail);

    JSONObject addDetail(Detail detail);

    JSONObject deleteDetail(Detail detail);
}
