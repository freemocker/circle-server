package com.ubatis.circleserver.util.generator;

public class GenConfig {

    public GenConfig(boolean isNormal, String packageName, String extendClass, String prefix, String suffix, String outDir) {
        this.isNormal = isNormal;
        this.packageName = packageName;
        this.extendClass = extendClass;
        this.prefix = prefix;
        this.suffix = suffix;
        this.outDir = outDir;
    }

    private boolean isNormal;
    private String packageName;
    private String extendClass;
    private String prefix;
    private String suffix;
    private String outDir;

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
}
