package com.itcwt.ss.util;

import com.alibaba.fastjson.JSON;

import java.util.Objects;

/**
 * @author cwt
 * @create by cwt on 2018-10-18 14:54
 */
public class R {

    private Integer status;

    private String message;

    private Object data;

    private static final Integer DEFAULT_OK_STATUS = 200;
    private static final String DEFAULT_OK_MESSAGE = "成功";

    private static final Integer DEFAULT_ERROR_STATUS = 400;
    private static final String DEFAULT_ERROR_MESSAGE = "服务器内部异常";

    public static R ok() {
        return new R().setStatus(DEFAULT_OK_STATUS)
                .setMessage(DEFAULT_OK_MESSAGE)
                .setData(null);
    }

    public static R error() {
        return new R().setStatus(DEFAULT_ERROR_STATUS)
                .setMessage(DEFAULT_ERROR_MESSAGE)
                .setData(null);
    }

    public static R r(Integer status){
        return new R().setStatus(status);
    }

    public static R r(Integer status, String message){
        return new R().setStatus(status).
                setMessage(message);
    }

    public static R r(Integer status, String message, Objects data){
        return new R().setStatus(status).
                setMessage(message).
                setData(data);
    }

    public Integer getStatus() {
        return status;
    }

    public R setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public R setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public R setData(Object data) {
        this.data = data;
        return this;
    }
}
