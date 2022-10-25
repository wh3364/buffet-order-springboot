package com.fch.buffetorder.service;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.entity.orderbody.OrderBody;
import com.fch.buffetorder.entity.User;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-21 21:51
 **/
public interface OrderService {

    Order getOrder(List<OrderBody> orders, Integer way, User user);

    Order queryOrderById(Order order);

    List<Order> queryOrderListByUserId(User user);

    JSONObject payOrder(Order order, User user);
}
