package com.fch.buffetorder.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: BuffetOrder
 * @description: 仪表盘所需的数据
 * @CreatedBy: fch
 * @create: 2023-02-25 17:13
 **/
@Data
public class DashboardDto {
    private List<dRevenue> revenueList;
    private List<dFood> dFoodList;

    @Data
    static class dRevenue{
        private String name;
        private String value;
    }
    @Data
    static class dFood{
        private String name;
        private String value;
    }
}
