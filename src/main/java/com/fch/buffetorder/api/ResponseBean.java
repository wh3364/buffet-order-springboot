package com.fch.buffetorder.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseBean implements Serializable{
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;

    // 构造方法设为私有
    public ResponseBean() {
    }

    private ResponseBean(ResultEnum resultEnum, Object data) {
        this.success = resultEnum.getSuccess();
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = data;
    }

    private ResponseBean(ResultEnum resultEnum) {
        this.success = resultEnum.getSuccess();
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
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

    public static ResponseBean badRequest() {
        return new ResponseBean(ResultEnum.BAD_REQUEST);
    }

    public static ResponseBean unauthorized(String message) {
        return new ResponseBean(ResultEnum.UNAUTHORIZED, message);
    }

    public static ResponseBean unauthorized() {
        return new ResponseBean(ResultEnum.UNAUTHORIZED);
    }

    public static ResponseBean forbidden(String message) {
        return new ResponseBean(ResultEnum.FORBIDDEN, message);
    }

    public static ResponseBean forbidden() {
        return new ResponseBean(ResultEnum.FORBIDDEN);
    }

    public static ResponseBean notFound(String message) {
        return new ResponseBean(ResultEnum.NOT_FOUND, message);
    }

    public static ResponseBean notFound() {
        return new ResponseBean(ResultEnum.NOT_FOUND);
    }
}