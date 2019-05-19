package com.ubatis.circleserver.util.token;

/**
 * Created by lance on 2017/4/26.
 */
public class LatestToken {

    public LatestToken() {
    }

    public LatestToken(String token, long expire) {
        this.token = token;
        this.expire = expire;
    }

    private String token;
    private long expire;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "LatestToken{" +
                "token='" + token + '\'' +
                ", expire=" + expire +
                '}';
    }
}
