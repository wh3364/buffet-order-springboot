package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Food;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.entity.OrderJsonInDb;
import com.fch.buffetorder.entity.detail.MultiDetail;
import com.fch.buffetorder.entity.detail.RadioDetail;
import com.fch.buffetorder.entity.orderbody.DM;
import com.fch.buffetorder.entity.orderbody.OrderBody;
import com.fch.buffetorder.entity.User;
import com.fch.buffetorder.mapper.FoodMapper;
import com.fch.buffetorder.mapper.OrderMapper;
import com.fch.buffetorder.mapper.UserMapper;
import com.fch.buffetorder.service.OrderService;
import com.fch.buffetorder.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-21 21:51
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    UserMapper userMapper;

    @Resource
    OrderMapper orderMapper;

    @Resource
    FoodMapper foodMapper;

    @Autowired
    JsonUtil jsonUtil;

    @Override
    public Order getOrder(List<OrderBody> orderBodyList, Integer way, User user) {
        user = userMapper.queryUserIdByOpenId(user);
        Order order = new Order();
        List<OrderJsonInDb> orderJsonInDbList = new ArrayList<>();
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (OrderBody item : orderBodyList) {
            Food food = new Food();
            food.setFoodId(item.getId());
            food = foodMapper.queryFoodById(food);
            BigDecimal finalPrice = food.getFoodPrice();
            StringBuilder sb = new StringBuilder();
            Integer number = 1;
            if (item.getHD() == 1 && item.getM().size() > 0) {
                MultiDetail multiDetail = jsonUtil.getMultiDetailInDb(food);
                for (DM m :
                        item.getM()) {
                    finalPrice = finalPrice.add(multiDetail.getMS().get(m.getS()).getV());
                    sb.append(multiDetail.getMS().get(m.getS()).getN()).append(" ");
                }
            }
            if (item.getHD() == 1 && item.getR().size() > 0) {
                List<RadioDetail> radioDetails = jsonUtil.getRadioDetailList(food);
                for (int i = 0; i < radioDetails.size(); i++) {
                    finalPrice = finalPrice.add(radioDetails.get(i).getRS().get(item.getR().get(i).getS()).getV());
                    sb.append(radioDetails.get(i).getRS().get(item.getR().get(i).getS()).getN()).append(" ");
                }
            } else if (item.getHD() == 0){
                if (item.getNumb() == 0)
                    continue;
                finalPrice = finalPrice.multiply(BigDecimal.valueOf(item.getNumb()));
                number = item.getNumb();
                sb.append("无");
            }
            orderJsonInDbList.add(new OrderJsonInDb(food.getFoodId(), food.getFoodName(), sb.toString(), number, finalPrice));
            sum = sum.add(finalPrice);
        }
        if (orderJsonInDbList.size() == 0) {
            return null;
        }
        log.info("订单:{}", orderJsonInDbList);
        log.info("订单Json:{}", JSONObject.toJSONString(orderJsonInDbList));
        order.setUserId(user.getUserId());
        order.setOrderWay(way);
        order.setOrderAddress("地址");
        order.setOrderCreateTime(new Date());

        order.setOrderJsonBody(JSONObject.toJSONString(orderJsonInDbList));

        order.setOrderGetNumb(1);
        order.setOrderState(0);
        order.setOrderNote("无");
        order.setOrderShouldPay(sum);

        if (orderMapper.createOrder(order) > 0) {
            return order;

        }
        return null;
    }

    @Override
    public Order queryOrderById(Order order) {
        order = orderMapper.queryOrderById(order);
        order.setOrderJsonBody(orderJsonBbToOrderRepJson(order.getOrderJsonBody()));
        return order;
    }

    @Override
    public List<Order> queryOrderListByUserId(User user) {
        List<Order> orderList = orderMapper.queryOrderListByUserId(user);
        List<Order> resOrderList = new ArrayList<>();
        for (Order order : orderList) {
            order.setOrderJsonBody(orderJsonBbToOrderRepJson(order.getOrderJsonBody()));
            resOrderList.add(order);
        }
        return resOrderList;
    }

    @Override
    public JSONObject payOrder(Order order, User user) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "支付失败");
        order = orderMapper.queryOrderById(order);
        if (order == null) {
            res.put("msg", "订单不存在");
            return res;
        }
        if (order.getOrderState() != 0){
            res.put("msg", "订单已付款或取消");
            return res;
        }
        user = userMapper.queryUserByOpenId(user);
        BigDecimal money = user.getMoney();
        BigDecimal shouldPay = order.getOrderShouldPay();
        if (money.compareTo(shouldPay) < 0 || money.compareTo(shouldPay) == 0){
            res.put("msg", "余额不足");
            return res;
        }
        money = money.subtract(shouldPay);
        user.setMoney(money);

        order.setOrderState(1);
        order.setOrderRealPay(shouldPay);
        if (orderMapper.uploadOrderPay(order) + userMapper.uploadUserPay(user) > 1){
            res.put("code", 1);
            res.put("msg", "支付成功");
            log.info("支付成功 钱{}￥ - 应付{}￥", money, shouldPay);
        }
        return res;
    }

    private String orderJsonBbToOrderRepJson(String json) {
        JSONArray jsonArray = JSONArray.parseArray(json);
        jsonArray.getJSONObject(0);
        for (int i = 0; i < jsonArray.size(); i++) {
            Food food = jsonUtil.getFootFromOrderJsonInDb(jsonArray.getJSONObject(i));
            food = foodMapper.queryFoodImgUrlById(food);
            jsonArray.getJSONObject(i).put("img", food.getFoodImg());
        }
        return jsonArray.toJSONString();
    }
}
