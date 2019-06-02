package com.ubatis.circleserver.bean;

import java.io.Serializable;

public class SysConfigBean implements Serializable {

	// config_name,circle_id
    private static final long serialVersionUID = 1L;

    /** 设置名称 */ 
    private String config_name;
    /**  */ 
    private int circle_id;

    //getter
    public String getConfig_name() { 
        return config_name;
    }
    public int getCircle_id() { 
        return circle_id;
    }

    //setter
    public void setConfig_name(String config_name) { 
        this.config_name = config_name;
    }
    public void setCircle_id(int circle_id) { 
        this.circle_id = circle_id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", config_name=").append(config_name);
        sb.append(", circle_id=").append(circle_id);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}