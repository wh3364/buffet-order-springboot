package com.fch.buffetorder.service;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.dto.CouponAddDto;
import com.fch.buffetorder.dto.CouponAuditDto;

/**
 * @author fch
 * @program BuffetOrder
 * @description 优惠券逻辑接口
 * @create 2023-03-18 10:58
 **/
public interface CouponService {
    ResponseBean addCoupon(CouponAddDto dto);

    ResponseBean getCouponByPage(Integer pageNum, Integer pageSize);

    ResponseBean getCouponById(Integer id);

    ResponseBean audit(CouponAuditDto dto);
}
