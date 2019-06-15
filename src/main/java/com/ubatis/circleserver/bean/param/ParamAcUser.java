package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.util.daoutils.MyParams;

/**
 * 小圈用户表 ac_user
 */
public class ParamAcUser extends MyParams implements Serializable {

	// openid,circle_id,nickname,unionid,phone,wechat,gender,avatar,province,city,address,login_times,login_code,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

	public ParamAcUser() {
		this.BEAN_TABLE_NAME = "ac_user";
	}

    /**  */ 
    private String openid;
    /**  */ 
    private long circle_id;
    /**  */ 
    private String nickname;
    /**  */ 
    private String unionid;
    /**  */ 
    private String phone;
    /**  */ 
    private String wechat;
    /**  */ 
    private int gender;
    /**  */ 
    private String avatar;
    /**  */ 
    private String province;
    /**  */ 
    private String city;
    /**  */ 
    private String address;
    /** 打开页面增加一次 */ 
    private int login_times;
    /** 微信登录的code，用于获取openid */ 
    private String login_code;
    /**  */ 
    private String gmt_create;
    /**  */ 
    private String gmt_update;

    //getter
    public String getOpenid() { 
        return openid;
    }
    public long getCircle_id() { 
        return circle_id;
    }
    public String getNickname() { 
        return nickname;
    }
    public String getUnionid() { 
        return unionid;
    }
    public String getPhone() { 
        return phone;
    }
    public String getWechat() { 
        return wechat;
    }
    public int getGender() { 
        return gender;
    }
    public String getAvatar() { 
        return avatar;
    }
    public String getProvince() { 
        return province;
    }
    public String getCity() { 
        return city;
    }
    public String getAddress() { 
        return address;
    }
    public int getLogin_times() { 
        return login_times;
    }
    public String getLogin_code() { 
        return login_code;
    }
    public String getGmt_create() { 
        return gmt_create;
    }
    public String getGmt_update() { 
        return gmt_update;
    }

    //setter
    public void setOpenid(String openid) { 
        put("openid",openid);
        this.openid = openid;
    }
    public void setCircle_id(long circle_id) { 
        put("circle_id",circle_id);
        this.circle_id = circle_id;
    }
    public void setNickname(String nickname) { 
        put("nickname",nickname);
        this.nickname = nickname;
    }
    public void setUnionid(String unionid) { 
        put("unionid",unionid);
        this.unionid = unionid;
    }
    public void setPhone(String phone) { 
        put("phone",phone);
        this.phone = phone;
    }
    public void setWechat(String wechat) { 
        put("wechat",wechat);
        this.wechat = wechat;
    }
    public void setGender(int gender) { 
        put("gender",gender);
        this.gender = gender;
    }
    public void setAvatar(String avatar) { 
        put("avatar",avatar);
        this.avatar = avatar;
    }
    public void setProvince(String province) { 
        put("province",province);
        this.province = province;
    }
    public void setCity(String city) { 
        put("city",city);
        this.city = city;
    }
    public void setAddress(String address) { 
        put("address",address);
        this.address = address;
    }
    public void setLogin_times(int login_times) { 
        put("login_times",login_times);
        this.login_times = login_times;
    }
    public void setLogin_code(String login_code) { 
        put("login_code",login_code);
        this.login_code = login_code;
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
        sb.append(", openid=").append(openid);
        sb.append(", circle_id=").append(circle_id);
        sb.append(", nickname=").append(nickname);
        sb.append(", unionid=").append(unionid);
        sb.append(", phone=").append(phone);
        sb.append(", wechat=").append(wechat);
        sb.append(", gender=").append(gender);
        sb.append(", avatar=").append(avatar);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", address=").append(address);
        sb.append(", login_times=").append(login_times);
        sb.append(", login_code=").append(login_code);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}