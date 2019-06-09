package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.bean.basic.MyParams;

/**
 *  fn_car_check_order
 */
public class ParamFnCarCheckOrder extends MyParams implements Serializable {

	// id,channel,openid,phone,car_number,remark,confirmation,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

	public ParamFnCarCheckOrder() {
		this.BEAN_TABLE_NAME = "fn_car_check_order";
	}

    /** id */ 
    private long id;
    /** 推广渠道 */ 
    private String channel;
    /** openid */ 
    private String openid;
    /** 手机号 */ 
    private String phone;
    /** 车牌号 */ 
    private String car_number;
    /** 备注 */ 
    private String remark;
    /** 确认到场标志 */ 
    private String confirmation;
    /**  */ 
    private String gmt_create;
    /**  */ 
    private String gmt_update;

    //getter
    public long getId() { 
        return id;
    }
    public String getChannel() { 
        return channel;
    }
    public String getOpenid() { 
        return openid;
    }
    public String getPhone() { 
        return phone;
    }
    public String getCar_number() { 
        return car_number;
    }
    public String getRemark() { 
        return remark;
    }
    public String getConfirmation() { 
        return confirmation;
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
    public void setChannel(String channel) { 
        put("channel",channel);
        this.channel = channel;
    }
    public void setOpenid(String openid) { 
        put("openid",openid);
        this.openid = openid;
    }
    public void setPhone(String phone) { 
        put("phone",phone);
        this.phone = phone;
    }
    public void setCar_number(String car_number) { 
        put("car_number",car_number);
        this.car_number = car_number;
    }
    public void setRemark(String remark) { 
        put("remark",remark);
        this.remark = remark;
    }
    public void setConfirmation(String confirmation) { 
        put("confirmation",confirmation);
        this.confirmation = confirmation;
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
        sb.append(", channel=").append(channel);
        sb.append(", openid=").append(openid);
        sb.append(", phone=").append(phone);
        sb.append(", car_number=").append(car_number);
        sb.append(", remark=").append(remark);
        sb.append(", confirmation=").append(confirmation);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}