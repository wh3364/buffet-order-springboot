package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.entity.orderbody.OrderBody;
import com.fch.buffetorder.entity.User;
import com.fch.buffetorder.service.OrderService;
import com.fch.buffetorder.service.UserService;
import com.fch.buffetorder.util.JsonUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 订单控制
 * @CreatedBy: fch
 * @create: 2022-10-21 16:40
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("Order")
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JsonUtil jsonUtil;

    @PostMapping("Create")
    public ResponseEntity createOrder(@RequestBody() String json,
                                      @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.size() > 0) {
            if (jsonUtil.needReg(openId)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            User user = new User();
            user.setOpenId(openId);
            List<OrderBody> orders = jsonUtil.reqParamJsonToOrderBody(jsonObject.getJSONObject("data"));
            JSONObject resp = orderService.userCreateOrder(orders, jsonObject.getJSONObject("data").getInteger("way"), user);
            if (resp.getInteger("code") == 0) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            log.info("创建订单");
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("GetOrder")
    public ResponseEntity getOrder(@RequestBody String json,
                                   @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.size() > 0) {
            if (jsonUtil.needReg(openId)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            User user = new User();
            user.setOpenId(openId);
            user = userService.queryUserIdByOpenId(user);
            Order order = new Order();
            Integer orderId = jsonObject.getInteger("orderId");
            order.setOrderId(orderId);
            order.setUserId(user.getUserId());
            order = orderService.queryOrderByOrderIdAndUserId(order);
            JSONObject resp = new JSONObject();
            resp.put("order", order);
            log.info("查询订单");
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("GetOrderList")
    public ResponseEntity getOrderMiniList(@RequestBody String json,
                                           @RequestAttribute("openId") String openId,
                                           @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.size() > 0) {
            if (jsonUtil.needReg(openId)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            Integer orderState = jsonObject.getInteger("orderState");
            User user = new User();
            user.setOpenId(openId);
            user = userService.queryUserIdByOpenId(user);
            Order order = new Order();
            order.setOrderState(orderState);
            order.setUserId(user.getUserId());
            PageInfo orders = orderService.userQueryOrderListById(order, pageNum, pageSize);
            JSONObject resp = new JSONObject();
            resp.put("orders", orders);
            log.info("查询订单列表");
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("PayOrder")
    public ResponseEntity payOrder(@RequestBody String json,
                                   @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.size() > 0) {
            if (jsonUtil.needReg(openId)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            User user = new User();
            user.setOpenId(openId);
            user = userService.queryUserIdByOpenId(user);
            user.setOpenId(openId);
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setOrderId(jsonObject.getInteger("openId"));
            JSONObject resp = orderService.payOrder(order, user);
            log.info("支付订单");
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("CancelOrder")
    public ResponseEntity cancelOrder(@RequestBody String json,
                                      @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.size() > 0) {
            if (jsonUtil.needReg(openId)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            User user = new User();
            user.setOpenId(openId);
            user = userService.queryUserIdByOpenId(user);
            user.setOpenId(openId);
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setOrderId(jsonObject.getInteger("openId"));
            JSONObject resp = orderService.cancelOrder(order, user);
            log.info("取消订单");
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("CompleteOrder")
    public ResponseEntity completeOrder(@RequestBody String json,
                                        @RequestAttribute("openId") String openId) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (jsonObject.size() > 0) {
            if (jsonUtil.needReg(openId)) {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            User user = new User();
            user.setOpenId(openId);
            user = userService.queryUserIdByOpenId(user);
            user.setOpenId(openId);
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setOrderId(jsonObject.getInteger("openId"));
            JSONObject resp = orderService.completeOrder(order, user);
            log.info("完成订单");
            return new ResponseEntity(resp, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
