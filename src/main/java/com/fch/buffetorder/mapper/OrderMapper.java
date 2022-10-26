package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Order;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-21 20:31
 **/
public interface OrderMapper {
    int createOrder(Order order);

    int uploadOrderPay(Order order);

    Order queryOrderById(Order order);

    List<Order> queryOrderListById(Order order);

    List<Order> userQueryOrderListDoingById(Order order);

    List<Order> userQueryOrderListCompleteOrCancelById(Order order);
}
