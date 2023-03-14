package com.fch.buffetorder.controller;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @program: BuffetOrder
 * @description: 仪表盘后端控制器
 * @CreatedBy: fch
 * @create: 2023-02-25 17:24
 **/
@RestController
@RequestMapping("Dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseBean getData(@RequestParam Long startTime,
                                @RequestParam Long endTime){
        return dashboardService.getData(new Date(startTime), new Date(endTime));
    }

    @GetMapping("order")
    public ResponseBean getOrderDataByNow(){
        return dashboardService.getOrderDataByNow();
    }
}
