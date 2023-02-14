package com.fch.buffetorder.controller;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.entity.Order;
import com.fch.buffetorder.service.AdminService;
import com.fch.buffetorder.service.OrderService;
import com.fch.buffetorder.websocket.WebSocket;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-10-27 22:57
 **/
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("Assistant")
public class AssistantController {

    @Autowired
    AdminService adminService;

    @Autowired
    OrderService orderService;

    @Autowired
    WebSocket webSocket;

    @GetMapping("Info")
    public ResponseBean getAdminInfo(HttpServletRequest request) {
        return adminService.getInfo(request.getHeader("token"));
    }

    @PostMapping("Logout")
    public ResponseBean adminLogout() {
        return ResponseBean.ok("success");
    }

    @GetMapping("GetOrderList")
    public ResponseBean getOrderList(@RequestParam Integer way, @RequestParam Integer state,
                                     @RequestParam String createTime,
                                     @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        String[] arr = createTime.split(",", 2);
        Date[] dates = new Date[2];
        for (int i = 0; i < arr.length; i++) {
            dates[i] = new Date(Long.parseLong(arr[i]));
        }
        Order order = new Order();
        order.setOrderWay(way);
        order.setOrderState(state);
        return orderService.adminQueryOrdersByWayAndState(order, dates, pageNum, pageSize);
    }

    @GetMapping("GetOrder")
    public ResponseBean getOrder(Order order) {
        return orderService.adminQueryOrderByOrderId(order);
    }

    @PostMapping("GoFood")
    public ResponseBean goFood(Order order) {
        return orderService.goFood(order);
    }
}
