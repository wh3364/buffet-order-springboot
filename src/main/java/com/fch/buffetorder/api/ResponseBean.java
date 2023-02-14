package com.fch.buffetorder.api;

import lombok.Data;

@Data
public class ResponseBean {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

    // 构造方法设为私有
    private ResponseBean() {
    }

    private ResponseBean(ResultEnum resultEnum, Object data) {
        this.success = resultEnum.getSuccess();
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }

    private ResponseBean(ResultEnum resultEnum, String message) {
        this.success = resultEnum.getSuccess();
        this.code = resultEnum.getCode();
        this.message = message;
        this.data = null;
    }

    private ResponseBean(ResultEnum resultEnum, String message, Object data) {
        this.success = resultEnum.getSuccess();
        this.code = resultEnum.getCode();
        this.message = message;
        this.data = data;
    }

    public static ResponseBean ok(Object params) {
        return new ResponseBean(ResultEnum.OK, params);
    }

    public static ResponseBean ok(Object params, String message) {
        return new ResponseBean(ResultEnum.OK, message, params);
    }

    public static ResponseBean badRequest(String message) {
        return new ResponseBean(ResultEnum.BAD_REQUEST, message);
    }

    public static ResponseBean unauthorized(String message) {
        return new ResponseBean(ResultEnum.UNAUTHORIZED, message);
    }

    public static ResponseBean notFound(String message) {
        return new ResponseBean(ResultEnum.NOT_FOUND, message);
    }
}