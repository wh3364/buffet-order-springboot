package com.fch.buffetorder.listener;

import com.alibaba.fastjson.JSONObject;
import com.fch.buffetorder.config.RabbitConfig;
import com.fch.buffetorder.entity.CouponTemplate;
import com.fch.buffetorder.entity.UserCoupon;
import com.fch.buffetorder.mapper.UserCouponMapper;
import com.fch.buffetorder.mapper.UserMapper;
import com.fch.buffetorder.util.DateUtil;
import com.fch.buffetorder.util.RedissonUtils;
import com.fch.buffetorder.util.ThreadPoolSingle;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fch
 * @program BuffetOrder
 * @description 监听优惠券队列
 * @create 2023-03-18 20:24
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class CouponListener {

    private final UserMapper userMapper;

    private final UserCouponMapper userCouponMapper;

    @RabbitListener(queues = RabbitConfig.COUPON_SYSTEM_QUEUE)
    public void onSystemMessage(Message message, Channel channel) {
        long tag = message.getMessageProperties().getDeliveryTag();
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        CouponTemplate couponTemplate = JSONObject.parseObject(json, CouponTemplate.class);
        // 优惠券作用的人群 和级别
        if (couponTemplate.getTarget().equals(CouponTemplate.Target.ALL_USER.getValue())) {
            // 因为没有用户模块 模拟获取这个等级的所有用户ID
            List<Integer> userIds = userMapper.queryAllUserId();
            Integer templateId = couponTemplate.getId();
            String templateKey = couponTemplate.getTemplateKey();
            // 上面的批量操作如果数据量太大 还是有问题
            // 继续优化 需要使用多线程并行处理
            // 需要先分片 每个分片1000条数据 使用线程池并行处理
            int batchs = userIds.size() / 1000;
            batchs = userIds.size() % 1000 == 0 ? batchs : batchs + 1;
            for (int i = 0; i < batchs; i++) {
                int start = i * 1000;
                List<Integer> pageIds = userIds.stream().skip(start).limit(1000).collect(Collectors.toList());
                List<UserCoupon> ucs = pageIds.stream().map(id -> new UserCoupon(templateId, id, templateKey)).collect(Collectors.toList());
                // 使用线程池处理
                ThreadPoolSingle.getInstance().poolExecutor.execute(() -> {
                    userCouponMapper.insertBatch(ucs);
                });
            }
        }
        try {
            channel.basicAck(tag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitConfig.COUPON_USER_QUEUE)
    public void onUserMessage(Message message, Channel channel) {
        long tag = message.getMessageProperties().getDeliveryTag();
        String json = new String(message.getBody(), StandardCharsets.UTF_8);
        CouponTemplate couponTemplate = JSONObject.parseObject(json, CouponTemplate.class);
        // 用户级别优惠券的 派发
        // 优惠券模板审核通过后 优惠券下发到Redis中 后续等待用户从Redis中领取
        Integer templateId = couponTemplate.getId();
        String key = "buffetorder:coupon:" + templateId;
        // 优惠券数量
        RedissonUtils.setList(key, couponTemplate.getCouponCount());
        // 可以领取优惠券的用户级别
        RedissonUtils.setList(key, couponTemplate.getTargetLevel());
        // 这个缓存key的过期时间应该跟优惠券模板的领取 结束时间一致
        RedissonUtils.expire(key, DateUtil.lastSeconds(couponTemplate.getExpireTime()));
        try {
            channel.basicAck(tag, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
