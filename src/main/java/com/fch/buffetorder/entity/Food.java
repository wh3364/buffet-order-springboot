package com.fch.buffetorder.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Food {
    private Integer foodId;
    private String foodName;
    private String foodImg;
    private Integer foodCate;
    private Integer haveDetail;
    private String foodDetail;
    private String cateName;
    private String foodNote;
    private BigDecimal foodPrice;
    private BigDecimal foodWeight;
    private Integer isEnable;
}
