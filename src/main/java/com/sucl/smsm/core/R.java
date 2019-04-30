package com.sucl.smsm.core;

import lombok.Data;

/**
 *
 * @author sucl
 * @date 2019/4/29
 */
@Data
public class R {
    public static final String SUCCESS = "0000";
    public static final String FAILURE = "9999";

    private String code;
    private String info;
    private Object data;

    private R(){}

    public static R of(String code ,String message) {
        R r = new R();
        r.setInfo(message);
        return r;
    }

    public static R of(String message) {
        R r = new R();
        r.setInfo(message);
        return r;
    }

    public R fail(String message){
        return of(FAILURE,message);
    }

    public R ok(String message){
        return of(SUCCESS,message);
    }

    public R build(Object data){
        this.setData(data);
        return this;
    }
}
