package com.ubatis.circleserver.util.token;

/** token校验结果
 * Created by lance on 2017/4/26.
 */
public class OauthValidResult {

    public OauthValidResult() {
    }

    public OauthValidResult(boolean valid, int code, String msg) {
        this.valid = valid;
        this.code = code;
        this.msg = msg;
    }

    private boolean valid;
    private int code;
    private String msg;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

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
}
