package com.fch.buffetorder.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author fch
 * @program BuffetOrder
 * @description 优惠券审核Dto
 * @create 2023-03-18 11:07
 **/
@Data
public class CouponAuditDto {
    // 优惠券模板ID
    @NotNull(message = "id不能为空")
    private Integer id;
    // 创建人ID 后台内部员工
    private Integer aid;
    // 优惠券状态 flag 2通过 3拒绝
    @Min(2)
    @Max(3)
    private Integer flag;
    // 审核意见
    private String info;
}

