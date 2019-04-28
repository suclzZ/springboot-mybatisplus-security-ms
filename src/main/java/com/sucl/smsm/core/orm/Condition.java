package com.sucl.smsm.core.orm;

/**
 * 前端传入的查询条件对象
 */
public class Condition {

    private String property;
    private String operate;
    private Object value;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
