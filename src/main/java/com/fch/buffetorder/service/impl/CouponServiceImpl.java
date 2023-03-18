package com.fch.buffetorder.service.impl;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.dto.CouponAddDto;
import com.fch.buffetorder.dto.CouponAuditDto;
import com.fch.buffetorder.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fch
 * @program BuffetOrder
 * @description 优惠券逻辑实现
 * @create 2023-03-18 10:58
 **/
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    @Override
    public ResponseBean addCoupon(CouponAddDto dto) {
        return null;
    }

    @Override
    public ResponseBean getCouponByPage(Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public ResponseBean getCouponById(Integer id) {
        return null;
    }

    @Override
    public ResponseBean audit(CouponAuditDto dto) {
        return null;
    }
}
