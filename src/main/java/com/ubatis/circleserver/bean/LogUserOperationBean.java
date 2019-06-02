package com.ubatis.circleserver.bean;

import java.io.Serializable;

public class LogUserOperationBean implements Serializable {

	// id,user_id,operate_name,operate_remark,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

    /**  */ 
    private long id;
    /**  */ 
    private long user_id;
    /**  */ 
    private String operate_name;
    /**  */ 
    private String operate_remark;
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
    public String getOperate_name() { 
        return operate_name;
    }
    public String getOperate_remark() { 
        return operate_remark;
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
    public void setOperate_name(String operate_name) { 
        this.operate_name = operate_name;
    }
    public void setOperate_remark(String operate_remark) { 
        this.operate_remark = operate_remark;
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
        sb.append(", operate_name=").append(operate_name);
        sb.append(", operate_remark=").append(operate_remark);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}