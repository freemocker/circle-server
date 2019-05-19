package com.ubatis.circleserver.util.token;

/**
 * Created by lance on 2017/4/27.
 */
public class LatestResult {

    public LatestResult(int code, LatestToken latestToken) {
        this.code = code;
        this.latestToken = latestToken;
    }

    public LatestResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;
    private LatestToken latestToken;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LatestToken getLatestToken() {
        return latestToken;
    }

    public void setLatestToken(LatestToken latestToken) {
        this.latestToken = latestToken;
    }

    @Override
    public String toString() {
        return "LatestResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", latestToken=" + latestToken +
                '}';
    }
}
