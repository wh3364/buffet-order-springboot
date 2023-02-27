package com.fch.buffetorder.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.entity.detail.MultiDetail;
import com.fch.buffetorder.entity.detail.RadioDetail;
import com.fch.buffetorder.entity.orderbody.OrderBody;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-21 21:41
 **/
public class JsonUtil {

    private static Integer orderNum = 0;

    /**
     * 获得请求参数的菜单Json
     * @param data
     * @return
     */
    public static List<OrderBody> reqParamJsonToOrderBody(JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("body");
        return JSONObject.parseArray(jsonArray.toJSONString(), OrderBody.class);
    }

    /**
     *
     * @param food
     * @return
     */
    public static MultiDetail getMultiDetailInDb(Food food) {
        JSONObject jsonObject = JSONObject.parseObject(food.getFoodDetail());
        return JSONObject.parseObject(jsonObject.getJSONObject("dM").toJSONString(), MultiDetail.class);
    }

    /**
     *
     * @param food
     * @return
     */
    public static List<RadioDetail> getRadioDetailList(Food food) {
        JSONObject jsonObject = JSONObject.parseObject(food.getFoodDetail());
        return  JSONArray.parseArray(jsonObject.getJSONArray("dR").toJSONString(), RadioDetail.class);
    }

    public static Food getFootFromOrderJsonInDb(JSONObject jsonObject){
        Food food = new Food();
        food.setFoodId(jsonObject.getInteger("id"));
        return food;
    }

    /**
     *
     * @return
     */
    public static String getOrderGetNum(){
        if (orderNum == 10000) {
            orderNum = 0;
        }
        orderNum++;
        return String.format("%04d", orderNum);
    }
}
