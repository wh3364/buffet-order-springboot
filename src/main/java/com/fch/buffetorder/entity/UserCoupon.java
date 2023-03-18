package com.fch.buffetorder.entity;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 9.用户优惠券表(UserCoupon)实体类
 *
 * @author makejava
 * @since 2023-03-18 10:26:00
 */
@Data
public class UserCoupon implements Serializable {
    private static final long serialVersionUID = 768782405572207587L;

    private Integer id;
    /**
     * 优惠券模板ID
     */
    private Integer templateId;
    /**
     * 前端用户ID
     */
    private Integer userId;
    /**
     * 优惠券码
     */
    private String couponCode;
    /**
     * 优惠券分发时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date assignDate;
    /**
     * 优惠券状态 1:未使用 2:已使用 3:已过期
     */
    private Integer status;

    public UserCoupon(Integer templateId, Integer userId, String couponCode) {
        this.templateId = templateId;
        this.userId = userId;
        this.couponCode = couponCode;
    }

    public void setStatus(Status status) {
        this.status = status.getValue();
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Getter
    public enum Status {
        UN_USE(1, "未使用"),
        USED(2, "已使用"),
        OVERDUE(3, "已过期");
        private final Integer value;
        private final String name;
        Status(Integer value, String name) {
            this.name = name;
            this.value = value;
        }
    }
}

