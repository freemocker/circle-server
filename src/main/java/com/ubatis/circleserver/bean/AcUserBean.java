package com.ubatis.circleserver.bean;

import java.io.Serializable;

public class AcUserBean implements Serializable {

	// id,circle_id,openid,nickname,unionid,phone,wechat,gender,avatar,province,city,address,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

    private int id;
    private int circle_id;
    private String openid;
    private String nickname;
    private String unionid;
    private String phone;
    private String wechat;
    private int gender;
    private String avatar;
    private String province;
    private String city;
    private String address;
    private String gmt_create;
    private String gmt_update;

    //getter
    public int getId() { 
        return id;
    }
    public int getCircle_id() { 
        return circle_id;
    }
    public String getOpenid() { 
        return openid;
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
    public String getGmt_create() { 
        return gmt_create;
    }
    public String getGmt_update() { 
        return gmt_update;
    }

    //setter
    public void setId(int id) { 
        this.id = id;
    }
    public void setCircle_id(int circle_id) { 
        this.circle_id = circle_id;
    }
    public void setOpenid(String openid) { 
        this.openid = openid;
    }
    public void setNickname(String nickname) { 
        this.nickname = nickname;
    }
    public void setUnionid(String unionid) { 
        this.unionid = unionid;
    }
    public void setPhone(String phone) { 
        this.phone = phone;
    }
    public void setWechat(String wechat) { 
        this.wechat = wechat;
    }
    public void setGender(int gender) { 
        this.gender = gender;
    }
    public void setAvatar(String avatar) { 
        this.avatar = avatar;
    }
    public void setProvince(String province) { 
        this.province = province;
    }
    public void setCity(String city) { 
        this.city = city;
    }
    public void setAddress(String address) { 
        this.address = address;
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
        sb.append(", circle_id=").append(circle_id);
        sb.append(", openid=").append(openid);
        sb.append(", nickname=").append(nickname);
        sb.append(", unionid=").append(unionid);
        sb.append(", phone=").append(phone);
        sb.append(", wechat=").append(wechat);
        sb.append(", gender=").append(gender);
        sb.append(", avatar=").append(avatar);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", address=").append(address);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}