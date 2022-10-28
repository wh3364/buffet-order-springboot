package com.fch.buffetorder.service.impl;

import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.mapper.FoodMapper;
import com.fch.buffetorder.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物服务接口实现类
 * @CreatedBy: fch
 * @create: 2022-10-16 23:22
 **/
@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodMapper foodMapper;

    @Override
    public List<Food> queryAllFoods() {
        return foodMapper.queryAllFoods();
    }
}
