package com.fch.buffetorder.api;

import org.springframework.http.HttpStatus;

/**
 * @program: SdLibrary
 * @description: 返回Base
 * @CreatedBy: fch
 * @create: 2022-04-23 09:38
 **/
public class BaseResponse<T>{
    //状态码
    private HttpStatus status;
    //描述信息
    private String msg;
    //响应数据-采用泛型表示可以接受通用的数据类型
    private T data;
    //重载的构造方法一
    public BaseResponse() {

    }

    //重载的构造方法三
    public BaseResponse(HttpStatus status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public HttpStatus status() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
