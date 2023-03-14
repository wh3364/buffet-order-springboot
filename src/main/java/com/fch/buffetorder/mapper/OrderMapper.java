package com.fch.buffetorder.mapper;

import com.fch.buffetorder.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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

    int uploadOrderCompleteOrCancel(Order order);

    int uploadOrderGoFood(Order order);

    Order queryOrderByOrderIdAndUserId(Order order);

    List<Order> queryOrderListById(Order order);

    List<Order> userQueryOrderListDoingById(Order order);

    List<Order> userQueryOrderListCompleteOrCancelById(Order order);

    List<Order> adminQueryOrdersByWayAndStateDefault(@Param("orderWay") Integer orderWay,
                                                     @Param("startDate") Date startDate,
                                                     @Param("endDate") Date endDate);

    List<Order> adminQueryOrdersByWayAndStateCompleteAndCancel(@Param("orderWay") Integer orderWay,
                                                               @Param("startDate") Date startDate,
                                                               @Param("endDate") Date endDate);

    List<Order> adminQueryOrdersByWayAndState(@Param("orderState") Integer orderState,
                                              @Param("orderWay") Integer orderWay,
                                              @Param("startDate") Date startDate,
                                              @Param("endDate") Date endDate);

    Order adminQueryOrderByOrderId(Order order);

    List<Order> adminQueryFinishOrdersByCreatTime(Date start, Date end);

    Integer adminQueryFinishedByNow();

    Integer adminQueryUnfinishedByNow();
}
