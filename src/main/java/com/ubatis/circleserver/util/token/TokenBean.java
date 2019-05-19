package com.ubatis.circleserver.util.token;

/** token的bean
 * Created by lance on 2017/4/21.
 */
public class TokenBean {

    private String token;
    private String refresh_token;
    private long refresh_expire;
    private long token_expire;
    private int refresh_times;
    private long last_token_timestamp;
    private long last_refresh_token_timestamp;

    public TokenBean(String token, String refresh_token, long refresh_expire, long token_expire, int refresh_times, long last_token_timestamp, long last_refresh_token_timestamp) {
        this.token = token;
        this.refresh_token = refresh_token;
        this.refresh_expire = refresh_expire;
        this.token_expire = token_expire;
        this.refresh_times = refresh_times;
        this.last_token_timestamp = last_token_timestamp;
        this.last_refresh_token_timestamp = last_refresh_token_timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getRefresh_expire() {
        return refresh_expire;
    }

    public void setRefresh_expire(long refresh_expire) {
        this.refresh_expire = refresh_expire;
    }

    public long getToken_expire() {
        return token_expire;
    }

    public void setToken_expire(long token_expire) {
        this.token_expire = token_expire;
    }

    public int getRefresh_times() {
        return refresh_times;
    }

    public void setRefresh_times(int refresh_times) {
        this.refresh_times = refresh_times;
    }

    public long getLast_token_timestamp() {
        return last_token_timestamp;
    }

    public void setLast_token_timestamp(long last_token_timestamp) {
        this.last_token_timestamp = last_token_timestamp;
    }

    public long getLast_refresh_token_timestamp() {
        return last_refresh_token_timestamp;
    }

    public void setLast_refresh_token_timestamp(long last_refresh_token_timestamp) {
        this.last_refresh_token_timestamp = last_refresh_token_timestamp;
    }
}
