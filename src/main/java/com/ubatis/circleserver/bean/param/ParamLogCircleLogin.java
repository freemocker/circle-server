package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.bean.basic.MyParams;

public class ParamLogCircleLogin extends MyParams implements Serializable {

	// id,circle_id,ip_address,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

    /** id */ 
    private long id;
    /** 登录的圈子账号id */ 
    private long circle_id;
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
    public long getCircle_id() { 
        return circle_id;
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
        put("id",id);
        this.id = id;
    }
    public void setCircle_id(long circle_id) { 
        put("circle_id",circle_id);
        this.circle_id = circle_id;
    }
    public void setIp_address(String ip_address) { 
        put("ip_address",ip_address);
        this.ip_address = ip_address;
    }
    public void setGmt_create(String gmt_create) { 
        put("gmt_create",gmt_create);
        this.gmt_create = gmt_create;
    }
    public void setGmt_update(String gmt_update) { 
        put("gmt_update",gmt_update);
        this.gmt_update = gmt_update;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", circle_id=").append(circle_id);
        sb.append(", ip_address=").append(ip_address);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}