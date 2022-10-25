package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.entity.User;

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

    List<Order> queryOrderListByUserId(User user);

}
