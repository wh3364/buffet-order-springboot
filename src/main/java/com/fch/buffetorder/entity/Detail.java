package com.fch.buffetorder.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: BuffetOrder
 * @description:
 * @CreatedBy: fch
 * @create: 2022-11-12 15:35
 **/
@Data
public class Detail {
   private Integer detailId;
   private String detailName;
   private BigDecimal detailPrice;
   private Integer detailType;
}
