package com.fch.buffetorder.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: BuffetOrder
 * @description: 存放进数据库的菜单JSON
 * @CreatedBy: fch
 * @create: 2022-10-22 10:40
 **/
@Data
public class OrderJsonInDb {

    //id
    private Integer id;
    //名
    private String na;
    //备注
    private String de;
    //个数
    private Integer nu;
    //钱
    private BigDecimal pr;

    public OrderJsonInDb(Integer id, String na, String de, Integer nu, BigDecimal pr) {
        this.id = id;
        this.na = na;
        this.de = de;
        this.nu = nu;
        this.pr = pr;
    }

    public OrderJsonInDb() {
    }
}
