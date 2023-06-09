package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.aspect.Cache;
import com.fch.buffetorder.aspect.BeforeClearCache;
import com.fch.buffetorder.entity.Detail;
import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.entity.detail.MultiDetail;
import com.fch.buffetorder.entity.detail.RadioDetail;
import com.fch.buffetorder.mapper.FoodMapper;
import com.fch.buffetorder.service.FoodService;
import com.fch.buffetorder.util.RedisUtil;
import com.fch.buffetorder.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 食物服务接口实现类
 * @CreatedBy: fch
 * @create: 2022-10-16 23:22
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    public static final String ALL_FOODS_KEY = "buffetorder:food:foods";

    private final FoodMapper foodMapper;

    private final RequestUtil requestUtil;

    /**
     * @return List<Food>
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Cache(key = ALL_FOODS_KEY)
    public ResponseBean queryAllFoods() {
//        String foodsJson = Optional
//                .ofNullable(redisUtil.getStr(ALL_FOODS_KEY))
//                .orElseGet(() -> {
//                    String json = JSONObject.toJSONString(foodMapper.queryAllFoods());
//                    redisUtil.setStr(ALL_FOODS_KEY, json, 1000 * 60 * 60);
//                    return json;
//                });
        return ResponseBean.ok(foodMapper.queryAllFoods());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ResponseBean adminQueryAllFoods() {
        return ResponseBean.ok(foodMapper.adminQueryAllFoods());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean isExistsByFoodId(Food food) {
        return foodMapper.queryFoodById(food) != null;
    }

    @Override
    @BeforeClearCache(key = ALL_FOODS_KEY)
    public ResponseBean updateFoodImg(Food food, MultipartFile file, HttpServletRequest request) {
        String objectName = food.getFoodId() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String imgPath = requestUtil.uploadImg(file, objectName, "img/food/");
        if ("上传失败".equals(imgPath)){
            return ResponseBean.badRequest("imgPath");
        }
        food = foodMapper.adminQueryFoodById(food);
        food.setFoodImg(imgPath);
        return foodMapper.updateFood(food) > 0 ? ResponseBean.ok(food, "更新图片成功") : ResponseBean.badRequest("更新图片失败");
    }

    @Override
    @BeforeClearCache(key = ALL_FOODS_KEY)
    public ResponseBean updateFood(Food food) {
        if (food.getHaveDetail() == 1) {
            String foodDetailStr = food.getFoodDetail();
            JSONObject foodDetail;
            try {
                foodDetail = getFoodDetail(foodDetailStr);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseBean.badRequest("更新失败");
            }
            if (foodDetail == null) {
                food.setHaveDetail(0);
                food.setFoodDetail(null);
            } else {
                food.setFoodDetail(foodDetail.toJSONString());
            }
        }
        return foodMapper.updateFood(food) > 0 ? ResponseBean.ok(food, "更新成功") : ResponseBean.badRequest("更新失败");
    }

    @Override
    @BeforeClearCache(key = ALL_FOODS_KEY)
    public ResponseBean addFood(Food food) {
        if (food.getHaveDetail() == 1) {
            String foodDetailStr = food.getFoodDetail();
            JSONObject foodDetail;
            try {
                foodDetail = getFoodDetail(foodDetailStr);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseBean.badRequest("添加失败");
            }
            if (foodDetail == null) {
                food.setHaveDetail(0);
                food.setFoodDetail(null);
            } else {
                food.setFoodDetail(foodDetail.toJSONString());
            }
        }
        return foodMapper.addFood(food) > 0 ? ResponseBean.ok(food, "添加成功") : ResponseBean.badRequest("添加失败");

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ResponseBean queryAllDefault() {
        return ResponseBean.ok(foodMapper.queryAllDetail());
    }

    @Override
    public ResponseBean updateDetail(Detail detail) {
        return foodMapper.updateDetail(detail) > 0 ? ResponseBean.ok(detail, "更新成功") : ResponseBean.badRequest("更新失败");
    }

    @Override
    public ResponseBean addDetail(Detail detail) {
        return foodMapper.addDetail(detail) > 0 ? ResponseBean.ok(detail, "添加成功") : ResponseBean.badRequest("添加失败");
    }

    @Override
    public ResponseBean deleteDetail(Detail detail) {
        return foodMapper.deleteDetail(detail) > 0 ? ResponseBean.ok(detail, "删除成功") : ResponseBean.badRequest("删除失败");
    }

    private JSONObject getFoodDetail(String foodDetailStr) {
        JSONObject foodDetail = JSONObject.parseObject(foodDetailStr);
        MultiDetail multiDetail = JSONObject.parseObject(foodDetail.getString("dM"), MultiDetail.class);
        List<RadioDetail> radioDetails = JSONArray.parseArray(foodDetail.getString("dR"), RadioDetail.class);
        if (multiDetail.getMS().size() > 0) {
            foodDetail.put("dM", multiDetail);
        }
        if (radioDetails.size() > 0) {
            if (radioDetails.get(0).getRS().size() > 0) {
                radioDetails.get(0).getRS().get(0).setS(1);
            }
            foodDetail.put("dR", radioDetails);
        }
        if (multiDetail.getMS().size() == 0 && radioDetails.size() == 0) {
            return null;
        }
        return foodDetail;
    }

//    private JSONObject getAddRes(Food food, JSONObject res) {
//        if (foodMapper.addFood(food) > 0) {
//            res.put("code", 200);
//        } else {
//            res.put("code", 0);
//            res.put("message", "添加失败");
//        }
//        return res;
//    }

//    private JSONObject getUpdateRes(Food food, JSONObject res) {
//        if (foodMapper.updateFood(food) > 0) {
//            res.put("data", food);
//            res.put("code", 200);
//        } else {
//            res.put("code", 0);
//            res.put("message", "更新失败");
//        }
//        return res;
//    }
}
