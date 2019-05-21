package com.ubatis.circleserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by liangshicong on 2017/4/16.
 */
@Component
@ConfigurationProperties(prefix = "sys-config")
public class SysConfig {

    private boolean debug;
    private String sign_key;
    private String sys_staff_login_key;
    private boolean valid_sign;
    private boolean valid_token;
    private String app_name;
    private String oss_endpoint;
    private String oss_accesskey_id;
    private String oss_accesskey_secret;
    private String oss_bucket_name;
    private int web_socket_port;

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getSign_key() {
        return sign_key;
    }

    public void setSign_key(String sign_key) {
        this.sign_key = sign_key;
    }

    public String getSys_staff_login_key() {
        return sys_staff_login_key;
    }

    public void setSys_staff_login_key(String sys_staff_login_key) {
        this.sys_staff_login_key = sys_staff_login_key;
    }

    public boolean isValid_sign() {
        return valid_sign;
    }

    public void setValid_sign(boolean valid_sign) {
        this.valid_sign = valid_sign;
    }

    public boolean isValid_token() {
        return valid_token;
    }

    public void setValid_token(boolean valid_token) {
        this.valid_token = valid_token;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getOss_endpoint() {
        return oss_endpoint;
    }

    public void setOss_endpoint(String oss_endpoint) {
        this.oss_endpoint = oss_endpoint;
    }

    public String getOss_accesskey_id() {
        return oss_accesskey_id;
    }

    public void setOss_accesskey_id(String oss_accesskey_id) {
        this.oss_accesskey_id = oss_accesskey_id;
    }

    public String getOss_accesskey_secret() {
        return oss_accesskey_secret;
    }

    public void setOss_accesskey_secret(String oss_accesskey_secret) {
        this.oss_accesskey_secret = oss_accesskey_secret;
    }

    public String getOss_bucket_name() {
        return oss_bucket_name;
    }

    public void setOss_bucket_name(String oss_bucket_name) {
        this.oss_bucket_name = oss_bucket_name;
    }

    public int getWeb_socket_port() {
        return web_socket_port;
    }

    public void setWeb_socket_port(int web_socket_port) {
        this.web_socket_port = web_socket_port;
    }

}
