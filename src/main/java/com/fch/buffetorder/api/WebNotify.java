package com.fch.buffetorder.api;

import lombok.Data;

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
    private boolean dangerouslyUseHTMLString;

    public WebNotify(String orderId, String title, String message, Type type, Integer duration, boolean dangerouslyUseHTMLString) {
        this.orderId = orderId;
        this.title = title;
        this.message = message;
        this.type = type.type();
        this.duration = duration;
        this.dangerouslyUseHTMLString = dangerouslyUseHTMLString;
    }

    public WebNotify() {
    }

    public enum Type{
        SUCCESS("success"), WARNING("warning"), INFO("info"), ERROR("error");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String type(){
            return type;
        }
    }
}
