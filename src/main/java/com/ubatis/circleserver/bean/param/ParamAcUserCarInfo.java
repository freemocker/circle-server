package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.bean.basic.MyParams;

/**
 *  ac_user_car_info
 */
public class ParamAcUserCarInfo extends MyParams implements Serializable {

	// id,user_id,car_brand,car_number,check_month,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

	public static final String BEAN_TABLE_NAME = "ac_user_car_info";

    /**  */ 
    private long id;
    /** ac_user.id */ 
    private long user_id;
    /** 品牌 */ 
    private String car_brand;
    /** 车牌号 */ 
    private String car_number;
    /** 年检月 */ 
    private int check_month;
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
    public String getCar_brand() { 
        return car_brand;
    }
    public String getCar_number() { 
        return car_number;
    }
    public int getCheck_month() { 
        return check_month;
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
    public void setCar_brand(String car_brand) { 
        put("car_brand",car_brand);
        this.car_brand = car_brand;
    }
    public void setCar_number(String car_number) { 
        put("car_number",car_number);
        this.car_number = car_number;
    }
    public void setCheck_month(int check_month) { 
        put("check_month",check_month);
        this.check_month = check_month;
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
        sb.append(", car_brand=").append(car_brand);
        sb.append(", car_number=").append(car_number);
        sb.append(", check_month=").append(check_month);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}