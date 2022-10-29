package com.fch.buffetorder.service;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.entity.orderbody.OrderBody;
import com.fch.buffetorder.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-21 21:51
 **/
public interface OrderService {

    Order getOrder(List<OrderBody> orders, Integer way, User user);

    Order queryOrderByOrderIdAndUserId(Order order);

    List<Order> queryOrderListById(Order order);

    PageInfo<Order> userQueryOrderListById(Order order, Integer pageNum, Integer pageSize);

    JSONObject payOrder(Order order, User user);

    PageInfo<Order> adminQueryOrdersByWayAndState(Order order, Integer pageNum, Integer pageSize);

    JSONObject goFood(Order order);
}
