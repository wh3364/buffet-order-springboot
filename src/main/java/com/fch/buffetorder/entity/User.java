package com.fch.buffetorder.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: BuffetOrder
 * @description: 用户类
 * @CreatedBy: fch
 * @create: 2022-10-14 22:47
 **/
@Data
public class User {
    private Integer userId;
    private String openId;
    private String nickName;
    private String avatarPath;
    private Integer isEnable;
    private BigDecimal money;
}
