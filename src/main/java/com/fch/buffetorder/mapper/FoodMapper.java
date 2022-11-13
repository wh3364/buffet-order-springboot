package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Detail;
import com.fch.buffetorder.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物Mapper
 * @CreatedBy: fch
 * @create: 2022-10-16 23:07
 **/
@Mapper
@Repository
public interface FoodMapper {
    int addFood(Food food);

    int updateFood(Food food);

    List<Food> queryAllFoods();

    Food queryFoodById(Food food);

    Food queryFoodImgUrlById(Food food);

    Food adminQueryFoodById(Food food);

    List<Food> adminQueryAllFoods();

    int addDetail(Detail detail);

    int deleteDetail(Detail detail);

    int updateDetail(Detail detail);

    List<Detail> queryAllDetail();

}
