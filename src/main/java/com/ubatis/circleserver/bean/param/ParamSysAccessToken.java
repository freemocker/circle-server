package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.util.daoutils.MyParams;

/**
 *  sys_access_token
 */
public class ParamSysAccessToken extends MyParams implements Serializable {

	// id,mp_appid,mp_secret,mp_access_token,mp_expires_time,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

	public ParamSysAccessToken() {
		this.BEAN_TABLE_NAME = "sys_access_token";
	}

    /**  */ 
    private int id;
    /** 小程序appid */ 
    private String mp_appid;
    /** 小程序秘钥 */ 
    private String mp_secret;
    /** 小程序actoken */ 
    private String mp_access_token;
    /** 小程序token过期时间戳 */ 
    private int mp_expires_time;
    /**  */ 
    private String gmt_create;
    /**  */ 
    private String gmt_update;

    //getter
    public int getId() { 
        return id;
    }
    public String getMp_appid() { 
        return mp_appid;
    }
    public String getMp_secret() { 
        return mp_secret;
    }
    public String getMp_access_token() { 
        return mp_access_token;
    }
    public int getMp_expires_time() { 
        return mp_expires_time;
    }
    public String getGmt_create() { 
        return gmt_create;
    }
    public String getGmt_update() { 
        return gmt_update;
    }

    //setter
    public void setId(int id) { 
        put("id",id);
        this.id = id;
    }
    public void setMp_appid(String mp_appid) { 
        put("mp_appid",mp_appid);
        this.mp_appid = mp_appid;
    }
    public void setMp_secret(String mp_secret) { 
        put("mp_secret",mp_secret);
        this.mp_secret = mp_secret;
    }
    public void setMp_access_token(String mp_access_token) { 
        put("mp_access_token",mp_access_token);
        this.mp_access_token = mp_access_token;
    }
    public void setMp_expires_time(int mp_expires_time) { 
        put("mp_expires_time",mp_expires_time);
        this.mp_expires_time = mp_expires_time;
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
        sb.append(", mp_appid=").append(mp_appid);
        sb.append(", mp_secret=").append(mp_secret);
        sb.append(", mp_access_token=").append(mp_access_token);
        sb.append(", mp_expires_time=").append(mp_expires_time);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}