package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-21 20:31
 **/
@Repository
@Mapper
public interface OrderMapper {
    int createOrder(Order order);

    int uploadOrderPay(Order order);

    int uploadOrderGoFood(Order order);

    Order queryOrderByOrderIdAndUserId(Order order);

    List<Order> queryOrderListById(Order order);

    List<Order> userQueryOrderListDoingById(Order order);

    List<Order> userQueryOrderListCompleteOrCancelById(Order order);

    List<Order> adminQueryOrdersByWayAndState(Order order);
}
