package com.fch.buffetorder.service;

import com.fch.buffetorder.api.ResponseBean;

import java.util.Date;

/**
 * @program: BuffetOrder
 * @description: 仪表盘逻辑接口
 * @CreatedBy: fch
 * @create: 2023-02-25 17:20
 **/
public interface DashboardService {
    ResponseBean getData(Date start, Date end);

    ResponseBean getOrderDataByNow();
}
