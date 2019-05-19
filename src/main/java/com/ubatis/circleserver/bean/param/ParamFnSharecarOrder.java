package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.bean.basic.MyParams;

public class ParamFnSharecarOrder extends MyParams implements Serializable {

	// id,circle_id,gmt_update,gmt_create
    private static final long serialVersionUID = 1L;

    private int id;
    private int circle_id;
    private String gmt_update;
    private String gmt_create;

    //getter
    public int getId() { 
        return id;
    }
    public int getCircle_id() { 
        return circle_id;
    }
    public String getGmt_update() { 
        return gmt_update;
    }
    public String getGmt_create() { 
        return gmt_create;
    }

    //setter
    public void setId(int id) { 
        put("id",id);
        this.id = id;
    }
    public void setCircle_id(int circle_id) { 
        put("circle_id",circle_id);
        this.circle_id = circle_id;
    }
    public void setGmt_update(String gmt_update) { 
        put("gmt_update",gmt_update);
        this.gmt_update = gmt_update;
    }
    public void setGmt_create(String gmt_create) { 
        put("gmt_create",gmt_create);
        this.gmt_create = gmt_create;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", circle_id=").append(circle_id);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}