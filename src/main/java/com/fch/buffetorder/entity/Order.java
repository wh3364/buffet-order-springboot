package com.fch.buffetorder.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {

    private Integer orderId;
    private Integer userId;
    private Integer orderWay;
    private String orderAddress;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date orderCreateTime;
    private String orderJsonBody;
    private String orderGetNumb;
    private Integer orderState;
    private String orderNote;
    private BigDecimal orderShouldPay;
    private BigDecimal orderRealPay;

}
