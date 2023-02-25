package com.fch.buffetorder.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: BuffetOrder
 * @description: 十五分类
 * @CreatedBy: fch
 * @create: 2022-10-15 16:37
 **/
@Data
public class Cate implements Serializable {
    Integer cateId;
    String cateName;
    Integer cateWeight;
    Integer isEnable;
}
