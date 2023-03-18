package com.fch.buffetorder.service.impl;

import com.fch.buffetorder.api.ResponseBean;
import com.fch.buffetorder.config.RabbitConfig;
import com.fch.buffetorder.dto.CouponAddDto;
import com.fch.buffetorder.dto.CouponAuditDto;
import com.fch.buffetorder.entity.Admin;
import com.fch.buffetorder.entity.CouponTemplate;
import com.fch.buffetorder.mapper.AdminMapper;
import com.fch.buffetorder.mapper.CouponTemplateMapper;
import com.fch.buffetorder.service.CouponService;
import com.fch.buffetorder.util.SnowFlowUtils;
import com.fch.buffetorder.util.ThreadLocalUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

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

    private final CouponTemplateMapper couponTemplateMapper;

    private final AdminMapper adminMapper;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public ResponseBean addCoupon(CouponAddDto dto) {
        String username = Optional.ofNullable(ThreadLocalUtils.get("username")).map(Object::toString).orElseThrow(() -> new IllegalArgumentException("身份异常"));
        Admin admin = new Admin();
        admin.setUsername(username);
        admin = adminMapper.queryAdminByUsername(admin);
        CouponTemplate couponTemplate = new CouponTemplate();
        BeanUtils.copyProperties(dto, couponTemplate);
        couponTemplate.setUserId(admin.getAdminId());
        couponTemplate.setCreateTime(new Date());
        couponTemplate.setFlag(CouponTemplate.Flag.AUDITING);
        couponTemplate.setTemplateKey(String.valueOf(SnowFlowUtils.getInstance().nextId()));
        return couponTemplateMapper.insert(couponTemplate) > 0 ? ResponseBean.ok(couponTemplate, "提交审核成功") : ResponseBean.badRequest("提交审核失败");
    }

    @Override
    public ResponseBean getCouponByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return ResponseBean.ok(new PageInfo<>(couponTemplateMapper.queryAll()));
    }

    @Override
    public ResponseBean getCouponById(Integer id) {
        return ResponseBean.ok(couponTemplateMapper.queryById(id));
    }

    @Override
    public ResponseBean audit(CouponAuditDto dto) {
        // 根据模板ID查询优惠券
        CouponTemplate couponTemplate = couponTemplateMapper.queryById(dto.getId());
        // 校验优惠券模板的状态
        if (Objects.isNull(couponTemplate)) {
            return ResponseBean.badRequest("审核失败");
        }
        if (!couponTemplate.getFlag().equals(CouponTemplate.Flag.AUDITING.getValue())) {
            return ResponseBean.badRequest("非法造作");
        }
        // 开始审核优惠券 更新优惠券模板
        couponTemplate.setFlag(dto.getFlag());
        couponTemplate.setUserId(dto.getAid());
        couponTemplate.setUserAudit(dto.getInfo());
        if (couponTemplateMapper.update(couponTemplate) > 0 && couponTemplate.getFlag().equals(CouponTemplate.Flag.PASS.getValue())) {
            // 发放优惠券，先写第一版本直接操作数据库的
            // 本质就是到用户优惠券表中新增数据
            // 获取给哪些用户发放优惠券

            // 把优惠券模板发送到MQ消息队列中

            // 路由key
            String rk = "";
            if (couponTemplate.getSendType().equals(CouponTemplate.SendType.SYSTEM_SEND.getValue())) {
                // 系统优惠券
                rk = RabbitConfig.COUPON_SYSTEM_ROUTING_KEY;
            } else if (couponTemplate.getSendType().equals(CouponTemplate.SendType.USER_SEND.getValue())) {
                // 用户优惠券
                rk = RabbitConfig.COUPON_USER_ROUTING_KEY;
            }
            // 发布消息到MQ
            rabbitTemplate.convertAndSend(RabbitConfig.COUPON_EXCHANGE, rk, couponTemplate);
            return ResponseBean.ok("优惠券审核通过");
        } else {
            return ResponseBean.ok("优惠券审核拒绝");
        }
    }
}
