package com.ubatis.circleserver.bean;

import java.io.Serializable;

/**
 *  log_user_login
 */
public class LogUserLoginBean implements Serializable {

	// id,user_id,ip_address,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

    /**  */ 
    private long id;
    /** 登录id */ 
    private long user_id;
    /**  */ 
    private String ip_address;
    /**  */ 
    private String gmt_create;
    /**  */ 
    private String gmt_update;

    //getter
    public long getId() { 
        return id;
    }
    public long getUser_id() { 
        return user_id;
    }
    public String getIp_address() { 
        return ip_address;
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
    public void setUser_id(long user_id) { 
        this.user_id = user_id;
    }
    public void setIp_address(String ip_address) { 
        this.ip_address = ip_address;
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
        sb.append(", user_id=").append(user_id);
        sb.append(", ip_address=").append(ip_address);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}