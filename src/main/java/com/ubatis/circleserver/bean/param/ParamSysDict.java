package com.ubatis.circleserver.bean.param;

import java.io.Serializable;
import com.ubatis.circleserver.bean.basic.MyParams;

public class ParamSysDict extends MyParams implements Serializable {

	// id,category,key,value,value_type,sort,comment,gmt_create,gmt_update
    private static final long serialVersionUID = 1L;

    /**  */ 
    private int id;
    /**  */ 
    private String category;
    /**  */ 
    private String key;
    /**  */ 
    private String value;
    /**  */ 
    private String value_type;
    /**  */ 
    private int sort;
    /** 注释 */ 
    private String comment;
    /**  */ 
    private String gmt_create;
    /**  */ 
    private String gmt_update;

    //getter
    public int getId() { 
        return id;
    }
    public String getCategory() { 
        return category;
    }
    public String getKey() { 
        return key;
    }
    public String getValue() { 
        return value;
    }
    public String getValue_type() { 
        return value_type;
    }
    public int getSort() { 
        return sort;
    }
    public String getComment() { 
        return comment;
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
    public void setCategory(String category) { 
        put("category",category);
        this.category = category;
    }
    public void setKey(String key) { 
        put("key",key);
        this.key = key;
    }
    public void setValue(String value) { 
        put("value",value);
        this.value = value;
    }
    public void setValue_type(String value_type) { 
        put("value_type",value_type);
        this.value_type = value_type;
    }
    public void setSort(int sort) { 
        put("sort",sort);
        this.sort = sort;
    }
    public void setComment(String comment) { 
        put("comment",comment);
        this.comment = comment;
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
        sb.append(", category=").append(category);
        sb.append(", key=").append(key);
        sb.append(", value=").append(value);
        sb.append(", value_type=").append(value_type);
        sb.append(", sort=").append(sort);
        sb.append(", comment=").append(comment);
        sb.append(", gmt_create=").append(gmt_create);
        sb.append(", gmt_update=").append(gmt_update);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

}