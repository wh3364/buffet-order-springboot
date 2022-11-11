package com.fch.buffetorder.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.WebNotify;
import com.fch.buffetorder.entity.*;
import com.fch.buffetorder.entity.detail.MultiDetail;
import com.fch.buffetorder.entity.detail.RadioDetail;
import com.fch.buffetorder.entity.orderbody.DM;
import com.fch.buffetorder.entity.orderbody.OrderBody;
import com.fch.buffetorder.mapper.AddressMapper;
import com.fch.buffetorder.mapper.FoodMapper;
import com.fch.buffetorder.mapper.OrderMapper;
import com.fch.buffetorder.mapper.UserMapper;
import com.fch.buffetorder.service.OrderService;
import com.fch.buffetorder.util.JsonUtil;
import com.fch.buffetorder.websocket.WebSocket;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    FoodMapper foodMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    WebSocket webSocket;

    @Autowired
    JsonUtil jsonUtil;

    @Override
    public JSONObject userCreateOrder(List<OrderBody> orderBodyList, Integer way, User user) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
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
            } else if (item.getHD() == 0) {
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
            return res;
        }
        if (way == 0) {
            order.setOrderGetNumb(jsonUtil.getOrderGetNum());
        } else if (way == 1) {
            Address address = new Address();
            address.setUserId(user.getUserId());
            address = addressMapper.queryAddressByUserId(address);
            if (address == null) {
                res.put("code", 2);
                res.put("msg", "请在个人中心添加地址");
                return res;
            }
            order.setOrderAddress(address.getAddress());
        }
        log.info("订单:{}", orderJsonInDbList);
        log.info("订单Json:{}", JSONObject.toJSONString(orderJsonInDbList));
        order.setUserId(user.getUserId());
        order.setOrderWay(way);
        order.setOrderCreateTime(new Date());
        order.setOrderJsonBody(JSONObject.toJSONString(orderJsonInDbList));
        order.setOrderState(0);
        order.setOrderNote("无");
        order.setOrderShouldPay(sum);
        if (orderMapper.createOrder(order) > 0) {
            res.put("order", order);
            res.put("code", 1);
            String title;
            if (order.getOrderWay() == 0) {
                title = "有新的店内订单";
            } else {
                title = "有新的外卖订单";
            }
            webSocket.sendMessage(JSONObject.toJSONString(new WebNotify(order.getOrderId(), title, "订单号:" + order.getOrderId(), WebNotify.Type.INFO, 0, false)));
        }
        return res;
    }

    @Override
    public Order queryOrderByOrderIdAndUserId(Order order) {
        order = orderMapper.queryOrderByOrderIdAndUserId(order);
        order.setOrderJsonBody(orderJsonBbToOrderRepJson(order.getOrderJsonBody()));
        return order;
    }

    @Override
    public List<Order> queryOrderListById(Order order) {
        List<Order> orderList = orderMapper.queryOrderListById(order);
        List<Order> resOrderList = new ArrayList<>();
        for (Order o : orderList) {
            o.setOrderJsonBody(orderJsonBbToOrderRepJson(o.getOrderJsonBody()));
            resOrderList.add(o);
        }
        return resOrderList;
    }

    @Override
    public PageInfo<Order> userQueryOrderListById(Order order, Integer pageNum, Integer pageSize) {
        List<Order> orderList;
        switch (order.getOrderState()) {
            case 0:
                PageHelper.startPage(pageNum, pageSize);
                orderList = orderMapper.userQueryOrderListDoingById(order);
                for (Order o : orderList) {
                    o.setOrderJsonBody(orderJsonBbToOrderRepJson(o.getOrderJsonBody()));
                }
                break;
            case 3:
                PageHelper.startPage(pageNum, pageSize);
                orderList = orderMapper.userQueryOrderListCompleteOrCancelById(order);
                for (Order o : orderList) {
                    o.setOrderJsonBody(orderJsonBbToOrderRepJson(o.getOrderJsonBody()));
                }
                break;
            case 4:
                PageHelper.startPage(pageNum, pageSize);
                orderList = orderMapper.userQueryOrderListCompleteOrCancelById(order);
                for (Order o : orderList) {
                    o.setOrderJsonBody(orderJsonBbToOrderRepJson(o.getOrderJsonBody()));
                }
                break;
            default:
                return null;
        }
        //resOrderList.sort((l1, l2) -> l2.getOrderCreateTime().compareTo(l1.getOrderCreateTime()));
        return new PageInfo<>(orderList);
    }

    @Override
    public JSONObject payOrder(Order order, User user) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "支付失败");
        order = orderMapper.queryOrderByOrderIdAndUserId(order);
        if (order == null) {
            res.put("msg", "订单不存在");
            return res;
        }
        if (order.getOrderState() != 0) {
            res.put("msg", "订单已付款或取消");
            return res;
        }
        user = userMapper.queryUserByOpenId(user);
        BigDecimal money = user.getMoney();
        BigDecimal shouldPay = order.getOrderShouldPay();
        if (money.compareTo(shouldPay) < 0 || money.compareTo(shouldPay) == 0) {
            res.put("msg", "余额不足");
            return res;
        }
        money = money.subtract(shouldPay);
        user.setMoney(money);

        order.setOrderState(1);
        order.setOrderRealPay(shouldPay);
        if (orderMapper.uploadOrderPay(order) + userMapper.uploadUserPay(user) > 1) {
            res.put("code", 1);
            res.put("msg", "支付成功");
            log.info("支付成功 钱{}￥ - 应付{}￥", money, shouldPay);
            webSocket.sendMessage(JSONObject.toJSONString(new WebNotify(order.getOrderId(), "订单支付成功", "订单号:" + order.getOrderId(), WebNotify.Type.SUCCESS, 0, false)));

        }
        return res;
    }

    @Override
    public JSONObject cancelOrder(Order order, User user) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "取消失败");
        order = orderMapper.queryOrderByOrderIdAndUserId(order);
        if (order == null) {
            res.put("msg", "订单不存在");
            return res;
        }
        if (order.getOrderState() != 0) {
            res.put("msg", "订单已付款或取消");
            return res;
        }
        if (!order.getUserId().equals(user.getUserId())) {
            res.put("msg", "这不是你的订单");
            return res;
        }
        order.setOrderState(4);
        if (orderMapper.uploadOrderCompleteOrCancel(order) > 0) {
            res.put("code", 1);
            res.put("msg", "取消成功");
            log.info("取消成功orderId{}", order.getOrderId());
            webSocket.sendMessage(JSONObject.toJSONString(new WebNotify(order.getOrderId(), "订单取消", "订单号:" + order.getOrderId(), WebNotify.Type.ERROR, 0, false)));
        }
        return res;
    }

    @Override
    public JSONObject completeOrder(Order order, User user) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("msg", "完成失败");
        order = orderMapper.queryOrderByOrderIdAndUserId(order);
        if (order == null) {
            res.put("msg", "订单不存在");
            return res;
        }
        if (order.getOrderState() != 2) {
            res.put("msg", "订单不是发货状态");
            return res;
        }
        if (!order.getUserId().equals(user.getUserId())) {
            res.put("msg", "这不是你的订单");
            return res;
        }
        order.setOrderState(3);
        if (orderMapper.uploadOrderCompleteOrCancel(order) > 0) {
            res.put("code", 1);
            res.put("msg", "完成成功");
            log.info("完成订单orderId{}", order.getOrderId());
            webSocket.sendMessage(JSONObject.toJSONString(new WebNotify(order.getOrderId(), "订单完成", "订单号:" + order.getOrderId(), WebNotify.Type.SUCCESS, 5000, false)));
        }
        return res;
    }

    @Override
    public PageInfo<Order> adminQueryOrdersByWayAndState(Order order, Date[] createTime, Integer pageNum, Integer pageSize) {
        List<Order> orders;
        Date startDate = createTime[0];
        Date endDate = createTime[1];
        Integer orderStatus = order.getOrderState();
        Integer orderWay = order.getOrderWay();
        switch (orderStatus) {
            //查询 3 4
            case -2:
                PageHelper.startPage(pageNum, pageSize);
                orders = orderMapper.adminQueryOrdersByWayAndStateCompleteAndCancel(orderWay, startDate, endDate);
                break;
            //查询 0 1 2
            case -1:
                PageHelper.startPage(pageNum, pageSize);
                orders = orderMapper.adminQueryOrdersByWayAndStateDefault(orderWay, startDate, endDate);
                break;
            default:
                PageHelper.startPage(pageNum, pageSize);
                orders = orderMapper.adminQueryOrdersByWayAndState(orderStatus, orderWay, startDate, endDate);
                break;
        }
        return new PageInfo<>(orders);
    }

    @Override
    public Order adminQueryOrderByOrderId(Order order) {
        order = orderMapper.adminQueryOrderByOrderId(order);
        order.setOrderJsonBody(orderJsonBbToOrderRepJson(order.getOrderJsonBody()));
        return order;
    }

    @Override
    public JSONObject goFood(Order order) {
        JSONObject res = new JSONObject();
        res.put("code", 0);
        res.put("message", "出餐失败");
        order = orderMapper.queryOrderByOrderIdAndUserId(order);
        if (order == null) {
            res.put("code", 0);
            res.put("message", "订单不存在");
            return res;
        }
        if (order.getOrderState() == 2 || order.getOrderState() == 3) {
            res.put("code", 0);
            res.put("message", "订单已出餐或完成");
        }
        if (order.getOrderState() == 4) {
            res.put("code", 0);
            res.put("message", "订单已取消");
        }
        if (order.getOrderState() == 0) {
            res.put("code", 0);
            res.put("message", "订单未付款");
        }
        if (order.getOrderState() == 1) {
            order.setOrderState(2);
            if (orderMapper.uploadOrderGoFood(order) > 0) {
                res.put("code", 200);
                res.put("message", "成功出餐");
            }
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
