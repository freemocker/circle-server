package com.ubatis.circleserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by liangshicong on 2017/4/16.
 */
@Component
@ConfigurationProperties(prefix = "gen-config")
public class GeneratorConfig {

    public GeneratorConfig() {
    }

    public GeneratorConfig(String normal_package_name, String normal_extend_class, String normal_prefix, String normal_suffix, String normal_out_dir, String param_package_name, String param_extend_class, String param_prefix, String param_suffix, String param_out_dir, String tablename_package, String tablename_path) {
        this.normal_package_name = normal_package_name;
        this.normal_extend_class = normal_extend_class;
        this.normal_prefix = normal_prefix;
        this.normal_suffix = normal_suffix;
        this.normal_out_dir = normal_out_dir;
        this.param_package_name = param_package_name;
        this.param_extend_class = param_extend_class;
        this.param_prefix = param_prefix;
        this.param_suffix = param_suffix;
        this.param_out_dir = param_out_dir;
        this.tablename_package = tablename_package;
        this.tablename_path = tablename_path;
    }

    private String normal_package_name;
    private String normal_extend_class;
    private String normal_prefix;
    private String normal_suffix;
    private String normal_out_dir;

    private String param_package_name;
    private String param_extend_class;
    private String param_prefix;
    private String param_suffix;
    private String param_out_dir;
    private String tablename_package;
    private String tablename_path;

    public String getNormal_package_name() {
        return normal_package_name;
    }

    public void setNormal_package_name(String normal_package_name) {
        this.normal_package_name = normal_package_name;
    }

    public String getNormal_extend_class() {
        return normal_extend_class;
    }

    public void setNormal_extend_class(String normal_extend_class) {
        this.normal_extend_class = normal_extend_class;
    }

    public String getNormal_prefix() {
        return normal_prefix;
    }

    public void setNormal_prefix(String normal_prefix) {
        this.normal_prefix = normal_prefix;
    }

    public String getNormal_suffix() {
        return normal_suffix;
    }

    public void setNormal_suffix(String normal_suffix) {
        this.normal_suffix = normal_suffix;
    }

    public String getNormal_out_dir() {
        return normal_out_dir;
    }

    public void setNormal_out_dir(String normal_out_dir) {
        this.normal_out_dir = normal_out_dir;
    }

    public String getParam_package_name() {
        return param_package_name;
    }

    public void setParam_package_name(String param_package_name) {
        this.param_package_name = param_package_name;
    }

    public String getParam_extend_class() {
        return param_extend_class;
    }

    public void setParam_extend_class(String param_extend_class) {
        this.param_extend_class = param_extend_class;
    }

    public String getParam_prefix() {
        return param_prefix;
    }

    public void setParam_prefix(String param_prefix) {
        this.param_prefix = param_prefix;
    }

    public String getParam_suffix() {
        return param_suffix;
    }

    public void setParam_suffix(String param_suffix) {
        this.param_suffix = param_suffix;
    }

    public String getParam_out_dir() {
        return param_out_dir;
    }

    public void setParam_out_dir(String param_out_dir) {
        this.param_out_dir = param_out_dir;
    }

    public String getTablename_package() {
        return tablename_package;
    }

    public void setTablename_package(String tablename_package) {
        this.tablename_package = tablename_package;
    }

    public String getTablename_path() {
        return tablename_path;
    }

    public void setTablename_path(String tablename_path) {
        this.tablename_path = tablename_path;
    }
}
