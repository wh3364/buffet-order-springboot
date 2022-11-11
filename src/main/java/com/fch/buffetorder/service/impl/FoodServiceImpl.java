package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.entity.detail.MultiDetail;
import com.fch.buffetorder.entity.detail.RadioDetail;
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

    @Override
    public JSONObject adminQueryAllFoods() {
        JSONObject res = new JSONObject();
        List<Food> foods = foodMapper.adminQueryAllFoods();
        if (foods.size() > 0) {
            res.put("data", foods);
            res.put("code", 200);
        } else {
            res.put("message", "查询失败");
            res.put("code", 0);
        }
        return res;
    }

    @Override
    public boolean isExistsByFoodId(Food food) {
        return foodMapper.queryFoodById(food) != null;
    }

    @Override
    public JSONObject updateFoodImg(Integer foodId, String imgPath) {
        JSONObject res = new JSONObject();
        Food food = new Food();
        food.setFoodId(foodId);
        food = foodMapper.adminQueryFoodById(food);
        food.setFoodImg(imgPath);
        if (foodMapper.updateFood(food) > 0) {
            res.put("data", food);
            res.put("code", 200);
        } else {
            res.put("message", "修改失败");
            res.put("code", 0);
        }
        return res;
    }

    @Override
    public JSONObject updateFood(Food food) {
        JSONObject res = new JSONObject();
        if (food.getHaveDetail() == 0) {
            return getUpdateRes(food, res);
        } else {
            String foodDetailStr = food.getFoodDetail();
            JSONObject foodDetail;
            try {
                foodDetail = getFoodDetail(foodDetailStr);
            } catch (Exception e) {
                e.printStackTrace();
                res.put("message", "修改失败");
                res.put("code", 0);
                return res;
            }
            food.setFoodDetail(foodDetail.toJSONString());
            return getUpdateRes(food, res);
        }
    }

    @Override
    public JSONObject addFood(Food food) {
        JSONObject res = new JSONObject();
        if (food.getHaveDetail() == 0) {
            return getAddRes(food, res);
        } else {
            String foodDetailStr = food.getFoodDetail();
            JSONObject foodDetail;
            try {
                foodDetail = getFoodDetail(foodDetailStr);
            } catch (Exception e) {
                e.printStackTrace();
                res.put("message", "添加失败");
                res.put("code", 0);
                return res;
            }
            food.setFoodDetail(foodDetail.toJSONString());
            return getAddRes(food, res);
        }
    }

    private JSONObject getFoodDetail(String foodDetailStr) {
        JSONObject foodDetail;
        foodDetail = JSONObject.parseObject(foodDetailStr);
        MultiDetail multiDetail = JSONObject.parseObject(foodDetail.getString("dM"), MultiDetail.class);
        List<RadioDetail> radioDetails = JSONArray.parseArray(foodDetail.getString("dR"), RadioDetail.class);
        if (radioDetails.get(0).getRS().size() > 0) {
            radioDetails.get(0).getRS().get(0).setS(1);
        }
        foodDetail.put("dM", multiDetail);
        foodDetail.put("dR", radioDetails);
        return foodDetail;
    }

    private JSONObject getAddRes(Food food, JSONObject res) {
        if (foodMapper.addFood(food) > 0) {
            res.put("code", 200);
        } else {
            res.put("code", 0);
            res.put("message", "添加失败");
        }
        return res;
    }

    private JSONObject getUpdateRes(Food food, JSONObject res) {
        if (foodMapper.updateFood(food) > 0) {
            res.put("data", food);
            res.put("code", 200);
        } else {
            res.put("code", 0);
            res.put("message", "更新失败");
        }
        return res;
    }
}
