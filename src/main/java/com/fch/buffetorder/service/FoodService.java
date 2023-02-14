package com.fch.buffetorder.service;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.entity.Detail;
import com.fch.buffetorder.entity.Food;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物服务类
 * @CreatedBy: fch
 * @create: 2022-10-16 23:21
 **/
public interface FoodService {
    List<Food> queryAllFoods();

    ResponseBean adminQueryAllFoods();

    boolean isExistsByFoodId(Food food);

    ResponseBean updateFoodImg(Food food, MultipartFile file, HttpServletRequest request);

    ResponseBean updateFood(Food food);

    ResponseBean addFood(Food food);

    ResponseBean queryAllDefault();

    ResponseBean updateDetail(Detail detail);

    ResponseBean addDetail(Detail detail);

    ResponseBean deleteDetail(Detail detail);
}
