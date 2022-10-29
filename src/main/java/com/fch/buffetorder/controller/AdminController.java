package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.service.OrderService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-27 22:57
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("Admin")
public class AdminController {

    @Autowired
    OrderService orderService;

    @PostMapping("login")
    public ResponseEntity adminLogin(){
        JSONObject r = new JSONObject();
        JSONObject resp = new JSONObject();
        r.put("token", "admin-token");
        resp.put("code", 20000);
        resp.put("data", r);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @GetMapping("Info")
    public ResponseEntity getAdminInfo(){
        JSONObject r = new JSONObject();
        JSONObject resp = new JSONObject();
        r.put("roles", "admin");
        r.put("introduction", "I am a super administrator");
        r.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        r.put("name", "Super Admin");
        resp.put("code", 20000);
        resp.put("data", r);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @PostMapping("Logout")
    public ResponseEntity adminLogout(){
        JSONObject resp = new JSONObject();
        resp.put("code", 20000);
        resp.put("data", "success");
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    /**
     * 这是一个测试接口
     * @return
     */
    @GetMapping("List")
    public ResponseEntity getList(){
        JSONArray rs = new JSONArray();
        JSONObject r = new JSONObject();
        JSONObject data = new JSONObject();
        r.put("id", "id");
        r.put("title", "title");
        r.put("author", "author");
        r.put("display_time", "display_time");
        r.put("pageviews", "pageviews");
        r.put("status", "published");
        rs.add(r);
        JSONObject resp = new JSONObject();
        resp.put("code", 20000);
        data.put("total", 1);
        data.put("items", rs);
        resp.put("data", data);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    @GetMapping("GetOrderList")
    public ResponseEntity getOrderList(@RequestParam Integer way, @RequestParam Integer state,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize){
        Order order = new Order();
        order.setOrderWay(way);
        order.setOrderState(state);
        PageInfo<Order> orders = orderService.adminQueryOrdersByWayAndState(order, pageNum, pageSize);
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @GetMapping("GetOrder")
    public ResponseEntity getOrder(@RequestParam Integer orderId, @RequestParam Integer userId){
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(userId);
        order = orderService.queryOrderByOrderIdAndUserId(order);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @PostMapping("GoFood")
    public ResponseEntity goFood(@RequestParam Integer orderId, @RequestParam Integer userId){
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(userId);
        JSONObject resp = orderService.goFood(order);
        return new ResponseEntity(resp, HttpStatus.OK);
    }
}
