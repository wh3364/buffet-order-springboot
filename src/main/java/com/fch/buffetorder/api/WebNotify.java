package com.fch.buffetorder.api;

import lombok.Data;
import lombok.Getter;

/**
 * @program: BuffetOrder
 * @description: 管理员通知
 * @CreatedBy: fch
 * @create: 2022-10-30 14:26
 **/
@Data
public class WebNotify {
    private String orderId;
    private String title;
    private String message;
    private String type;
    private Integer duration;
    private Boolean dangerouslyUseHTMLString;

    private WebNotify(String orderId, String title, String message, Type type, Integer duration, Boolean dangerouslyUseHTMLString) {
        this.orderId = orderId;
        this.title = title;
        this.message = message;
        this.type = type.getType();
        this.duration = duration;
        this.dangerouslyUseHTMLString = dangerouslyUseHTMLString;
    }

    private WebNotify(String orderId, String title, String message, Type type) {
        this.orderId = orderId;
        this.title = title;
        this.message = message;
        this.type = type.getType();
        this.duration = type.getDuration();
        this.dangerouslyUseHTMLString = type.getDangerouslyUseHTMLString();
    }

    private WebNotify() {
    }

    public static WebNotify success(String orderId, String title, String message, Integer duration, Boolean dangerouslyUseHTMLString){
        return new WebNotify(orderId, title, message, Type.SUCCESS, duration, dangerouslyUseHTMLString);
    }

    public static WebNotify success(String orderId, String title, String message){
        return new WebNotify(orderId, title, message, Type.SUCCESS);
    }

    public static WebNotify warning(String orderId, String title, String message, Integer duration, Boolean dangerouslyUseHTMLString){
        return new WebNotify(orderId, title, message, Type.WARNING, duration, dangerouslyUseHTMLString);
    }

    public static WebNotify warning(String orderId, String title, String message){
        return new WebNotify(orderId, title, message, Type.WARNING);
    }

    public static WebNotify info(String orderId, String title, String message, Integer duration, Boolean dangerouslyUseHTMLString){
        return new WebNotify(orderId, title, message, Type.INFO, duration, dangerouslyUseHTMLString);
    }

    public static WebNotify info(String orderId, String title, String message){
        return new WebNotify(orderId, title, message, Type.INFO);
    }

    public static WebNotify error(String orderId, String title, String message, Integer duration, Boolean dangerouslyUseHTMLString){
        return new WebNotify(orderId, title, message, Type.ERROR, duration, dangerouslyUseHTMLString);
    }

    public static WebNotify error(String orderId, String title, String message){
        return new WebNotify(orderId, title, message, Type.ERROR);
    }

    @Getter
    public enum Type{
        SUCCESS("success"), WARNING("warning"), INFO("info"), ERROR("error");

        private final String type;
        private final Integer duration = 10000;
        private final Boolean dangerouslyUseHTMLString = false;

        Type(String type) {
            this.type = type;
        }
    }
}
