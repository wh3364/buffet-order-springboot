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
    private String title;
    private String message;
    private String type;
    private Integer duration;

    public WebNotify(String title, String message, String type, Integer duration) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.duration = duration;
    }

    public WebNotify() {
    }
}
