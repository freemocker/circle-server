package com.ubatis.circleserver.modules.common.bean;

/**
 * Created by lance on 2018/9/12.
 */
public class SystemInfoBean {

    public SystemInfoBean() {
    }

    public SystemInfoBean(String timezone, int timestamp) {
        this.timezone = timezone;
        this.timestamp = timestamp;
    }

    private String timezone;
    private int timestamp;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
