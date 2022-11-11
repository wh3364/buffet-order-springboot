package com.fch.buffetorder.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.entity.User;
import com.fch.buffetorder.entity.detail.MultiDetail;
import com.fch.buffetorder.entity.detail.RadioDetail;
import com.fch.buffetorder.entity.orderbody.OrderBody;
import com.fch.buffetorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-21 21:41
 **/
@Component
public class JsonUtil {

    private static Integer orderNum = 0;

    @Autowired
    private UserService userService;


//    public JSONObject filterRequestJson(String json){
//        JSONObject req = JSONObject.parseObject(json);
//        JSONObject res = new JSONObject();
//        String sessionKey = req.getString("session_key");
//        String code = req.getString("code");
//        String openId;
//        if (StringUtils.hasText(sessionKey)) {
//            openId = openIdUtil.getOpenIdFromSession(sessionKey);
//            if (StringUtils.hasText(openId)){
//                res.put("code", 1);
//                res.put("openId", openId);
//                res.put("session_key", sessionKey);
//                res.put("needReg", needReg(openId));
//                log.info("从session获取OpenID{}", res.getString("openId"));
//                return res;
//            }
//            else {
//                res.put("code", 2);
//                res.put("msg", "需要更新session");
//                log.info("更新session");
//                return res;
//            }
//        }
//        if (StringUtils.hasText(code)) {
//            JSONObject openIdRes = openIdUtil.getOpenId(code);
//            if (openIdRes.getBoolean("flag")){
//                res.put("code", 1);
//                res.put("openId", openIdRes.getString("openId"));
//                res.put("session_key", openIdRes.getString("session_key"));
//                res.put("needReg", needReg(openIdRes.getString("openId")));
//                log.info("从微信服务器获取OpenID");
//                return res;
//            }
//            else {
//                res.put("code", 0);
//                res.put("msg", openIdRes.get("msg"));
//                return res;
//            }
//        }
//        res.put("code", 0);
//        res.put("msg", "参数错误");
//        return res;
//    }

    public boolean needReg(String openId){
        User user = new User();
        user.setOpenId(openId);
        return !userService.isExistByOpenId(user);
    }

    /**
     * 获得请求参数的菜单Json
     * @param data
     * @return
     */
    public List<OrderBody> reqParamJsonToOrderBody(JSONObject data) {
        JSONArray jsonArray = data.getJSONArray("body");
        return JSONObject.parseArray(jsonArray.toJSONString(), OrderBody.class);
    }

    /**
     *
     * @param food
     * @return
     */
    public MultiDetail getMultiDetailInDb(Food food) {
        JSONObject jsonObject = JSONObject.parseObject(food.getFoodDetail());
        return JSONObject.parseObject(jsonObject.getJSONObject("dM").toJSONString(), MultiDetail.class);
    }

    /**
     *
     * @param food
     * @return
     */
    public List<RadioDetail> getRadioDetailList(Food food) {
        JSONObject jsonObject = JSONObject.parseObject(food.getFoodDetail());
        return  JSONArray.parseArray(jsonObject.getJSONArray("dR").toJSONString(), RadioDetail.class);
    }

    public Food getFootFromOrderJsonInDb(JSONObject jsonObject){
        Food food = new Food();
        food.setFoodId(jsonObject.getInteger("id"));
        return food;
    }

    /**
     *
     * @return
     */
    public String getOrderGetNum(){
        if (orderNum == 10000) {
            orderNum = 0;
        }
        orderNum++;
        return String.format("%04d", orderNum);
    }
}
