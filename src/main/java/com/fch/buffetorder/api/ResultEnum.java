package com.fch.buffetorder.api;

import lombok.Getter;

@Getter
public enum ResultEnum {
    OK(true, 200, "请求成功"),
    BAD_REQUEST(false, 400, "参数错误"),
    UNAUTHORIZED(false, 401, "要求用户的身份认证"),
    FORBIDDEN(false, 403, "服务器拒绝执行此请求"),
    NOT_FOUND(false, 404, "未找到");
    private final Boolean success;
    private final Integer code;
    private final String message;

    ResultEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
