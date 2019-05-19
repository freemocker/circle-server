package com.ubatis.circleserver.util.token;

/** 刷新结果
 * Created by lance on 2017/4/26.
 */
public class RefreshResult {

    public RefreshResult() {
    }

    public RefreshResult(int code, TokenBean tokenBean) {
        this.code = code;
        this.tokenBean = tokenBean;
    }

    public RefreshResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;//返回码
    private String msg;//刷新结果文字描述
    private TokenBean tokenBean;

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

    public TokenBean getTokenBean() {
        return tokenBean;
    }

    public void setTokenBean(TokenBean tokenBean) {
        this.tokenBean = tokenBean;
    }
}
