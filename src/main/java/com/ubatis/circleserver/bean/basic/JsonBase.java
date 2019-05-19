package com.ubatis.circleserver.bean.basic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liangshicong on 2017/4/18.
 */
public class JsonBase<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public JsonBase() {

    }

    public JsonBase(int c, Object info) {
        this.code = c;
        this.info = info;
    }

    public JsonBase(int c, List<T> d, Object info, Object h) {
        this.code = c;
        this.data = d;
        this.info = info;
        this.header = h;
    }

    public JsonBase(int c, List<T> d, Object info) {
        this.code = c;
        this.data = d;
        this.info = info;
    }

    private int code;
    private Object header;
    private List<T> data;
    private Object info;
    private Object latest_token;

    public int getCode() {
        return code;
    }

    public void setCode(int c) {
        this.code = c;
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object h) {
        this.header = h;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> d) {
        this.data = d;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Object getLatest_token() {
        return latest_token;
    }

    public void setLatest_token(Object latest_token) {
        this.latest_token = latest_token;
    }
}
