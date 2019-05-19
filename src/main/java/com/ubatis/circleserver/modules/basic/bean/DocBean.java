package com.ubatis.circleserver.modules.basic.bean;

import java.util.List;

/**
 * Created by lance on 2017/10/23.
 */
public class DocBean {

    private boolean isDiretory;
    private String parent;
    private String name;
    private List<DocBean> mdList;
    private String value;

    public boolean isDiretory() {
        return isDiretory;
    }

    public void setDiretory(boolean diretory) {
        isDiretory = diretory;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DocBean> getMdList() {
        return mdList;
    }

    public void setMdList(List<DocBean> mdList) {
        this.mdList = mdList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
