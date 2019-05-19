package com.ubatis.circleserver.exception;

/**
 * Created by lance on 2017/4/24.
 */
public class ValidException extends RuntimeException {

    public ValidException(Integer code, String message ) {
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

    public ValidException(String message) {
        super(message);
    }
}
