package com.fch.buffetorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fch
 * @program BuffetOrder
 * @description 新增优惠券模板
 * @create 2023-03-18 11:00
 **/
@Data
public class CouponAddDto {

    @NotEmpty(message = "优惠券名称不能为空")
    private String name;
    // @NotEmpty(message = "优惠券LOGO不能为空")
    private String logo;
    @NotEmpty(message = "优惠券简介不能为空")
    private String intro;
    @Min(value = 1)
    @Max(value = 2)
    private Integer category;
    @Min(value = 1)
    @Max(value = 3)
    private Integer scope;
    private Integer scopeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    @Min(value = 1, message = "优惠券最少1张")
    private Integer couponCount;
    @Min(value = 1)
    @Max(value = 4)
    private Integer target;
    private Integer targetLevel;
    @Min(value = 1)
    @Max(value = 2)
    private Integer sendType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "开始时间不能为空")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotNull(message = "结束时间不能为空")
    private Date endTime;
    @NotNull(message = "偏移量不能为空")
    private BigDecimal limitMoney;
    @NotNull(message = "优惠不能为空")
    private BigDecimal discount;
}
