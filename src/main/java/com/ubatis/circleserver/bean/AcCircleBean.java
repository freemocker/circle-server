package com.ubatis.circleserver.bean;

import java.io.Serializable;

/**
 * 小圈信息表 ac_circle
 */
public class AcCircleBean implements Serializable {

	// id,circle_code,circle_name,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

    /**  */ 
    private long id;
    /** 圈子编号 */ 
    private String circle_code;
    /** 圈子名称 */ 
    private String circle_name;
    /**  */ 
    private String gmt_create;
    /**  */ 
    private String gmt_update;

    //getter
    public long getId() { 
        return id;
    }
    public String getCircle_code() { 
        return circle_code;
    }
    public String getCircle_name() { 
        return circle_name;
    }
    public String getGmt_create() { 
        return gmt_create;
    }
    public String getGmt_update() { 
        return gmt_update;
    }

    //setter
    public void setId(long id) { 
        this.id = id;
    }
    public void setCircle_code(String circle_code) { 
        this.circle_code = circle_code;
    }
    public void setCircle_name(String circle_name) { 
        this.circle_name = circle_name;
    }
    public void setGmt_create(String gmt_create) { 
        this.gmt_create = gmt_create;
    }
    public void setGmt_update(String gmt_update) { 
        this.gmt_update = gmt_update;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", circle_code=").append(circle_code);
        sb.append(", circle_name=").append(circle_name);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}