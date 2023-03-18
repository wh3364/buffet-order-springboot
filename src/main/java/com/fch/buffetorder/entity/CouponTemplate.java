package com.fch.buffetorder.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 8.优惠券模板表(CouponTemplate)实体类
 *
 * @author makejava
 * @since 2023-03-18 10:29:51
 */
@Data
public class CouponTemplate implements Serializable {
    private static final long serialVersionUID = 123306620986278874L;

    private Integer id;
    /**
     * 状态：1.未审核 2.审核通过 3.审核失败
     */
    private Integer flag;
    /**
     * 名字
     */
    private String name;

    private String logo;
    /**
     * 简介
     */
    private String intro;
    /**
     * 种类: 1-满减；2-折扣
     */
    private Integer category;
    /**
     * 使用范围：1-单品；2-商品类型；3-全品
     */
    private Integer scope;
    /**
     * 对应的id：单品id；商品类型id；全品为0
     */
    private Integer scopeId;
    /**
     * 优惠券发放结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;
    /**
     * 优惠券发放数量
     */
    private Integer couponCount;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 创建人的ID，后台内部员工
     */
    private Integer userId;
    /**
     * 审核意见
     */
    private String userAudit;
    /**
     * 优惠券模板的识别码(有一定的识别度)
     */
    private String templateKey;
    /**
     * 优惠券作用的人群：1-全体；2-会员等级 3-新用户 4-收费会员
     */
    private Integer target;
    /**
     * 用户等级要求，默认0
     */
    private Integer targetLevel;
    /**
     * 发放类型：1.用户领取 2.系统发放
     */
    private Integer sendType;
    /**
     * 优惠券生效日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 优惠券失效日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 优惠券可以使用的金额，满减、满折等
     */
    private BigDecimal limitMoney;
    /**
     * 减免或折扣
     */
    private BigDecimal discount;

    public void setFlag(Flag flag) {
        this.flag = flag.getValue();
    }

    public void setCategory(Category category) {
        this.category = category.getValue();
    }

    public void setTarget(Target target) {
        this.target = target.getValue();
    }

    public void setSendType(SendType sendType) {
        this.sendType = sendType.getValue();
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    @Getter
    public enum Flag {
        AUDITING(1, "审核中"),
        PASS(2, "审核通过"),
        UN_PASS(3, "审核未通过");
        private final Integer value;
        private final String name;
        Flag(Integer value, String name) {
            this.name = name;
            this.value = value;
        }
    }

    @Getter
    public enum Category {
        SINGLE_ITEM(1, "单品"),
        TYPE_ITEM(2, "商品类型"),
        ALL_ITEM(3, "全品");
        private final Integer value;
        private final String name;
        Category(Integer value, String name) {
            this.name = name;
            this.value = value;
        }
    }

    @Getter
    public enum Target {
        ALL_USER(1, "全体"),
        LEVEL_USER(2, "会员等级"),
        NEW_USER(3, "新用户"),
        VIP_USER(4, "收费会员");
        private final Integer value;
        private final String name;
        Target(Integer value, String name) {
            this.name = name;
            this.value = value;
        }
    }

    @Getter
    public enum SendType {
        USER_SEND(1, "用户领取"),
        SYSTEM_SEND(2, "系统发放");
        private final Integer value;
        private final String name;
        SendType(Integer value, String name) {
            this.name = name;
            this.value = value;
        }
    }

}

