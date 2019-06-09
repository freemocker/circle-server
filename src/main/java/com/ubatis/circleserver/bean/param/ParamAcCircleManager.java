package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.bean.basic.MyParams;

/**
 * 小圈管理账号表 ac_circle_manager
 */
public class ParamAcCircleManager extends MyParams implements Serializable {

	// id,circle_id,role,login_name,login_pwd,active_status,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

	public ParamAcCircleManager() {
		this.BEAN_TABLE_NAME = "ac_circle_manager";
	}

    /** id */ 
    private long id;
    /**  */ 
    private long circle_id;
    /** 角色 */ 
    private String role;
    /** 登录名 */ 
    private String login_name;
    /** 登录密码 */ 
    private String login_pwd;
    /** 0未审核，1已审核，2禁用 */ 
    private int active_status;
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
    public String getRole() { 
        return role;
    }
    public String getLogin_name() { 
        return login_name;
    }
    public String getLogin_pwd() { 
        return login_pwd;
    }
    public int getActive_status() { 
        return active_status;
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
    public void setRole(String role) { 
        put("role",role);
        this.role = role;
    }
    public void setLogin_name(String login_name) { 
        put("login_name",login_name);
        this.login_name = login_name;
    }
    public void setLogin_pwd(String login_pwd) { 
        put("login_pwd",login_pwd);
        this.login_pwd = login_pwd;
    }
    public void setActive_status(int active_status) { 
        put("active_status",active_status);
        this.active_status = active_status;
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
        sb.append(", role=").append(role);
        sb.append(", login_name=").append(login_name);
        sb.append(", login_pwd=").append(login_pwd);
        sb.append(", active_status=").append(active_status);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}