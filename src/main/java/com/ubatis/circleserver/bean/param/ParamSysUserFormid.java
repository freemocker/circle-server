package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.util.daoutils.MyParams;

/**
 * 小程序模板消息通知豆表 sys_user_formid
 */
public class ParamSysUserFormid extends MyParams implements Serializable {

	// id,user_id,form_id,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

	public ParamSysUserFormid() {
		this.BEAN_TABLE_NAME = "sys_user_formid";
	}

    /**  */ 
    private long id;
    /**  */ 
    private long user_id;
    /**  */ 
    private String form_id;
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
    public String getForm_id() { 
        return form_id;
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
    public void setUser_id(long user_id) { 
        put("user_id",user_id);
        this.user_id = user_id;
    }
    public void setForm_id(String form_id) { 
        put("form_id",form_id);
        this.form_id = form_id;
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
        sb.append(", user_id=").append(user_id);
        sb.append(", form_id=").append(form_id);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}