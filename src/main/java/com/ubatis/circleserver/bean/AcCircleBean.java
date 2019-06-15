package com.ubatis.circleserver.bean;

import java.io.Serializable;

/**
 * 小圈信息表 ac_circle
 */
public class AcCircleBean implements Serializable {

	// id,circle_name,circle_remark,ac_token_id,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

    /**  */ 
    private long id;
    /** 圈子名称 */ 
    private String circle_name;
    /** 备注 */ 
    private String circle_remark;
    /** sys_access_token.id */ 
    private int ac_token_id;
    /**  */ 
    private String gmt_create;
    /**  */ 
    private String gmt_update;

    //getter
    public long getId() { 
        return id;
    }
    public String getCircle_name() { 
        return circle_name;
    }
    public String getCircle_remark() { 
        return circle_remark;
    }
    public int getAc_token_id() { 
        return ac_token_id;
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
    public void setCircle_name(String circle_name) { 
        this.circle_name = circle_name;
    }
    public void setCircle_remark(String circle_remark) { 
        this.circle_remark = circle_remark;
    }
    public void setAc_token_id(int ac_token_id) { 
        this.ac_token_id = ac_token_id;
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
        sb.append(", circle_name=").append(circle_name);
        sb.append(", circle_remark=").append(circle_remark);
        sb.append(", ac_token_id=").append(ac_token_id);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}