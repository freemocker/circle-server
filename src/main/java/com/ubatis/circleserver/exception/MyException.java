package com.ubatis.circleserver.exception;

/**
 * Created by lance on 2017/4/24.
 */
public class MyException extends RuntimeException {

    public MyException(Integer code, String message ) {
        super(message);
        this.code = code;
    }

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public MyException(String message) {
        super(message);
    }
}
