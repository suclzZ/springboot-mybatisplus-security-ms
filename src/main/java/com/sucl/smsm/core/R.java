package com.sucl.smsm.core;

import lombok.Data;

/**
 * @author sucl
 * @date 2019/4/29
 */
@Data
public class R {
    private String code;
    private String info;
    private Object data;

    public static R of(String message) {
        R r = new R();
        r.setInfo(message);
        return r;
    }
}
