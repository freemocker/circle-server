package com.ubatis.circleserver.util.generator;

public class GenConfig {

    public GenConfig() {
    }

    public GenConfig(boolean isNormal, String packageName, String extendClass, String prefix, String suffix, String outDir, String tablename_package, String tablename_path) {
        this.isNormal = isNormal;
        this.packageName = packageName;
        this.extendClass = extendClass;
        this.prefix = prefix;
        this.suffix = suffix;
        this.outDir = outDir;
        this.tablename_package = tablename_package;
        this.tablename_path = tablename_path;
    }

    private boolean isNormal;
    private String packageName;
    private String extendClass;
    private String prefix;
    private String suffix;
    private String outDir;
    private String tablename_package;
    private String tablename_path;

    public boolean isNormal() {
        return isNormal;
    }

    public void setNormal(boolean normal) {
        isNormal = normal;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getExtendClass() {
        return extendClass;
    }

    public void setExtendClass(String extendClass) {
        this.extendClass = extendClass;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getOutDir() {
        return outDir;
    }

    public void setOutDir(String outDir) {
        this.outDir = outDir;
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
