package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.bean.basic.MyParams;

/**
 * 系统配置表 sys_config
 */
public class ParamSysConfig extends MyParams implements Serializable {

	// id,circle_id,config_name,config_value
    private static final long serialVersionUID = 1L;

	public ParamSysConfig() {
		this.BEAN_TABLE_NAME = "sys_config";
	}

    /**  */ 
    private long id;
    /**  */ 
    private long circle_id;
    /** 设置名称 */ 
    private String config_name;
    /**  */ 
    private String config_value;

    //getter
    public long getId() { 
        return id;
    }
    public long getCircle_id() { 
        return circle_id;
    }
    public String getConfig_name() { 
        return config_name;
    }
    public String getConfig_value() { 
        return config_value;
    }

    //setter
    public void setId(long id) { 
        put("id",id);
        this.id = id;
    }
    public void setCircle_id(long circle_id) { 
        put("circle_id",circle_id);
        this.circle_id = circle_id;
    }
    public void setConfig_name(String config_name) { 
        put("config_name",config_name);
        this.config_name = config_name;
    }
    public void setConfig_value(String config_value) { 
        put("config_value",config_value);
        this.config_value = config_value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", circle_id=").append(circle_id);
        sb.append(", config_name=").append(config_name);
        sb.append(", config_value=").append(config_value);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}