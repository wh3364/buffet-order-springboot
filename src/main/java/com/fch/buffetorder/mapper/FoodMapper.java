package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Food;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物Mapper
 * @CreatedBy: fch
 * @create: 2022-10-16 23:07
 **/
@Mapper
public interface FoodMapper {
    List<Food> queryAllFoods();

    Food queryFoodById(Food food);

    Food queryFoodImgUrlById(Food food);
}
