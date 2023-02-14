package com.fch.buffetorder.service;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.entity.orderbody.OrderBody;
import com.fch.buffetorder.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-21 21:51
 **/
public interface OrderService {

    JSONObject userCreateOrder(List<OrderBody> orders, Integer way, User user);

    Order queryOrderByOrderIdAndUserId(Order order);

    List<Order> queryOrderListById(Order order);

    PageInfo<Order> userQueryOrderListById(Order order, Integer pageNum, Integer pageSize);

    JSONObject payOrder(Order order, User user);

    JSONObject cancelOrder(Order order, User user);

    JSONObject completeOrder(Order order, User user);

    void confirmPay(Order order);

    ResponseBean adminQueryOrdersByWayAndState(Order order, Date[] createTime, Integer pageNum, Integer pageSize);

    ResponseBean adminQueryOrderByOrderId(Order order);

    ResponseBean goFood(Order order);
}
